package com.qtechweb.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qtechweb.commonutils.result.PageUtils;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.eduservice.entity.EduTeacher;
import com.qtechweb.eduservice.entity.vo.TeacherQuery;
import com.qtechweb.eduservice.service.EduTeacherService;
import com.qtechweb.eduservice.service.impl.EduTeacherServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Api(value = "讲师接口")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Resource(type = EduTeacherServiceImpl.class)
    private EduTeacherService eduTeacherService;


    @ApiOperation("查询讲师表所有数据")
    @GetMapping(path = "/findAll")
    public Result<List<EduTeacher>> findAll() {
        return Result.success(eduTeacherService.list());
    }

    @ApiOperation("根据ID查询讲师")
    @GetMapping(path = "/find/{id}")
    public Result<List<EduTeacher>> findById(@PathVariable("id") Long id) {
        return Result.success(eduTeacherService.getById(id));
    }

    @ApiOperation("通过id删除讲师")
    @DeleteMapping(path = "/delete/{id}")
    public Result deleteById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable("id") String id) {
        return eduTeacherService.removeById(id) ? Result.success() : Result.fail();
    }

    @ApiOperation("分页查询讲师")
    @GetMapping(path = "/page/{size}/{current}")
    public Result<PageUtils> page(@PathVariable("size") Long size,
                                  @PathVariable("current") Long current) {
        Page<EduTeacher> page = new Page<>();
        page.setCurrent(current).setSize(size);
        eduTeacherService.page(page, null);
        return Result.success(PageUtils.page(page));
    }

    /*
     * 重点提示一下,无论是Get请求还是Post请求都是可以接收到请求行与请求体的数据
     * 需要注意的是Get请求在接收请求体中数据的时候,不能使用@RequestBody注解
     * 一旦使用@RequestBody来接收请求体对象,必须是Post等请求
     * */
    @ApiOperation("讲师条件查询带分页")
    @PostMapping(path = "/pageCondition/{size}/{current}")
    public Result<PageUtils> pageCondition(@PathVariable("size") Long size,
                                           @PathVariable("current") Long current,
                                           @RequestBody(required = false) TeacherQuery teacherQuery) {
        return Result.success(eduTeacherService.pageCondition(current, size, teacherQuery));
    }

    @ApiOperation("添加讲师")
    @PostMapping(path = "/add")
    public Result add(@ApiParam @RequestBody EduTeacher teacher) {
        return eduTeacherService.save(teacher) ? Result.success() : Result.fail();
    }

    @ApiOperation("讲师信息修改")
    @PostMapping(path = "/update")
    public Result update(@RequestBody EduTeacher teacher) {
        return eduTeacherService.updateById(teacher) ? Result.success() : Result.fail();
    }

    @ApiOperation("讲师姓名校验")
    @GetMapping(path = "/check/name/{name}")
    public Result checkoutForName(@PathVariable(value = "name", required = false) String name) {
        return Result.success(eduTeacherService.checkOutTeacherByName(name));
    }

    @ApiOperation("下拉框讲师列表显示")
    @GetMapping(path = "/findAll/comBox")
    public Result combox() {
        return Result.success(eduTeacherService.queryTeacherComBox());
    }
}

