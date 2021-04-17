package com.evie.autotest.playwright;

import com.microsoft.playwright.Page;

public class WechatPage {

    private final Page page;

    public WechatPage(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate("https://work.weixin.qq.com/wework_admin/loginpage_wx");
    }

    public void clickAddressBook() {
        page.click("text=通讯录");
    }

}
