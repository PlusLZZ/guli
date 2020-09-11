package com.qtechweb.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtechweb.commonutils.result.PageUtils;
import com.qtechweb.edu.entity.EduTeacher;
import com.qtechweb.edu.entity.teacher.TeacherComBox;
import com.qtechweb.edu.entity.vo.TeacherQuery;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lzz
 * @since 2020-08-22
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //讲师条件查询分页
    PageUtils pageCondition(Long current, Long size, TeacherQuery query);

    //查询数据库是否有这个讲师名称
    Boolean checkOutTeacherByName(String name);

    /*讲师列表下拉框*/
    List<TeacherComBox> queryTeacherComBox();

}
