package com.easechat.easechat.common.api;

public enum ResultCode implements IResultCode {
    SUCCESS(200, "SUCCCESS"),
    FAILURE(400, "FAIL"),

    ;

    final int code;
    final String message;

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private ResultCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
