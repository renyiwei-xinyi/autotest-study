package com.evie.autotest;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ColorScheme;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.Arrays;

public class TestPlayWright {

    private static Browser browser;

    private static Browser.NewContextOptions options;

    @BeforeAll
    static void beforeAll() {

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions() //启动选项
                .setHeadless(true) // 有头模式 false & 无头模式 true
                .setSlowMo(10000); // 慢动作
        // 实例化谷歌浏览器对象
        browser = Playwright.create().chromium().launch(launchOptions);

        //浏览器上下文，可以用来设置打开的模式
        options = new Browser.NewContextOptions()
                // 设置用户代理模式
//                .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
//                .setViewportSize(180, 812) // 设置窗口长宽
//                .setDeviceScaleFactor(3) // 设置设备比例因子
                .setIsMobile(false) // 设置是否为移动设备显示模式
                .setHasTouch(false) // 设置有无触摸
//                .setPermissions(Arrays.asList("geolocation")) // 设置权限
//                .setGeolocation(52.52, 13.39) // 设置 具体地理位置
                .setColorScheme(ColorScheme.DARK) // 设置配色方案
                .setLocale("cn-CN"); // 设置语言环境

    }

    @Test
    void test_network() {
        Page page = browser.newContext(options).newPage();

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
        page.navigate("https://www.fmz.com/robot/171548");

        ElementHandle id = page.querySelector("id=summary-tbl");
        String x = id.innerText();
        String[] split = x.split("币安：");
        System.out.println(x);


    }
}
