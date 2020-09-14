package com.qtechweb.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qtechweb.commonutils.result.ResMap;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.edu.entity.EduCourse;
import com.qtechweb.edu.entity.EduTeacher;
import com.qtechweb.edu.service.EduCourseService;
import com.qtechweb.edu.service.EduTeacherService;
import com.qtechweb.edu.service.impl.EduCourseServiceImpl;
import com.qtechweb.edu.service.impl.EduTeacherServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页热门数据
 * </p>
 *
 * @author lzz
 * @since 2020-09-12
 */
@Slf4j
@Api(value = "首页热门数据")
@CrossOrigin
@RestController
@RequestMapping("/edu/hot")
public class IndexFrontController {

    @Resource(type = EduCourseServiceImpl.class)
    private EduCourseService courseService;

    @Resource(type = EduTeacherServiceImpl.class)
    private EduTeacherService teacherService;


    @Cacheable(cacheNames = {"courseTeacher"}, sync = true, key = "'hot'")
    @ApiOperation("查询前八条热门课程与名师")
    @GetMapping("/courseTeacher")
    public Result courseTeacher() {
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("view_count");
        courseWrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(courseWrapper);
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("level", "sort");
        teacherWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper);
        return Result.success(ResMap.create().setKeyValue("courseList", courseList)
                .setKeyValue("teacherList", teacherList));
    }
}
