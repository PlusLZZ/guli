package com.qtechweb.eduservice.controller;

import com.qtechweb.commonutils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 模拟登录接口
 * </p>
 *
 * @author lzz
 * @since 2020-08-25
 */
@CrossOrigin
@Slf4j
@Api(value = "登录接口")
@RestController
@RequestMapping("/eduservice/user")
public class UserLoginController {

    @ApiOperation("登录接口")
    @PostMapping(path = "/login")
    public Result login() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", "admin");
        return Result.success(map);
    }

    @ApiOperation("用户信息接口")
    @GetMapping(path = "/info")
    public Result info() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "admin");
        map.put("avatar", "http://127.0.0.1:8847/images/ico/abc.jpg");
        map.put("roles", new String[]{"user", "boss", "admin"});
        return Result.success(map);
    }

}
