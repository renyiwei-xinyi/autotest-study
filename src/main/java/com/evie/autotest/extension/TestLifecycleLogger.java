package com.evie.autotest.extension;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

public interface TestLifecycleLogger {

    static final Logger logger = LogManager.getLogger(TestLifecycleLogger.class.getName());

    @BeforeAll
    static void Before() {
        logger.info("================ Before all tests ================");
    }

    @AfterAll
    static void After() {
        logger.info("================ After all tests ================");

    }

}
