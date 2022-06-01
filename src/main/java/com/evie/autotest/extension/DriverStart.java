package com.evie.autotest.extension;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeDriverService;


public interface DriverStart {

    Logger LOGGER = LogManager.getLogger(DriverStart.class.getName());

    @BeforeAll
    static void Before() {
        LOGGER.info("================ Before all tests ================");
        //todo: 需要本地配置
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "src/test/resources/chromedriver.exe");
    }

    @AfterAll
    static void After() {
        LOGGER.info("================ After all tests ================");
    }


}
