package com.evie.example.web0414;

import com.evie.autotest.annotation.JsonFileSource;
import com.evie.autotest.interfaces.DriverStart;
import com.evie.autotest.interfaces.TimeExecutionLogger;
import com.evie.autotest.util.JsonLogUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WeixinloginTest implements DriverStart, TimeExecutionLogger  {

    private static final Logger LOGGER = LogManager.getLogger(WeixinloginTest.class);

    private static final String URL = "https://work.weixin.qq.com/wework_admin/loginpage_wx";

    private static final String COOKIE = "/example/cookies.json"; //相对路径

    private static ChromeDriver driver;

    @BeforeAll
    static void before_all(){
        // 谷歌浏览器实例
        driver = new ChromeDriver(new ChromeOptions().setHeadless(false));

    }
    @AfterAll
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


    @JsonFileSource(files = COOKIE)
    @ParameterizedTest
    void test_2_login(List<HashMap<String, String>> cookies){
        // 打开网页
        driver.get(URL);

        // 设置cookie
        cookies.forEach(cookie->{

            driver.manage().addCookie(
                    new Cookie(cookie.get("name"), cookie.get("value")));
        });
        //导航栏 刷新操作
        driver.navigate().refresh();
        // 打印 页面title
        LOGGER.info(driver.getTitle());
    }


}
