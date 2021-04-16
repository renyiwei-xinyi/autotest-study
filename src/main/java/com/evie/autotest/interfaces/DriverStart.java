package com.evie.autotest.interfaces;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeDriverService;

public interface DriverStart {

    static final Logger logger = LogManager.getLogger(DriverStart.class.getName());

    @BeforeAll
    static void Before() {
        logger.info("================ Before all tests ================");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "src/test/resources/chromedriver.exe");
    }

    @AfterAll
    static void After() {
        logger.info("================ After all tests ================");
    }


}
