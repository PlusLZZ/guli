package com.qtechweb.eduservice.controller;


import com.qtechweb.commonutils.result.ResMap;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.eduservice.entity.EduCourse;
import com.qtechweb.eduservice.entity.vo.CourseInfoVo;
import com.qtechweb.eduservice.entity.vo.CourseQuery;
import com.qtechweb.eduservice.service.EduCourseDescriptionService;
import com.qtechweb.eduservice.service.EduCourseService;
import com.qtechweb.eduservice.service.impl.EduCourseDescriptionServiceImpl;
import com.qtechweb.eduservice.service.impl.EduCourseServiceImpl;
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
@Api(value = "课程基本信息接口")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/edu-course")
public class EduCourseController {

    @Resource(type = EduCourseServiceImpl.class)
    private EduCourseService courseService;

    @Resource(type = EduCourseDescriptionServiceImpl.class)
    private EduCourseDescriptionService courseDescriptionService;

    @ApiOperation("添加课程基本信息")
    @PostMapping("/add/basic")
    public Result addForBasic(@RequestBody CourseInfoVo courseInfoVo) {
        return Result.success(ResMap.create().setKeyValue("id", courseService.saveCourseInfo(courseInfoVo)));
    }

    @ApiOperation("根据ID查询课程基本信息")
    @GetMapping(path = "/getInfo/{courseId}")
    public Result getInfo(@PathVariable("courseId") String courseId) {
        return Result.success(courseService.getCourseInfoById(courseId));
    }

    @ApiOperation("更新课程基本信息")
    @PostMapping("/update/info")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        return Result.success(courseService.updateCourseInfo(courseInfoVo));
    }

    @ApiOperation("根据课程id查询课程确认发布的基本信息")
    @GetMapping("/get/publish/{id}")
    public Result getPublishInfo(@PathVariable("id") String id) {
        return Result.success(courseService.getCoursePublishVoById(id));
    }

    @ApiOperation("课程最终发布")
    @GetMapping(path = "/publish/{id}")
    public Result publish(@PathVariable("id") String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        return Result.success(courseService.updateById(eduCourse));
    }

    @ApiOperation("课程分页条件查询")
    @PostMapping(path = "/list/{size}/{current}")
    public Result list(@RequestBody(required = false) CourseQuery query, @PathVariable("size") Long size, @PathVariable("current") Long current) {
        return Result.success(courseService.pageCondition(current, size, query));
    }

    @ApiOperation("通过id删除课程")
    @DeleteMapping(path = "/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        return Result.success(courseService.removeById(id));
    }
}

