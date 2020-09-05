package com.qtechweb.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qtechweb.eduservice.entity.EduTeacher;
import com.qtechweb.eduservice.entity.teacher.TeacherComBox;

import java.util.List;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author lzz
 * @since 2020-08-22
 */
public interface EduTeacherMapper extends BaseMapper<EduTeacher> {

    //查询数据库是否有这个讲师名称
    Integer checkOutTeacherByName(String name);

    /*讲师列表下拉框*/
    List<TeacherComBox> queryTeacherComBox();

}
