package com.qtechweb.commonutils.enums;

import com.qtechweb.commonutils.exce.IDefaultEnum;

public enum OrderEnum implements IDefaultEnum {

    PAY_FAIL(22001, "支付失败"),
    PAY_RUN(22002, "正在支付");


    private Integer code;
    private String message;

    OrderEnum(Integer code, String message) {
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
