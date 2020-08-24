package com.qtechweb.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.commonutils.result.PageUtils;
import com.qtechweb.eduservice.entity.EduTeacher;
import com.qtechweb.eduservice.entity.vo.TeacherQuery;
import com.qtechweb.eduservice.mapper.EduTeacherMapper;
import com.qtechweb.eduservice.service.EduTeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-08-22
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {


    @Override
    public PageUtils pageCondition(Long current, Long size, TeacherQuery query) {
        Page<EduTeacher> page = new Page<>();
        page.setSize(size).setCurrent(current);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query.getName()))
            wrapper.like("name", query.getName());
        if (!org.springframework.util.StringUtils.isEmpty(query.getLevel()))
            wrapper.eq("level", query.getLevel());
        if (StringUtils.isNotBlank(query.getBegin()))
            wrapper.ge("gmt_create", query.getBegin());
        if (StringUtils.isNotBlank(query.getEnd()))
            wrapper.le("gmt_create", query.getEnd());
        return PageUtils.page(page(page, wrapper));
    }
}
