package com.qtechweb.eduservice.controller;


import com.qtechweb.commonutils.result.Result;
import com.qtechweb.eduservice.entity.EduTeacher;
import com.qtechweb.eduservice.service.EduTeacherService;
import com.qtechweb.eduservice.service.impl.EduTeacherServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lzz
 * @since 2020-08-22
 */
@Api(value = "讲师接口")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Resource(type = EduTeacherServiceImpl.class)
    private EduTeacherService eduTeacherService;

    //查询讲师表所有数据  http://localhost:8001/eduservice/edu-teacher
    @ApiOperation("查询讲师表所有数据")
    @GetMapping(path = "/findAll")
    public Result<List<EduTeacher>> findAll() {
        return Result.success(eduTeacherService.list());
    }

    //通过id删除讲师
    @ApiOperation("通过id删除讲师")
    @DeleteMapping(path = "/delete/{id}")
    public Result deleteById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable("id") String id) {
        return eduTeacherService.removeById(id) ? Result.success() : Result.fail();
    }
}

