package com.qtechweb.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.qtechweb.commonutils.exception.DefaultException;
import com.qtechweb.eduservice.entity.EduSubject;
import com.qtechweb.eduservice.entity.excel.SubjectData;
import com.qtechweb.eduservice.service.EduSubjectService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


/*
 * 可以做数量优化,一次存30个
 * 判断需要插入的,进行批处理操作
 * 待优化.............
 * */
@Slf4j
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    /*此监听器无法进行注入,可以使用构造器注入的方式进行注入*/

    public EduSubjectService subjectService;


    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @SneakyThrows(value = DefaultException.class)
    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if (null == data || null == data.getOneSubjectName() || null == data.getTwoSubjectName()) {
            throw new DefaultException(20001, "文件读取异常,请修改格式!");

        }
        log.info(data.toString());
        save(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    private void save(SubjectData data) {
        EduSubject oneSubject = subjectService.existOneSubject(data.getOneSubjectName());
        if (null == oneSubject) {
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(data.getOneSubjectName());
            //默认回显id给对象
            subjectService.save(oneSubject);
        }
        EduSubject twoSubject = subjectService.existTwoSubject(data.getTwoSubjectName(), oneSubject.getId());
        if (null == twoSubject) {
            twoSubject = new EduSubject();
            twoSubject.setTitle(data.getTwoSubjectName());
            twoSubject.setParentId(oneSubject.getId());
            subjectService.save(twoSubject);
        }
    }


}
