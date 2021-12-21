package com.evie.autotest.platform.extension;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

public interface TestLifecycleLogger {

    static final Logger LOGGER = LogManager.getLogger(TestLifecycleLogger.class.getName());

    @BeforeAll
    static void Before() {
        LOGGER.info("================ Before all tests ================");
    }

    @AfterAll
    static void After() {
        LOGGER.info("================ After all tests ================");

    }

}
