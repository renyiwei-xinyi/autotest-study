package com.evie.autotest.handler.exception;


import com.evie.autotest.vo.Result;
import com.evie.autotest.vo.ResultInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalException {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e){
        e.printStackTrace();

        return Result.error().codeAndMessage(ResultInfo.GLOBAL_ERROR);
    }

    @ExceptionHandler(MyRuntiomeException.class)
    @ResponseBody
    public Result exception(MyRuntiomeException e){
        e.printStackTrace();

        return Result.error().codeAndMessage(e.getCode(), e.getMessage());
    }
}
