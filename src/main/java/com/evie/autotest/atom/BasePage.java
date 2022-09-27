package com.evie.autotest.atom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author:Renyiwei
 * @date：2019.9.22
 * @Description: 处理页面元素公共类，重写页面操作事件，为每个元素加入显式等待
 */
public class BasePage {

    public WebDriver driver;
    /**
     * 最长超时时间，默认以秒为单位
     */
    private final long timeOut = 3;

    /**
     * 检查间隔时间，默认为毫秒为单位
     */
    private final long sleep = 300;

    public BasePage(WebDriver driver) {
        // TODO Auto-generated constructor stub
        this.driver = driver;
    }

    /**
     * 重写sendKeys方法
     */
    public BasePage sendKeys(WebElement element, CharSequence data) {
        // 加入显式等待
        new WebDriverWait(driver, timeOut, sleep).until(ExpectedConditions.visibilityOf(element));
        // 输入数据
        element.sendKeys(data);

        return this;

    }

    /**
     * 重写click方法
     */
    public BasePage click(WebElement element) {
        // 加入显式等待
        new WebDriverWait(driver, timeOut, sleep).until(ExpectedConditions.visibilityOf(element));
        element.click();

        return this;

    }

    /**
     * 跳转网址
     */
    public BasePage getUrl(String url) {
        driver.get(url);

        return this;

    }

    public RemoteWebDriver getChromeDriver(){
        return new ChromeDriver();
    }


}