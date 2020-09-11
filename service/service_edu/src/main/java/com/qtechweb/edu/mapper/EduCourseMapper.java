package com.qtechweb.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qtechweb.edu.entity.EduCourse;
import com.qtechweb.edu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lzz
 * @since 2020-09-01
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /*根据课程id查询课程基本信息*/
    CoursePublishVo getCoursePublishVoById(String id);

}
