package com.qtechweb.commonutils.exce;


/*
 * 提供自定义枚举的接口,实现这个接口的的枚举可以直接放入自定义异常中
 * */
public interface IDefaultEnum {
    Integer getCode();

    String getMessage();
}
