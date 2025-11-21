package com.easechat.easechat.common.exception.handler;

import com.easechat.easechat.common.api.R;
import com.easechat.easechat.common.exception.ServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public R serviceExceptionHandler(ServiceException e) {
        e.printStackTrace();
        return R.fail(e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        e.printStackTrace();
        return R.fail("未知的异常");
    }
}
