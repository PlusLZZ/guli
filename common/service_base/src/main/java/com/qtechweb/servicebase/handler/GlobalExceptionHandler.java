package com.qtechweb.servicebase.handler;

import com.qtechweb.commonutils.exception.DefaultException;
import com.qtechweb.commonutils.result.Result;
import com.qtechweb.servicebase.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result SystemException(HttpServletRequest request, Exception ex) {
        return consoleError(request, ex);
    }

    @ExceptionHandler(value = DefaultException.class)
    @ResponseBody
    public Result DefaultException(HttpServletRequest request, DefaultException ex) {
        return consoleError(request, ex);
    }

    private Result consoleError(HttpServletRequest request, Exception ex) {

        log.error("接口地址:" + request.getRequestURL());
        log.error("请求类型: " + request.getMethod() + "  传入参数为:");
        request.getParameterMap().forEach((key, value) -> {
            log.error(key + " : " + value);
        });
        log.error(ex.getLocalizedMessage());
        log.error(ExceptionUtil.getMessage(ex));
        //ex.printStackTrace();
        return Result.fail(ex);
    }


}
