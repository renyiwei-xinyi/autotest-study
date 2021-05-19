package com.evie.example.web0414.playwright;

import com.evie.autotest.provider.*;
import com.evie.autotest.extension.TimeExecutionLogger;
import com.evie.autotest.util.JsonUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ColorScheme;
import com.microsoft.playwright.options.Cookie;
import com.microsoft.playwright.options.SameSiteAttribute;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 需要用 python - pip 准备环境 安装playwrigh
 */
@ExtendWith({
        RandomParameters.class,
        ContextArgumentsProvider.class,
        JsonFileArgumentsProvider.class,
        YamlFileArgumentsProvider.class,})
public class PlayWrightLoginTest implements TimeExecutionLogger {
    // 浏览器
    private static Browser browser;
    // 浏览器配置
    private static Browser.NewContextOptions options;



    @BeforeAll
    static void beforeAll() {

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions() //启动选项
                .setHeadless(false) // 有头模式 false & 无头模式 true
                .setSlowMo(300); // 慢动作
        // 实例化谷歌浏览器对象
        browser = Playwright.create().chromium().launch(launchOptions);

        //浏览器上下文，可以用来设置打开的模式
        options = new Browser.NewContextOptions()
                // 设置用户代理模式
                .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                .setViewportSize(375, 812) // 设置窗口长宽
                .setDeviceScaleFactor(3) // 设置设备比例因子
                .setIsMobile(true) // 设置是否为移动设备显示模式
                .setHasTouch(true) // 设置有无触摸
                .setPermissions(Arrays.asList("geolocation")) // 设置权限
                .setGeolocation(52.52, 13.39) // 设置 具体地理位置
                .setColorScheme(ColorScheme.DARK) // 设置配色方案
                .setLocale("de-DE"); // 设置语言环境

    }
    @AfterAll
    static void after_all() {
        browser.close();
    }

    @Disabled
    @Test
    @DisplayName("获取cookie，手动扫码，10秒等待")
    void test_1_login() throws InterruptedException {
        // 打开网页
        BrowserContext context = browser.newContext();

        WechatPage wechatPage = new WechatPage(context.newPage());

        wechatPage.navigate();

        Thread.sleep(10000);

        List<Cookie> cookies = context.cookies();

        String path = "src/test/resources/example/cookies2.json";

        JsonUtils.writeFile(path, cookies);


    }

    @JsonFileSource(files = "/example/cookies2.json", type = Cookie.class, isArrayType = true)
    void test_2_login(List<Cookie> cookie){

        BrowserContext context = browser.newContext();

        context.addCookies(cookie);

        WechatPage wechatPage = new WechatPage(context.newPage());

        wechatPage.navigate();

        wechatPage.clickRessBook();

    }


    @Disabled
    @JsonFileSource(files = "/example/cookies2.json", type = Cookie.class, isArrayType = true)
    void test_12839(List<Cookie> cookies){
        JsonUtils.printJson(cookies);

        Browser browser = Playwright.create().chromium().launch();

        BrowserContext context = browser.newContext();
        // test add cookies
        context.addCookies(cookies);
    }


}
