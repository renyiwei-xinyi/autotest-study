package com.evie.autotest.controller;

import com.evie.autotest.api.TestService;
import com.evie.autotest.handler.exception.MyRuntiomeException;
import com.evie.autotest.vo.Result;
import com.evie.autotest.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试模块")
public class TestController {


    @Autowired
    private TestService testService;

    @GetMapping("/test1")
    @ApiOperation("测试接口1")
    @ApiModelProperty
    public Result test(@RequestParam("className")String className, @RequestParam("methodName")String... methodName){

        String test =  testService.run(className, methodName);
        if(!StringUtils.isEmpty(test)){
            return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", test);
        }
        return Result.error().codeAndMessage(ResultInfo.NOT_FOUND);
    }

    @GetMapping("/test2")
    @ApiOperation("测试接口2")
    @ApiModelProperty
    public Result test(@RequestParam("className")String className){

        String test =  testService.run(className);
        if(!StringUtils.isEmpty(test)){
            return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", test);
        }
        return Result.error().codeAndMessage(ResultInfo.NOT_FOUND);
    }

    @GetMapping("/exception")
    @ApiOperation("异常接口")
    @ApiModelProperty
    public Result test2(){
        throw new MyRuntiomeException("111", "自定义异常");
    }


}
