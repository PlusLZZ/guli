package com.qtechweb.ucenter.service.impl;

import com.alibaba.nacos.client.config.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.commonutils.enums.AuthEnum;
import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.utils.JwtUtils;
import com.qtechweb.ucenter.entity.UcenterMember;
import com.qtechweb.ucenter.entity.vo.RegisterVo;
import com.qtechweb.ucenter.mapper.UcenterMemberMapper;
import com.qtechweb.ucenter.service.UcenterMemberService;
import com.qtechweb.ucenter.utils.ConstantWx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-09-15
 */
@Service
@Slf4j
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /* 返回token */
    @Override
    public String loginUser(UcenterMember user) {
        String mobile = user.getMobile();
        String password = user.getPassword();
        AssertUtils.StringNotNull(mobile, "手机号为空,请输入");
        AssertUtils.StringNotNull(password, "密码为空,请输入密码");
        QueryWrapper<UcenterMember> query = new QueryWrapper<>();
        query.eq("mobile", mobile);
        UcenterMember one = getOne(query);
        AssertUtils.ObjectNotNull(one, "手机号输入错误!");
        /*
         * TODO
         *  后期加入密码加密,密码不能存储明文,暂时预定为MD5加密
         *  使用nacos的MD5加密工具类
         * */
        boolean isPassword = MD5.getInstance().getMD5String(password).equalsIgnoreCase(one.getPassword());
        AssertUtils.BooleanIsTrue(isPassword, "密码错误");
        AssertUtils.BooleanIsTrue(!one.getIsDisabled(), "此账号已被禁用");
        String token = JwtUtils.getJwtToken(one.getId(), one.getNickname());
        return token;
    }

    @Override
    public String registerUser(RegisterVo vo) {
        String code = vo.getCode();
        String mobile = vo.getMobile();
        String password = vo.getPassword();
        String nickname = vo.getNickname();
        AssertUtils.StringNotNull(code, "验证码为空!");
        AssertUtils.StringNotNull(mobile, "手机号为空");
        AssertUtils.StringNotNull(password, "密码为空");
        AssertUtils.StringNotNull(nickname, "昵称为空");
        Boolean aBoolean = redisTemplate.hasKey(mobile);
        AssertUtils.BooleanIsTrue(aBoolean, "验证码已过期");
        String redisCode = redisTemplate.opsForValue().get(mobile);
        boolean equals = code.equals(redisCode);
        AssertUtils.BooleanIsTrue(equals, "验证码输入错误");
        /* 判断手机号是否已经被注册 */
        QueryWrapper<UcenterMember> query = new QueryWrapper<>();
        query.eq("mobile", mobile);
        UcenterMember one = getOne(query);
        log.info("---------------------");
        log.info(one.toString());
        AssertUtils.BooleanIsTrue(null == one, "手机号已被注册!");
        /* 添加到数据库并返回token */
        UcenterMember user = new UcenterMember();
        user.setMobile(mobile);
        user.setPassword(MD5.getInstance().getMD5String(password));
        user.setNickname(nickname);
        user.setAvatar("https://educate-guli.oss-cn-beijing.aliyuncs.com/header/default/man.jpg");
        boolean save = save(user);
        AssertUtils.BooleanIsTrue(save, "注册失败");
        log.info("当前注册的用户:  " + user.toString());
        return JwtUtils.getJwtToken(user.getId(), user.getNickname());
    }

    @Override
    public UcenterMember getMemberInfoByToken(HttpServletRequest request) {
        log.info(request.getHeader("token"));
        String id = JwtUtils.getMemberIdByJwtToken(request);
        log.info("当前进入的ID为: " + id);
        AssertUtils.StringNotNull(id, AuthEnum.NOT_LOGGED);
        UcenterMember member = getMemberInfoById(id);
        AssertUtils.ObjectNotNull(member, "系统获取信息异常,请刷新");
        return member;
    }

    @Override
    public String getwXQrConnect() {
        /* 拼接API */
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect?" +
                "appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String redirecturl = ConstantWx.REDIRECT_URL;
        try {
            redirecturl = URLEncoder.encode(redirecturl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "http://localhost:7700/error";
        }
        String url = String.format(baseUrl, ConstantWx.APP_ID, redirecturl, "guli");
        /* 防止csrf攻击 */
        return url;
    }


    @Transactional
    @Override
    public String wxCallback(String code, String state) {
        /* 1.使用code请求微信API,得到令牌与openID */
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        accessTokenUrl = String.format(accessTokenUrl,
                ConstantWx.APP_ID, ConstantWx.APP_SECRET, code);
        ResponseEntity<Map> accessTokenEntity = restTemplate.getForEntity(accessTokenUrl, Map.class);
        Map tokenEntityBody = accessTokenEntity.getBody();
        AssertUtils.BooleanIsTrue(tokenEntityBody.containsKey("openid"),
                tokenEntityBody.get("errcode") + " : " + tokenEntityBody.get("errmsg"));
        /* 2.使用openID与令牌得到用户信息,并判断是已经注册的用户还是未注册的用户 */
        String accessToken = (String) tokenEntityBody.get("access_token");
        String openId = (String) tokenEntityBody.get("openid");
        /* 判断是否需要添加用户 */
        QueryWrapper<UcenterMember> query = new QueryWrapper<>();
        query.eq("openid", openId);
        UcenterMember member = getOne(query);
        if (null == member) {
            /* 获取扫码用户的信息 */
            String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?" +
                    "access_token=%s" +
                    "&openid=%s";
            userInfoUrl = String.format(userInfoUrl, accessToken, openId);
            ResponseEntity<Map> userInfoEntity = restTemplate.getForEntity(userInfoUrl, Map.class);
            Map infoEntityBody = userInfoEntity.getBody();
            AssertUtils.BooleanIsTrue(infoEntityBody.containsKey("openid"),
                    tokenEntityBody.get("errcode") + " : " + tokenEntityBody.get("errmsg"));
            String nickname = (String) infoEntityBody.get("nickname");
            Integer sex = (Integer) infoEntityBody.get("sex");
            String headimgurl = (String) infoEntityBody.get("headimgurl");
            /* ----------------------------- */
            member = new UcenterMember();
            member.setOpenid(openId);
            member.setNickname(nickname);
            member.setAvatar(headimgurl);
            member.setSex(sex);
            boolean save = save(member);
            if (!save) {
                return "http://localhost:7700/error";
            }
        }
        /* 生成token */
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return "http://localhost:7700?token=" + jwtToken;
    }

    @Cacheable(cacheNames = "memberInfo", key = "#memberId", unless = "#result==null")
    @Override
    public UcenterMember getMemberInfoById(String memberId) {
        return getById(memberId);
    }


}
