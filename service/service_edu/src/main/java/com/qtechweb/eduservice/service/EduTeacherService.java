package com.qtechweb.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtechweb.commonutils.result.PageUtils;
import com.qtechweb.eduservice.entity.EduTeacher;
import com.qtechweb.eduservice.entity.vo.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lzz
 * @since 2020-08-22
 */
public interface EduTeacherService extends IService<EduTeacher> {

    PageUtils pageCondition(Long current, Long size, TeacherQuery query);

}
