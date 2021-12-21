package com.evie.autotest.handler.exception;

import com.evie.autotest.vo.ResultInfo;

public class MyRuntiomeException extends RuntimeException{

    private String code;

    private String message;

    public MyRuntiomeException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public MyRuntiomeException(ResultInfo resultInfo) {
        this.code = resultInfo.getCode();
        this.message = resultInfo.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
