package com.qtechweb.guli.EasyExcelDemo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
//@Accessors(chain = true)//这个注解会导致读取数据为空
public class DemoData {

    /*设置表头名称*/
    @ExcelProperty(value = "学生编号", index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;
}
