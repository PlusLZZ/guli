package com.qtechweb.commonutils.enums;

import com.qtechweb.commonutils.exce.IDefaultEnum;


/*
 * 自定义默认异常
 * */
public enum DefaultEnum implements IDefaultEnum {
    SUCCESS(200, "成功"),
    ACCEPTED(202, "请求已接收,无响应"),
    NO_CONTENT(204, "无数据,已返回头信息"),
    BAD_REQUEST(400, "请求语法错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "权限不足"),
    NOT_FOUND(404, "无资源"),
    FAIL(500, "失败"),
    TIMEOUT(504, "超时");


    private Integer code;
    private String message;

    DefaultEnum(Integer code, String message) {
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
