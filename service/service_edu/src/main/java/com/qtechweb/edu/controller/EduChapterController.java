package com.qtechweb.edu.controller;


import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.edu.entity.EduChapter;
import com.qtechweb.edu.service.EduChapterService;
import com.qtechweb.edu.service.impl.EduChapterServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lzz
 * @since 2020-09-01
 */
@Slf4j
@Api(value = "课程章节接口")
@CrossOrigin
@RestController
@RequestMapping("/edu/chapter")
public class EduChapterController {

    @Resource(type = EduChapterServiceImpl.class)
    private EduChapterService chapterService;

    @ApiOperation("查询课程大纲")
    @GetMapping("/get/line/{courseId}")
    public Result getLine(@PathVariable("courseId") String courseId) {
        return Result.success(chapterService.getChapterLine(courseId));
    }

    @ApiOperation("添加课程章节")
    @PostMapping("/add/chapter")
    public Result addChapter(@RequestBody EduChapter chapter) {
        AssertUtils.BooleanIsTrue(chapterService.save(chapter), "添加失败");
        return Result.success();
    }

    @ApiOperation("修改章节信息")
    @PostMapping("/edit/chapter")
    public Result editChapter(@RequestBody EduChapter chapter) {
        AssertUtils.BooleanIsTrue(chapterService.updateById(chapter), "修改失败");
        return Result.success();
    }

    @ApiOperation("删除章节")
    @GetMapping("/delete/chapter/{id}")
    public Result deleteChapter(@PathVariable("id") String id) {
        AssertUtils.BooleanIsTrue(chapterService.removeById(id), "删除失败");
        return Result.success();
    }

}

