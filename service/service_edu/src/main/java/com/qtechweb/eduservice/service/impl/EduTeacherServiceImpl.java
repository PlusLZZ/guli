package com.qtechweb.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.commonutils.result.PageUtils;
import com.qtechweb.eduservice.entity.EduTeacher;
import com.qtechweb.eduservice.entity.vo.TeacherQuery;
import com.qtechweb.eduservice.mapper.EduTeacherMapper;
import com.qtechweb.eduservice.service.EduTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-08-22
 */
@Service
@Slf4j
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Resource
    EduTeacherMapper mapper;

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
        wrapper.orderByDesc("gmt_create");
        return PageUtils.page(page(page, wrapper));
    }

    @Override
    public Boolean checkOutTeacherByName(String name) {
        if (StringUtils.isEmpty(name) || mapper.checkOutTeacherByName(name).equals(0)) {
            return true;
        }
        return false;
    }


}
