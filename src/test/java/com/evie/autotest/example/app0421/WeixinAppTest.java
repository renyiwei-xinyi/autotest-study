package com.evie.autotest.example.app0421;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class WeixinAppTest {
    public static AppiumDriver<MobileElement> driver;


    @BeforeAll
    static void before_all() {
        DesiredCapabilities cap = new DesiredCapabilities();

        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");//设备名称
        cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.tencent.wework");//被测app的包名
        cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".launch.LaunchSplashActivity");//被测app的入口Activity名称
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);//安卓自动化还是IOS自动化
        cap.setCapability(MobileCapabilityType.NO_RESET, true);//不清理
        cap.setCapability(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET, true);//重置不停止应用
        cap.setCapability(AndroidMobileCapabilityType.NO_SIGN, true); //不重新签名apk
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");//appium做自动化
        //cap.setCapability(MobileCapabilityType.APP, "");//安装apk

        cap.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true); //支持中文输入
        cap.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true); //支持中文输入，必须两条都配置

//        cap.setCapability("browserName", "chrome");//设置HTML5的自动化，打开谷歌浏览器

        cap.setCapability("udid", "8b99c044"); //设备的udid (adb devices 查看到的)

        try {
            driver = new AppiumDriver<>(new URL("http://127.0.0.1:4723/wd/hub"),cap);//把以上配置传到appium服务端并连接手机
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//隐式等待

    }

    @Test
    void test_demo(){



        MobileElement el1 = driver.findElementById("com.tencent.wework:id/f4f");
        el1.click();
        MobileElement el2 = driver.findElementById("com.tencent.wework:id/f4m");
        el2.sendKeys("15068753001");
        MobileElement el3 = driver.findElementById("com.tencent.wework:id/dd");
        el3.click();
        MobileElement el4 = driver.findElementById("com.tencent.wework:id/d9n");
        InputStream in = System.in;
        el4.sendKeys(in.toString());
        MobileElement el5 = driver.findElementById("com.tencent.wework:id/dd");
        el5.click();
        MobileElement el6 = driver.findElementById("com.tencent.wework:id/fti");
        el6.click();
        MobileElement el7 = driver.findElementById("com.tencent.wework:id/duw");
        el7.click();
        MobileElement el8 = driver.findElementById("com.tencent.wework:id/f4m");
        el8.sendKeys("15068753001");
        MobileElement el9 = driver.findElementById("com.tencent.wework:id/f72");
        el9.click();

    }
}
