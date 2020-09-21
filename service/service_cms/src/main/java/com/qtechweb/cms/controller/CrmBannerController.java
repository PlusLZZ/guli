package com.qtechweb.cms.controller;


import com.qtechweb.cms.entity.CrmBanner;
import com.qtechweb.cms.service.CrmBannerService;
import com.qtechweb.cms.service.impl.CrmBannerServiceImpl;
import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author lzz
 * @since 2020-09-11
 */
@Slf4j
@Api(value = "首页banner接口")
@CrossOrigin
@RestController
@RequestMapping("/cms/banner")
public class CrmBannerController {

    @Resource(type = CrmBannerServiceImpl.class)
    private CrmBannerService bannerService;

    @CacheEvict(cacheNames = {"Banner"}, key = "'hotBanner'")
    @ApiOperation("新增首页banner")
    @PostMapping("/add")
    public Result add(@RequestBody CrmBanner banner) {
        AssertUtils.BooleanIsTrue(bannerService.save(banner), "添加banner失败");
        return Result.success();
    }

    @CacheEvict(cacheNames = {"Banner"}, key = "'hotBanner'")
    @ApiOperation("修改banner信息")
    @PostMapping(path = "/edit")
    public Result edit(@RequestBody CrmBanner banner) {
        AssertUtils.BooleanIsTrue(bannerService.updateById(banner), "更新banner信息失败");
        return Result.success();
    }

    @ApiOperation("查询banner信息")
    @GetMapping("/get")
    public Result get() {
        return Result.success(bannerService.getHotBanner());
    }

    @CacheEvict(cacheNames = {"Banner"}, key = "'hotBanner'")
    @ApiOperation("根据ID逻辑删除banner信息")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        AssertUtils.BooleanIsTrue(bannerService.removeById(id), "删除失败");
        return Result.success();
    }
}

