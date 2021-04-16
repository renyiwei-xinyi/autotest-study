package com.evie.example.web0414;

import com.evie.autotest.annotation.JsonFileSource;
import com.evie.autotest.annotation.YamlFileSource;
import com.evie.autotest.interfaces.DriverStart;
import com.evie.autotest.interfaces.TimeExecutionLogger;
import com.evie.autotest.util.JsonLogUtils;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class WeixinloginTest implements DriverStart, TimeExecutionLogger  {

    private static final Logger LOGGER = LogManager.getLogger(WeixinloginTest.class);

    private static final String URL = "https://work.weixin.qq.com/wework_admin/loginpage_wx";

    private static final String COOKIE = "/example/cookies.json"; //相对路径

    private static ChromeDriver driver;

    private static WeixinPage page;

    @BeforeAll
    static void before_all(){
        // 谷歌浏览器实例
        driver = new ChromeDriver(new ChromeOptions().setHeadless(false));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        // 初始化页面元素
        page = PageFactory.initElements(driver, WeixinPage.class);

    }
    //@AfterAll
    static void after_all(){
        // 测试结束 关闭浏览器
        driver.close();
    }

    @DisplayName("手动登录储存cookie操作 10s等待时间 完成扫码")
    @Disabled
    @Test
    void test_1_login_save_cookie() throws InterruptedException, IOException {
        driver.get(URL);
        // 设置等待时间 扫码登录
        Thread.sleep(10000);
        Set<Cookie> cookies = driver.manage().getCookies();
        // 将cookie 写入 json文件用于复用
        OutputStream outputStream= new FileOutputStream("src/test/resources/example/cookies.json");
        JsonLogUtils.objectMapper.writeValue(outputStream, cookies);
    }


    /**
     * 课后作业1：自动登录企业微信
     * @param cookies 已经保存的，未过期的cookie
     */
    @JsonFileSource(files = COOKIE)
    @ParameterizedTest
    void test_2_login(List<Map<String, String>> cookies){
        // 打开网页
        driver.get(URL);

        // 设置cookie
        cookies.forEach(cookie->{

            driver.manage().addCookie(
                    new Cookie(cookie.get("name"), cookie.get("value")));
        });
        //导航栏 刷新操作
        driver.navigate().refresh();
        // 登录成功 有退出 按钮 校验是否可见
        assertTrue(page.logout.isDisplayed(),"登录失败！请重新手动获取存储cookie");
    }

    @Data
    static class memberInfo{

        private String name;
        private String alias;
        private String id;
        private String phone;
        private String landline;
        private String email;
        private String address;
        private String jobTitle;
    }

    /**
     * 课后作业2：自动添加公司成员
     * @param date
     */
    @YamlFileSource(files = "/example/web0414/memberInfo.yaml")
    @ParameterizedTest
    void test_3_add_member(Object date){
        memberInfo memberInfo = JsonLogUtils.jsonTo(date, memberInfo.class);
        assert memberInfo != null;
        // 点击通讯录
        page.menu_contacts.click();
        // 点击添加成员
        page.add_member.click();
        // 输入成员信息
        page.username.sendKeys(memberInfo.getName());
        page.memberAdd_english_name.sendKeys(memberInfo.getAlias());
        page.memberAdd_acctid.sendKeys(memberInfo.getId());
        page.memberAdd_phone.sendKeys(memberInfo.getPhone());
        page.memberAdd_telephone.sendKeys(memberInfo.getLandline());
        page.memberAdd_mail.sendKeys(memberInfo.getEmail());
        page.memberEdit_address.sendKeys(memberInfo.getAddress());
        page.memberAdd_title.sendKeys(memberInfo.getJobTitle());

        page.button.forEach(WebElement::click);
        page.extern_position.sendKeys("自定义职务");

        page.sendInvite.click();

        page.save.click();
    }


}
