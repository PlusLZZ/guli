package com.qtechweb.eduservice.controller;


import com.qtechweb.commonutils.result.Result;
import com.qtechweb.eduservice.entity.subject.TreeSubject;
import com.qtechweb.eduservice.service.EduSubjectService;
import com.qtechweb.eduservice.service.impl.EduSubjectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lzz
 * @since 2020-08-31
 */
@Slf4j
@Api(value = "课程类别接口")
@CrossOrigin
@RestController
@RequestMapping("/edu/subject")
public class EduSubjectController {

    @Resource(type = EduSubjectServiceImpl.class)
    private EduSubjectService eduSubjectService;

    @ApiOperation("使用文件添加课程分类")
    @PostMapping(path = "/add/excel")
    public Result addForExcel(MultipartFile excel) {
        return Result.success(eduSubjectService.addSubjectForExcel(excel, eduSubjectService));
    }

    @ApiOperation("课程分类的树形展示")
    @GetMapping(path = "/getAll/tree")
    public Result<List<TreeSubject>> getAllForTree() {
        return Result.success(eduSubjectService.getAllForTree());
    }

}

