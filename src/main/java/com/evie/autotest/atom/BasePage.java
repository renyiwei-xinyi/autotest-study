package com.evie.autotest.atom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
     * 检查时间间隔
     */
    private final int timeOut = 300;

    public BasePage(WebDriver driver) {
        // TODO Auto-generated constructor stub
        this.driver = driver;
    }

    /**
     * 重写sendKeys方法
     */
    public void sendKeys(WebElement element, CharSequence data) {
        // 加入显式等待
        new WebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOf(element));
        // 输入数据
        element.sendKeys(data);
    }

    /**
     * 重写click方法
     */
    public void click(WebElement element) {
        // 加入显式等待
        new WebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOf(element));
        element.click();

    }

}