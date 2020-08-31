package com.qtechweb.guli.EasyExcelDemo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {


    /*一行一行读取*/
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println(data);
        context.readRowHolder().getCellMap().forEach((sno, sname) -> {
            System.out.println("sno: " + sno + " sname: " + sname);
        });
    }

    /*读取表头内容*/
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        headMap.forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
    }

    /*读取完成之后的方法*/
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
