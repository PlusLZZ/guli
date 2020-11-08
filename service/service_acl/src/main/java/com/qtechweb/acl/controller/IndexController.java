package com.qtechweb.acl.controller;


import com.alibaba.fastjson.JSONObject;
import com.qtechweb.acl.service.IndexService;
import com.qtechweb.acl.service.impl.IndexServiceImpl;
import com.qtechweb.commonutils.result.ResMap;
import com.qtechweb.commonutils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lzz
 * @since 2020-10-08
 */
@Slf4j
@Api(value = "菜单接口")
@RestController
@RequestMapping("/acl/index")
public class IndexController {

    @Resource(type = IndexServiceImpl.class)
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @ApiOperation("根据token获取用户信息")
    @GetMapping("/info")
    public Result info() {
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return Result.success(userInfo);
    }

    /**
     * 获取菜单
     *
     * @return
     */
    @ApiOperation("获取菜单")
    @GetMapping("/menu")
    public Result getMenu() {
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return Result.success(ResMap.create().setKeyValue("permissionList", permissionList));
    }

    @PostMapping("/logout")
    public Result logout() {
        return Result.success();
    }

}

