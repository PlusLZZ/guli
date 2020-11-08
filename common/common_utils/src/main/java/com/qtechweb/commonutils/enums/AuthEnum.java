package com.qtechweb.commonutils.enums;

import com.qtechweb.commonutils.exce.IDefaultEnum;

public enum AuthEnum implements IDefaultEnum {
    NOT_LOGGED(21001, "用户未登录"),
    USER_DISABLED(21002, "用户已被停用"),
    NOT_FOUND(21003, "未找到此用户"),
    NOT_AUTH(21004, "鉴权失败"),
    LOGOUT_SUCCESS(21005, "登出成功"),
    LOGIN_FAIL(21006, "登录失败"),
    LOGIN_SUCCESS(21007, "登录成功");


    private Integer code;
    private String message;

    AuthEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
