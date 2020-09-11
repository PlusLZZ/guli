package com.qtechweb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.commonutils.exception.AssertUtils;
import com.qtechweb.commonutils.result.PageUtils;
import com.qtechweb.edu.entity.EduCourse;
import com.qtechweb.edu.entity.EduCourseDescription;
import com.qtechweb.edu.entity.vo.CourseInfoVo;
import com.qtechweb.edu.entity.vo.CoursePublishVo;
import com.qtechweb.edu.entity.vo.CourseQuery;
import com.qtechweb.edu.mapper.EduCourseMapper;
import com.qtechweb.edu.service.EduCourseDescriptionService;
import com.qtechweb.edu.service.EduCourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-09-01
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Resource(type = EduCourseDescriptionServiceImpl.class)
    private EduCourseDescriptionService descriptionService;

    @Transactional
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        eduCourse.setSubjectId(courseInfoVo.getSubjectIds()[1]);
        eduCourse.setSubjectParentId(courseInfoVo.getSubjectIds()[0]);
        boolean save = this.save(eduCourse);
        AssertUtils.BooleanIsTrue(save, 20001, "添加课程信息失败");
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfoVo.getDescription());
        description.setId(eduCourse.getId());
        boolean save1 = descriptionService.save(description);
        AssertUtils.BooleanIsTrue(save1, 20001, "添加课程信息失败");
        return eduCourse.getId();
    }

    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        EduCourse course = getById(courseId);
        AssertUtils.ObjectNotNull(course, "获取课程信息失败");
        EduCourseDescription courseDescription = descriptionService.getById(courseId);
        AssertUtils.ObjectNotNull(courseDescription, "获取课程信息失败");
        CourseInfoVo vo = new CourseInfoVo();
        BeanUtils.copyProperties(course, vo);
        vo.setDescription(courseDescription.getDescription());
        vo.setSubjectIds(new String[]{course.getSubjectParentId(), course.getSubjectId()});
        return vo;
    }

    @Transactional
    @Override
    public Boolean updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        eduCourse.setSubjectParentId(courseInfoVo.getSubjectIds()[0]);
        eduCourse.setSubjectId(courseInfoVo.getSubjectIds()[1]);
        AssertUtils.BooleanIsTrue(updateById(eduCourse), "课程信息修改失败");
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(courseInfoVo.getId());
        AssertUtils.BooleanIsTrue(descriptionService.updateById(courseDescription), "课程信息修改失败");
        return true;
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.getCoursePublishVoById(id);
    }

    @Override
    public PageUtils pageCondition(Long current, Long size, CourseQuery query) {
        Page<EduCourse> page = new Page<>();
        page.setSize(size).setCurrent(current);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query.getName()))
            wrapper.like("title", query.getName());
        if (StringUtils.isNotBlank(query.getState()))
            wrapper.eq("status", query.getState());
        if (StringUtils.isNotBlank(query.getBegin()))
            wrapper.ge("gmt_create", query.getBegin());
        if (StringUtils.isNotBlank(query.getEnd()))
            wrapper.le("gmt_create", query.getEnd());
        wrapper.orderByDesc("gmt_create");
        return PageUtils.page(page(page, wrapper));
    }
}
