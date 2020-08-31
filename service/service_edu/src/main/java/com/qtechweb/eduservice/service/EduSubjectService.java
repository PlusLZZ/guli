package com.qtechweb.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtechweb.eduservice.entity.EduSubject;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lzz
 * @since 2020-08-31
 */
public interface EduSubjectService extends IService<EduSubject> {

    Boolean addSubjectForExcel(MultipartFile file, EduSubjectService service);

    /*根据名称判断是否有这个一级分类*/
    EduSubject existOneSubject(String name);

    /*根据名称判断是否有这个二级分类*/
    EduSubject existTwoSubject(String name, String pid);

}
