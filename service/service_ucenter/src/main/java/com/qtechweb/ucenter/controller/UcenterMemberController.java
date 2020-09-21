package com.qtechweb.ucenter.controller;


import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.result.ResMap;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.ucenter.entity.UcenterMember;
import com.qtechweb.ucenter.entity.vo.RegisterVo;
import com.qtechweb.ucenter.service.UcenterMemberService;
import com.qtechweb.ucenter.service.impl.UcenterMemberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 控制器
 * </p>
 *
 * @author lzz
 * @since 2020-09-15
 */
@Slf4j
@Api(value = "用户中心会员接口")
@CrossOrigin
@RestController
@RequestMapping("/ucenter/member")
public class UcenterMemberController {

    @Resource(type = UcenterMemberServiceImpl.class)
    private UcenterMemberService ucenterMemberService;

    @ApiOperation("会员登录")
    @PostMapping("/login")
    public Result loginUser(@RequestBody UcenterMember user) {
        String token = ucenterMemberService.loginUser(user);
        AssertUtils.StringNotNull(token, "返回token错误");
        return Result.success(ResMap.create().setKeyValue("token", token));
    }


    @ApiOperation("用户手机验证码注册")
    @PostMapping("/register")
    public Result registerUser(@RequestBody RegisterVo register) {
        String token = ucenterMemberService.registerUser(register);
        AssertUtils.StringNotNull(token, "注册已成功,但服务器异常,请重新登录");
        return Result.success(ResMap.create().setKeyValue("token", token));
    }

    @ApiOperation("根据token获取用户信息")
    @GetMapping("/memberInfo")
    public Result getMemberInfo(HttpServletRequest request) {
        UcenterMember ucenterMember = ucenterMemberService.getMemberInfoByToken(request);
        return Result.success(ucenterMember);
    }

}

