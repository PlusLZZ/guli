package com.qtechweb.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qtechweb.commonutils.result.PageUtils;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.edu.entity.EduTeacher;
import com.qtechweb.edu.service.EduTeacherService;
import com.qtechweb.edu.service.impl.EduTeacherServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前台名师分页
 * </p>
 *
 * @author lzz
 * @since 2020-09-19
 */
@Slf4j
@Api(value = "前台名师分页")
@CrossOrigin
@RestController
@RequestMapping("/edu/front/teacher")
public class TeacherFrontController {

    @Resource(type = EduTeacherServiceImpl.class)
    private EduTeacherService teacherService;

    @Cacheable(cacheNames = {"frontTeacherPage"}, sync = true, key = "#size+':'+#current")
    @ApiOperation("首页的分页名师")
    @PostMapping(path = "/page/{size}/{current}")
    public Result page(@PathVariable("size") Long size,
                       @PathVariable("current") Long current) {
        Page<EduTeacher> page = new Page<>();
        page.setSize(size).setCurrent(current);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return Result.success(PageUtils.page(teacherService.page(page, wrapper), 7));
    }

    @ApiOperation("讲师的详情信息")
    @PostMapping(path = "/info/{id}")
    public Result page(@PathVariable("id") Long id) {
        return Result.success(teacherService.getTeacherInfo(id));
    }


}
