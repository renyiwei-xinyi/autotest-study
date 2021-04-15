package com.evie.example.web0414;

import com.evie.autotest.atom.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WeixinPage extends BasePage {
    public WeixinPage(WebDriver driver) {
        super(driver);
    }

    @FindBy
    public WebElement search_button;

    @FindBy
    public WebElement search_term;

    @FindBy
    public WebElement search_text;
}
