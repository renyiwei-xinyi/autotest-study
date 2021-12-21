package com.evie.autotest.platform.testcase.example.web0414;

import com.evie.autotest.platform.testcase.atom.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.List;

public class WeixinPage extends BasePage {
    public WeixinPage(WebDriver driver) {
        super(driver);
    }

    public void getUrl() {
        driver.get("https://work.weixin.qq.com/wework_admin/loginpage_wx");
    }


    //================================ 可被点击按钮元素 ==================
    @FindBy(id = "menu_contacts")
    public WebElement menu_contacts;

    @FindBy(linkText = "添加")
    public WebElement add;

    @FindBy(linkText = "添加部门")
    public WebElement add_party;

    @FindBy(linkText = "添加成员")
    public WebElement add_member;

    @FindBy(id = "logout")
    public WebElement logout;

    @FindBy(name = "sendInvite")
    public WebElement sendInvite;

    @FindBy(linkText = "保存")
    public WebElement save;

    @FindBy(name = "extern_position")
    public WebElement extern_position;

    @FindBy(className = "ww_radio")
    public List<WebElement> button;

    @FindBy(linkText = "选择所属部门")
    public WebElement parent_party;

    @FindBy(xpath = "//*[@id=\"1688850174192889_anchor\"]")
    public List<WebElement> evie;

    @FindBys({
            @FindBy(tagName = "form"),
            @FindBy(linkText = "Evie")
    })
    public WebElement form_evie;

    @FindBy(linkText = "确定")
    public WebElement fix;


    //================================ 输入框元素 ==================
    @FindBy(id = "username")
    public WebElement username;
    @FindBy(id = "memberAdd_english_name")
    public WebElement memberAdd_english_name;
    @FindBy(id = "memberAdd_acctid")
    public WebElement memberAdd_acctid;
    @FindBy(id = "memberAdd_phone")
    public WebElement memberAdd_phone;
    @FindBy(id = "memberAdd_telephone")
    public WebElement memberAdd_telephone;
    @FindBy(id = "memberAdd_mail")
    public WebElement memberAdd_mail;
    @FindBy(id = "memberEdit_address")
    public WebElement memberEdit_address;
    @FindBy(id = "memberAdd_title")
    public WebElement memberAdd_title;
    @FindBy(name = "name")
    public WebElement name;

}
