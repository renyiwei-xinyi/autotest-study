package com.evie.autotest.example.web0414;

import com.evie.autotest.provider.JsonFileSource;
import com.evie.autotest.provider.Random;
import com.evie.autotest.provider.RandomParameters;
import com.evie.autotest.provider.YamlFileSource;
import com.evie.autotest.extension.DriverStart;
import com.evie.autotest.extension.TimeExecutionLogger;
import com.evie.autotest.util.JsonUtils;
import com.evie.autotest.util.RandomStringUtil;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 课后作业
 */
@ExtendWith(RandomParameters.class)
public class WeixinAutoTest implements DriverStart, TimeExecutionLogger {

    private static final Logger LOGGER = LogManager.getLogger(WeixinAutoTest.class);

    private static final String COOKIE = "/example/cookies1.json"; //相对路径
    // 浏览器实例
    private static RemoteWebDriver driver;


    private static WeixinPage page;

    @BeforeAll
    static void before_all() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        WeixinAutoTest.driver.navigate().to("https://www.baidu.com");
        //设置页面隐式等待
        WeixinAutoTest.driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        // 初始化页面元素
        page = PageFactory.initElements(WeixinAutoTest.driver, WeixinPage.class);

    }

    @AfterAll
    static void after_all() {
        // 测试结束 关闭浏览器
        driver.close();
    }


    @Disabled
    @DisplayName("手动登录储存cookie操作 10s等待时间 完成扫码")
    @Test
    void test_1_login_save_cookie() throws InterruptedException {
        //page.getUrl();
        // 设置等待时间 扫码登录
        Thread.sleep(10000);
        Set<Cookie> cookies = driver.manage().getCookies();
        // 将cookie 写入 json文件用于复用
        JsonUtils.writeFile("src/test/resources" + COOKIE, cookies);
    }



    @Tag("login")
    @DisplayName("自动登录企业微信首页")
    @JsonFileSource(files = COOKIE)
    void test_2_login(List<Map<String, String>> cookies) {
        // 打开网页
        page.getUrl();

        // 设置cookie
        cookies.forEach(cookie -> {

            driver.manage().addCookie(
                    new Cookie(cookie.get("name"), cookie.get("value")));
        });
        //导航栏 刷新操作
        driver.navigate().refresh();
        // 登录成功 有退出 按钮 校验是否可见
        assertTrue(page.logout.isDisplayed(), "登录失败！请重新手动获取存储cookie");
    }

    @Data
    static class MemberInfo {

        public String name;
        public String alias;
        public String id;
        public String phone;
        public String landline;
        public String email;
        public String address;
        public String jobTitle;
    }


    @Disabled
    @Tag("add")
    @DisplayName("自动添加成员")
    @YamlFileSource(files = "/example/web0414/memberInfo.yaml", type = MemberInfo.class)
    void test_3_add_member(MemberInfo memberInfo, @Random int id) {
        assert memberInfo != null;
        String phone = RandomStringUtil.getPhone();

        memberInfo.setId(String.valueOf(id));
        memberInfo.setEmail(phone + "@164" + ".com");
        memberInfo.setName(RandomStringUtil.getRandomName());
        memberInfo.setPhone(phone);
        // 点击通讯录 然后点击添加成员
        page.click(page.menu_contacts).click(page.add_member).click(page.sendInvite);
        // 输入成员信息
        page.sendKeys(page.username, memberInfo.getName())
                .sendKeys(page.memberAdd_english_name, memberInfo.getAlias())
                .sendKeys(page.memberAdd_acctid, memberInfo.getId())
                .sendKeys(page.memberAdd_phone, memberInfo.getPhone())
                .sendKeys(page.memberAdd_telephone, memberInfo.getLandline())
                .sendKeys(page.memberAdd_mail, memberInfo.getEmail())
                .sendKeys(page.memberEdit_address, memberInfo.getAddress())
                .sendKeys(page.memberAdd_title, memberInfo.getJobTitle())
                .sendKeys(page.extern_position, "自定义职务");

        page.button.forEach(webElement -> page.click(webElement));

        page.click(page.save);
    }

    @Tag("add")
    @DisplayName("自动添加部门")
    @YamlFileSource(files = "/example/web0414/party.yaml", type = String.class)
    void test_4_department_management(String data, @Random int a) {
        page.click(page.menu_contacts)
                .click(page.add)
                .click(page.add_party)
                .sendKeys(page.name, data + a)
                .click(page.parent_party)
                .click(page.form_evie)
                .click(page.fix);
    }
}
