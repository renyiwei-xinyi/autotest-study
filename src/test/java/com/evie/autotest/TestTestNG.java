package com.evie.autotest;

import com.evie.autotest.annotation.JsonSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.Map;

public class TestTestNG {
    private static final Logger LOGGER = LogManager.getLogger(TestTestNG.class);

    @BeforeSuite
    void before1(){
        LOGGER.info(1);
    }

    @BeforeTest
    void before2(){
        LOGGER.info(2);

    }

    @BeforeClass
    void before3(){
        LOGGER.info(3);

    }

    @BeforeMethod
    void before4(){
        LOGGER.info(4);

    }



    @Test
    @Parameters
    void test(@Optional("{\"test\":12}") String o){
        LOGGER.info(o);
        //参数化测试 目前只支持 String 参数
    }

    @Test(enabled = false)
    void test_123(){
        // 忽略测试 和junit5 相比 就idea插件而言 会导致本地也无法执行 而不是在执行时进行过滤
    }

    @Parameters
    @Test(successPercentage = 20)
    void test_1231(@Optional("1") String s,@Optional("2") String a){
        Assert.assertEquals(s,"1");
        Assert.assertEquals(a,"1");
        LOGGER.info("PASS");

    }
}
