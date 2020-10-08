package com.qtechweb.ucenter.controller;


import com.qtechweb.commonutils.result.Result;
import com.qtechweb.ucenter.service.UcenterMemberService;
import com.qtechweb.ucenter.service.impl.UcenterMemberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 控制器
 * </p>
 *
 * @author lzz
 * @since 2020-10-07
 */
@Slf4j
@Api(value = "用户中心会员信息统计接口")
@RestController
@RequestMapping("/ucenter/info")
public class UcenterInfoController {

    @Resource(type = UcenterMemberServiceImpl.class)
    private UcenterMemberService ucenterMemberService;

    @ApiOperation("查询某天注册人数")
    @GetMapping(path = "/countRegister/{day}")
    public Result countRegister(@PathVariable("day") String day) {
        return Result.success(ucenterMemberService.countRegister(day));
    }


}

