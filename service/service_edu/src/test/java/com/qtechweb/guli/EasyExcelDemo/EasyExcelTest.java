package com.qtechweb.guli.EasyExcelDemo;

import com.alibaba.excel.EasyExcel;

public class EasyExcelTest {

    public static void main(String[] args) {
        //whiteExcel();
        readExcel();
    }

    /*写操作*/
    public static void whiteExcel() {
        //1.设置写入文件夹的地址和excel文件名称
        String filename = "D:\\ccc.xlsx";
        //2.调用实现写操作
        /*ArrayList<DemoData> list = new ArrayList<>();
        list.add(new DemoData().setSno(1).setSname("刘泽志"));
        list.add(new DemoData().setSno(2).setSname("黄渤"));
        list.add(new DemoData().setSno(3).setSname("李成鑫"));
        list.add(new DemoData().setSno(4).setSname("周连杰"));
        EasyExcel.write(filename,DemoData.class).sheet().doWrite(list);*/
    }

    /*读操作*/
    public static void readExcel() {
        String fileName = "D:\\ccc.xlsx";
        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();
    }

}
