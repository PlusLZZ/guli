package com.qtechweb.vod.controller;

import com.qtechweb.commonutils.result.ResMap;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.vod.service.VodService;
import com.qtechweb.vod.service.impl.VodServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 视频点播接口
 * </p>
 *
 * @author lzz
 * @since 2020-09-06
 */
@Slf4j
@Api(value = "视频点播接口")
@CrossOrigin
@RestController
@RequestMapping("/vod")
public class VodController {

    @Resource(type = VodServiceImpl.class)
    private VodService vodService;

    @ApiOperation("上传视频到阿里云")
    @PostMapping("/upload")
    public Result upload(MultipartFile video) {
        return Result.success(ResMap.create().setKeyValue("videoId", vodService.uploadVideoAli(video)));
    }

    @ApiOperation("通过视频ID删除视频")
    @DeleteMapping("/delete/{id}")
    public Result deleteVideoById(@PathVariable("id") String id) {
        log.info("被删除的视频ID为: " + id);
        return vodService.deleteVideoById(id) ? Result.success() : Result.fail(20001, "删除视频失败");
    }

    @ApiOperation("通过ID获取视频播放凭证")
    @GetMapping("/getAuth/{id}")
    public Result getPath(@PathVariable("id") String id) {
        return Result.success(vodService.getAuth(id));
    }


}
