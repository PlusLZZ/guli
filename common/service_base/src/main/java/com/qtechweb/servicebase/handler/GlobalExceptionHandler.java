package com.qtechweb.servicebase.handler;

import com.qtechweb.commonutils.enums.DefaultEnum;
import com.qtechweb.commonutils.exception.DefaultException;
import com.qtechweb.commonutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.concurrent.CompletionException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /*
     * 生产环境
     * */
    private final static String ENV_PROD = "prod";

    /*
     * 当前环境
     * */
    @Value("${spring.profiles.active}")
    private String profile;

    /*
     * 默认异常
     * */
    @ExceptionHandler(value = DefaultException.class)
    @ResponseBody
    public Result handleDefaultException(DefaultException e) {
        log.error(e.getMessage(), e);
        return Result.fail(e);
    }

    /*
     * controller上一层相关异常
     * */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            // BindException.class,
            // MethodArgumentNotValidException.class
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class,
            CompletionException.class
    })
    @ResponseBody
    public Result handleServletException(Exception e) {
        log.error(e.getMessage(), e);
        if (ENV_PROD.equals(profile)) {
            // 生产环境抛出简单异常
            return Result.fail(DefaultEnum.FAIL);
        }
        return Result.fail(e);
    }

    /*
     * 参数绑定异常
     * */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result handleBindException(BindException e) {
        log.error("参数绑定异常", e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /*
     * 参数校验异常
     * */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常", e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /*
     * 包装绑定异常结果
     * */
    private Result wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();

        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(", ");
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());

        }

        return Result.fail(50000, msg.substring(2));
    }

    /*
     * 未捕获的其它异常
     * */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.fail(e);
    }


}
