package com.evie.autotest;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 需要用 python - pip 准备环境 安装playwrigh
 */
public class TestPlayWright {

    private static Browser browser;

    @BeforeAll
    static void beforeAll() {
        browser = Playwright
                .create()
                .chromium()
                .launch();
    }

    @Test
    void test() {
        Page page = browser.newPage();
        page.navigate("http://www.baidu.com");

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
        browser.close();
    }
}
