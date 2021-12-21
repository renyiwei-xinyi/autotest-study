package com.evie.autotest.platform.testcase.example.xUnit0410;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.platform.launcher.listeners.LoggingListener;

// 设置用例顺序
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestLife {

    private static final Logger LOGGER = LogManager.getLogger(TestLife.class);


    @BeforeAll
    static void before_all(TestInfo testInfo) {

        LOGGER.info("before all " + testInfo);
    }

    @BeforeEach
    void before_each(TestInfo testInfo) {
        LOGGER.info("before each " + testInfo);
    }

    @Order(2)
    @Test
    void test_12381(TestInfo testInfo) {
        Assertions.fail("报错了 怎么班");
        LOGGER.info("test 12381 " + testInfo);
    }

    @Order(1)
    @ParameterizedTest
    @NullSource
    void test_172389(String s, TestInfo testInfo) {
        LOGGER.info("test 172389 " + testInfo);
    }

    @Order(3)
    @RepeatedTest(3)
    void test_17398(RepetitionInfo repetitionInfo, TestInfo testInfo) {
        LOGGER.info("test 17398 " + testInfo);

        LOGGER.info("test 17398 " + repetitionInfo);

    }


    @AfterEach
    void after_each(TestInfo testInfo) {
        LOGGER.info("after each " + testInfo);
    }

    @AfterAll
    static void after_all(TestReporter testReporter, TestInfo testInfo) {
        LOGGER.info("after all " + testInfo);
        testReporter.publishEntry("test","nihao");
        LOGGER.info("after all " + JSON.toJSONString(testReporter, true));

    }

}
