package com.evie.autotest.platform.testcase.example.web0414;

import com.evie.autotest.platform.testcase.atom.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CeshirenPage extends BasePage {
    public CeshirenPage(WebDriver driver) {
        super(driver);
    }

    public void getUrl(){
        driver.get("https://ceshiren.com/");
    }

    @FindBy(id = "search-button")
    public WebElement search_button;

    @FindBy(id = "search-term")
    public WebElement search_term;

    @FindBy(xpath = "//span[text()='微信小程序自动化']")
    public WebElement search_text;


}
