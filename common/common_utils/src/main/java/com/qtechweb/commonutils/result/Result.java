package com.qtechweb.commonutils.result;

import com.qtechweb.commonutils.enums.DefaultEnum;
import com.qtechweb.commonutils.exce.IDefaultEnum;
import com.qtechweb.commonutils.exception.DefaultException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel(value = "统一响应对象", description = "Response响应的Json对象")
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否执行成功")
    private Boolean isSuccess;
    @ApiModelProperty(value = "响应码")
    private Integer code;
    @ApiModelProperty(value = "响应消息")
    private String message;
    @ApiModelProperty(value = "响应数据")
    private T data;

    private Result() {
    }

    private Result setCodeMessage(IDefaultEnum defaultEnum) {
        this.code = defaultEnum.getCode();
        this.message = defaultEnum.getMessage();
        return this;
    }

    private Result setCodeMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    /*无数据的默认响应成功提示*/
    public static Result success() {
        return new Result().setIsSuccess(true).setCodeMessage(DefaultEnum.SUCCESS);
    }

    /*无数据的自定义响应成功提示*/
    public static Result success(IDefaultEnum defaultEnum) {
        return new Result().setIsSuccess(true).setCodeMessage(defaultEnum);
    }

    /*无数据的自定义响应码与成功信息*/
    @Deprecated
    public static Result success(Integer code, String message) {
        return new Result().setIsSuccess(true).setCodeMessage(code, message);
    }

    /*有数据的默认响应成功提示*/
    public static <T> Result success(T t) {
        return new Result().setIsSuccess(true).setCodeMessage(DefaultEnum.SUCCESS).setData(t);
    }

    /*有数据的自定义响应成功提示*/
    public static <T> Result success(IDefaultEnum defaultEnum, T t) {
        return new Result().setIsSuccess(true).setCodeMessage(defaultEnum).setData(t);
    }

    /*有数据的自定义响应码与成功信息*/
    @Deprecated
    public static <T> Result success(Integer code, String message, T t) {
        return new Result().setIsSuccess(true).setCodeMessage(code, message).setData(t);
    }

    /*默认失败返回*/
    public static Result fail() {
        return new Result().setIsSuccess(false).setCodeMessage(DefaultEnum.FAIL);
    }

    /*自定义失败码返回*/
    @Deprecated
    public static Result fail(Integer code, String message) {
        return new Result().setIsSuccess(false).setCodeMessage(code, message);
    }

    /*自定义枚举失败返回*/
    public static Result fail(IDefaultEnum defaultEnum) {
        return new Result().setIsSuccess(false).setCodeMessage(defaultEnum);
    }

    /*失败返回时带数据*/
    public static <T> Result fail(IDefaultEnum defaultEnum, T t) {
        return new Result().setIsSuccess(false).setCodeMessage(defaultEnum).setData(t);
    }

    /*传入错误类返回响应体*/
    public static Result fail(Exception e) {
        return e instanceof DefaultException ? new Result().setIsSuccess(false).setCodeMessage(((DefaultException) e).getCode(), e.getMessage())
                : new Result().setIsSuccess(false).setCodeMessage(50000, e.getMessage());
    }
}
