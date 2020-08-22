package com.qtechweb.eduservice.controller;


import com.qtechweb.eduservice.entity.EduTeacher;
import com.qtechweb.eduservice.service.EduTeacherService;
import com.qtechweb.eduservice.service.impl.EduTeacherServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Resource(type = EduTeacherServiceImpl.class)
    private EduTeacherService eduTeacherService;

    //查询讲师表所有数据
    @GetMapping(path = "/findAll")
    public List<EduTeacher> findAll(){
        List<EduTeacher> list = eduTeacherService.list();
        return list;
    }
}

