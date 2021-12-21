package com.evie.autotest.platform.testcase.example;

import com.evie.autotest.platform.extension.DriverStart;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;

public class TestSelenium implements DriverStart {

    static ChromeDriver driver;

    @BeforeAll
    static void before_all(){
        //setHeadless 参数为false 是打开浏览器执行 true 是无头浏览器执行
        driver = new ChromeDriver(new ChromeOptions().setHeadless(false));
    }

    @Test
    void test_127381() {
        driver.get("https://www.baidu.com/");


        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 700)");
        driver.close();
    }

    @AfterAll
    static void after_all(){
        driver.close();
    }


    @FindBy(id = "")
    public WebElement element1;

    @FindBy(xpath = "")
    public WebElement element2;

    @FindBy(linkText = "")
    public WebElement element3;

    @FindBy(className = "")
    public WebElement element4;

    @FindBy(css = "")
    public WebElement element5;
}
