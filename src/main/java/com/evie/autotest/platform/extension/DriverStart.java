package com.evie.autotest.platform.extension;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public interface DriverStart {

    Logger LOGGER = LogManager.getLogger(DriverStart.class.getName());

    @BeforeAll
    static void Before() {
        LOGGER.info("================ Before all tests ================");
        //System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, "src/test/resources/msedgedriver.exe");
    }

    @AfterAll
    static void After() {
        LOGGER.info("================ After all tests ================");
    }


}
