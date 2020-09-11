package com.qtechweb.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtechweb.commonutils.result.PageUtils;
import com.qtechweb.edu.entity.EduCourse;
import com.qtechweb.edu.entity.vo.CourseInfoVo;
import com.qtechweb.edu.entity.vo.CoursePublishVo;
import com.qtechweb.edu.entity.vo.CourseQuery;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lzz
 * @since 2020-09-01
 */
public interface EduCourseService extends IService<EduCourse> {

    /*添加课程基本信息*/
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /*根据id获取课程基本信息*/
    CourseInfoVo getCourseInfoById(String courseId);

    /*课程信息更新*/
    Boolean updateCourseInfo(CourseInfoVo courseInfoVo);

    /*根据课程id查询课程基本信息*/
    CoursePublishVo getCoursePublishVoById(String id);

    //课程条件查询分页
    PageUtils pageCondition(Long current, Long size, CourseQuery query);
}
