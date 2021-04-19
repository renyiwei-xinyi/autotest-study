package com.evie.example.web0414;

import com.evie.autotest.extension.DriverStart;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

public class CeshirenLoginTest implements DriverStart {

    private static ChromeDriver driver;
    private static CeshirenPage page;

    @BeforeAll
    static void before_all() {
        driver = new ChromeDriver(new ChromeOptions().setHeadless(false));
        page = PageFactory.initElements(driver, CeshirenPage.class);

    }

    @Test
    void test_login() throws InterruptedException {
        page.getUrl();
        page.click(page.search_button);
        page.sendKeys(page.search_term, "微信小程序自动化");
        page.click(page.search_text);
        Thread.sleep(1000);
    }

    @AfterAll
    static void after_all() {
        driver.close();
    }
}
