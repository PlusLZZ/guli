package com.qtechweb.eduservice.controller;


import com.qtechweb.commonutils.result.Result;
import com.qtechweb.eduservice.entity.EduVideo;
import com.qtechweb.eduservice.service.EduVideoService;
import com.qtechweb.eduservice.service.impl.EduVideoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lzz
 * @since 2020-09-01
 */
@Slf4j
@Api(value = "课程视频接口")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/edu-video")
public class EduVideoController {

    @Resource(type = EduVideoServiceImpl.class)
    private EduVideoService eduVideoService;

    @ApiOperation("添加小节")
    @PostMapping("/add")
    public Result add(@RequestBody EduVideo video) {
        return Result.success(eduVideoService.save(video));
    }

    //TODO 后面需要完善删除视频
    @ApiOperation("删除小节")
    @DeleteMapping("/delete/{id}")
    public Result add(@PathVariable("id") String id) {
        return Result.success(eduVideoService.removeById(id));
    }

    @ApiOperation("查询小节")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable("id") String id) {
        return Result.success(eduVideoService.getById(id));
    }

    @ApiOperation("修改小节")
    @PostMapping("/edit")
    public Result get(@RequestBody EduVideo video) {
        return Result.success(eduVideoService.updateById(video));
    }

}

