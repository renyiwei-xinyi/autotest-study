package com.evie.example.web0414;

import cn.hutool.json.JSONUtil;
import com.evie.autotest.annotation.JsonFileSource;
import com.evie.autotest.annotation.YamlFileSource;
import com.evie.autotest.interfaces.DriverStart;
import com.evie.autotest.interfaces.TimeExecutionLogger;
import com.evie.autotest.util.RandomStringUtil;
import com.evie.autotest.util.TextUtils;
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

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class WeixinAutoTest implements DriverStart, TimeExecutionLogger {

    private static final Logger LOGGER = LogManager.getLogger(WeixinAutoTest.class);

    private static final String COOKIE = "/example/cookies2.json"; //相对路径

    private static ChromeDriver driver;

    private static WeixinPage page;

    @BeforeAll
    static void before_all() {
        // 谷歌浏览器实例
        driver = new ChromeDriver(new ChromeOptions().setHeadless(false));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        // 初始化页面元素
        page = PageFactory.initElements(driver, WeixinPage.class);

    }

    //@AfterAll
    static void after_all() {
        // 测试结束 关闭浏览器
        driver.close();
    }

    @DisplayName("手动登录储存cookie操作 10s等待时间 完成扫码")
    @Disabled
    @Test
    void test_1_login_save_cookie() throws InterruptedException {
        page.getUrl();
        // 设置等待时间 扫码登录
        Thread.sleep(10000);
        Set<Cookie> cookies = driver.manage().getCookies();
        // 将cookie 写入 json文件用于复用
        TextUtils.jsonWriteTo("src/test/resources/example/cookies1.json", cookies);
    }


    /**
     * 课后作业1：自动登录企业微信
     *
     * @param cookies 已经保存的，未过期的cookie
     */
    @JsonFileSource(files = COOKIE)
    @ParameterizedTest
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
    static class memberInfo {

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
     *
     * @param date
     */
    @YamlFileSource(files = "/example/web0414/memberInfo.yaml")
    @ParameterizedTest
    void test_3_add_member(Object date) {
        memberInfo memberInfo = JSONUtil.toBean(JSONUtil.parseObj(date), memberInfo.class);
        assert memberInfo != null;
        String phone = RandomStringUtil.getPhone();
        memberInfo.setId(RandomStringUtil.getRandom(4,false));
        memberInfo.setEmail(phone + "@164" + ".com");
        memberInfo.setName(RandomStringUtil.getRandomName());
        memberInfo.setPhone(phone);
        // 点击通讯录
        page.click(page.menu_contacts);
        // 点击添加成员
        page.click(page.add_member);
        // 输入成员信息
        page.sendKeys(page.username, memberInfo.getName());
        page.sendKeys(page.memberAdd_english_name, memberInfo.getAlias());
        page.sendKeys(page.memberAdd_acctid, memberInfo.getId());
        page.sendKeys(page.memberAdd_phone, memberInfo.getPhone());
        page.sendKeys(page.memberAdd_telephone, memberInfo.getLandline());
        page.sendKeys(page.memberAdd_mail, memberInfo.getEmail());
        page.sendKeys(page.memberEdit_address, memberInfo.getAddress());
        page.sendKeys(page.memberAdd_title, memberInfo.getJobTitle());

        page.button.forEach(webElement -> {
            page.click(webElement);
        });
        page.sendKeys(page.extern_position, "自定义职务");

        page.click(page.sendInvite);

        page.click(page.save);
    }


}
