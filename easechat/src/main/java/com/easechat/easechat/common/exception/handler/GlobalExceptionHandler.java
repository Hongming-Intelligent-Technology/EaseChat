package com.easechat.easechat.common.exception.handler;

import com.easechat.easechat.common.api.R;
import com.easechat.easechat.common.exception.ServiceException;
import java.sql.SQLSyntaxErrorException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public R<String> serviceExceptionHandler(ServiceException e) {
        e.printStackTrace();
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<String> badRequestExceptionHandler(HttpMessageNotReadableException e) {
        e.printStackTrace();
        return R.fail("Invalid request payload");
    }


    @ExceptionHandler(Exception.class)
    public R<String> exceptionHandler(Exception e) {
        e.printStackTrace();
        if (hasCause(e, CannotGetJdbcConnectionException.class)) {
            return R.fail("Database connection failed. Please check MySQL and database configuration.");
        }
        if (hasCause(e, SQLSyntaxErrorException.class)) {
            return R.fail("Database is not initialized. Please import db.sql first.");
        }
        return R.fail("System exception, please check server logs.");
    }

    private boolean hasCause(Throwable throwable, Class<? extends Throwable> targetType) {
        Throwable current = throwable;
        while (current != null) {
            if (targetType.isInstance(current)) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}
