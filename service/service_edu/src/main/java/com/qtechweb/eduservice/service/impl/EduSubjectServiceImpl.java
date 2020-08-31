package com.qtechweb.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.eduservice.entity.EduSubject;
import com.qtechweb.eduservice.entity.excel.SubjectData;
import com.qtechweb.eduservice.listener.SubjectExcelListener;
import com.qtechweb.eduservice.mapper.EduSubjectMapper;
import com.qtechweb.eduservice.service.EduSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-08-31
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public Boolean addSubjectForExcel(MultipartFile file, EduSubjectService service) {
        try {
            InputStream stream = file.getInputStream();
            EasyExcel.read(stream, SubjectData.class, new SubjectExcelListener(service)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public EduSubject existOneSubject(String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        return getOne(wrapper);
    }

    @Override
    public EduSubject existTwoSubject(String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        return getOne(wrapper);
    }
}
