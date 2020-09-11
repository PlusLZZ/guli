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

    public DefaultException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    //传入自定义的枚举,可以自定义异常信息
    public DefaultException(IDefaultEnum defaultEnum) {
        super(defaultEnum.getMessage());
        this.code = defaultEnum.getCode();
    }

    public DefaultException() {
        super("未知异常");
        this.code = 50000;
    }

}
