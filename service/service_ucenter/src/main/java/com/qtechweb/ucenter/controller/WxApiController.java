package com.qtechweb.ucenter.controller;

import com.qtechweb.ucenter.service.UcenterMemberService;
import com.qtechweb.ucenter.service.impl.UcenterMemberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@Api(value = "微信登录相关接口")
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {


    @Resource(type = UcenterMemberServiceImpl.class)
    private UcenterMemberService ucenterMemberService;


    @ApiOperation("获取微信二维码")
    @GetMapping(path = "/login")
    public String getQrConnect(HttpSession session) {
        return "redirect:" + ucenterMemberService.getwXQrConnect();
    }


    @ApiOperation("微信登录回调地址")
    @GetMapping("/callback")
    public String callback(String code, String state) {
        String url = ucenterMemberService.wxCallback(code, state);
        return "redirect:" + url;
    }
}
