package com.evie.autotest.vo;

import java.util.HashMap;
import java.util.Map;

public class Result {


    private boolean status;

    private String code;

    private String message;

    Map<String, Object> data = new HashMap<>();


    public Result() {

    }

    public static Result success() {
        Result result = new Result();
        result.status = true;
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.status = false;
        return result;
    }

    public Result code(String code) {
        this.setCode(code);
        return this;
    }

    public Result message(String msg) {
        this.setMessage(msg);
        return this;
    }

    public Result codeAndMessage(String code, String msg) {
        this.setCode(code);
        this.setMessage(msg);
        return this;
    }

    public Result codeAndMessage(ResultInfo resultInfo) {
        this.setCode(resultInfo.getCode());
        this.setMessage(resultInfo.getMessage());
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);

        return this;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }


}
