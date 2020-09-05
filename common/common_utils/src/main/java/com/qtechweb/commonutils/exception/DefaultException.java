package com.qtechweb.commonutils.exception;

import com.qtechweb.commonutils.exce.IDefaultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class DefaultException extends RuntimeException {

    private Integer code;
    private String message;

    public DefaultException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    //传入自定义的枚举,可以自定义异常信息
    public DefaultException(IDefaultEnum defaultEnum) {
        this.code = defaultEnum.getCode();
        this.message = defaultEnum.getMessage();
    }

    public DefaultException() {
        this.code = 100;
        this.message = "异常信息未传入";
    }

}
