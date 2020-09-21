package com.qtechweb.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.edu.entity.EduSubject;
import com.qtechweb.edu.entity.excel.SubjectData;
import com.qtechweb.edu.entity.subject.TreeSubject;
import com.qtechweb.edu.listener.SubjectExcelListener;
import com.qtechweb.edu.mapper.EduSubjectMapper;
import com.qtechweb.edu.service.EduSubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-08-31
 */
@Service
@Slf4j
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

    @Cacheable(cacheNames = {"subject"}, sync = true, key = "#root.methodName")
    @Override
    public List<TreeSubject> getAllForTree() {
        /*List<TreeSubject> treeSubjects=new ArrayList<>();
        QueryWrapper<EduSubject> wrapper1=new QueryWrapper();
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper1.eq("parent_id","0");
        this.list(wrapper1).forEach(es -> {
            TreeSubject subject = new TreeSubject();
            subject.setId(es.getId()).setTitle(es.getTitle());
            List<TreeSubject> child=new ArrayList<>();
            wrapper2.eq("parent_id",es.getId());
            this.list(wrapper2).forEach(es2 -> child.add(new TreeSubject().setId(es2.getId()).setTitle(es2.getTitle())));
            subject.setChildren(child);
            treeSubjects.add(subject);
            wrapper2.clear();
        });
        return treeSubjects;*/
        //此方法操作数据库过于频繁,选择使用一次查询,内存中操作的方式
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper();
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper1.eq("parent_id", "0");
        wrapper2.ne("parent_id", "0");
        List<EduSubject> listOne = this.list(wrapper1);
        List<EduSubject> listTwo = this.list(wrapper2);

        List<TreeSubject> finalList = new ArrayList<>();
        listOne.forEach(one -> {
            TreeSubject subject = new TreeSubject();
            subject.setId(one.getId()).setTitle(one.getTitle());
            List<TreeSubject> twoL = new ArrayList<>();
            List<EduSubject> collect = listTwo.stream().filter(two -> one.getId().equals(two.getParentId())).collect(Collectors.toList());
            collect.forEach(col -> twoL.add(new TreeSubject().setId(col.getId()).setTitle(col.getTitle())));
            subject.setChildren(twoL);
            finalList.add(subject);
            collect = null;
        });
        return finalList;
    }
}
