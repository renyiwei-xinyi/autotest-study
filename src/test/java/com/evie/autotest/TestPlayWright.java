package com.evie.autotest;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ColorScheme;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 需要用 python - pip 准备环境 安装playwrigh
 */
public class TestPlayWright {

    private static Browser browser;

    @BeforeAll
    static void beforeAll() {

        BrowserType.LaunchOptions launchOptions = new BrowserType
                .LaunchOptions() //启动选项
                .setHeadless(false) // 有头模式 false & 无头模式 true
                .setSlowMo(500); // 慢动作

        browser = Playwright
                .create()
                .chromium()
                .launch(launchOptions);
    }

    @Test
    void test() throws InterruptedException {
        // 打开 百度网页
        Page page = browser.newPage();
        page.navigate("http://www.baidu.com");

        //浏览器上下文，可以用来设置打开的模式

        Browser.NewContextOptions geolocation = new Browser.NewContextOptions()
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

        BrowserContext browserContext = browser.newContext(geolocation);
        //以这个上下文内容新建一个页面
        browserContext.newPage().navigate("http://www.baidu.com");
        Thread.sleep(5000);


    }

    @Test
    void test_network() {
        Page page = browser.newPage();
        page.onRequest(
                request ->
                {
                    System.out.println(">> " + request.method() + " " + request.url() + request.headers());
                }
        );
        page.onResponse(
                response ->
                {
                    System.out.println("<<" + response.status() + " " + response.url() + response.headers());
                }
        );
        page.navigate("https://www.baidu.com");
    }


    @AfterAll
    static void after_all(){
        browser.close();
    }
}
