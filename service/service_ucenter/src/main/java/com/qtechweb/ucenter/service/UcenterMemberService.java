package com.qtechweb.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtechweb.ucenter.entity.UcenterMember;
import com.qtechweb.ucenter.entity.vo.RegisterVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lzz
 * @since 2020-09-15
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /* 用户登录 */
    String loginUser(UcenterMember user);

    /* 用户验证码手机注册 */
    String registerUser(RegisterVo vo);

    /* 根据用户头中的token获取用户信息 */
    UcenterMember getMemberInfoByToken(HttpServletRequest request);

    /* 重定向到微信二维码 */
    String getwXQrConnect();

    /* 微信登录回调 */
    String wxCallback(String code, String state);

    /* 通过id获取用户信息 */
    UcenterMember getMemberInfoById(String memberId);
}
