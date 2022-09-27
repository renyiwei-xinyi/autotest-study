package com.evie.autotest;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ColorScheme;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // 先获取当前时间字段
//        Pattern compile = Pattern.compile(regMainData);
//        Matcher matcher = compile.matcher(x);
//        String group = matcher.group();
//        System.out.println(group + "\n");


        String[] split = x.split("币安：");
        ArrayList<Demo> demos = new ArrayList<>();
        String dataTime = null;
        for (String s : split) {
            Demo demo = new Demo();
            List<String> tabKeys = null;
            ArrayList<LinkedHashMap<String, String>> objects = new ArrayList<>();
            if (s.contains("当前时间:")){
                dataTime = s.split("==================当前时间:")[1].split("==================")[0];
                System.out.println("数据时间：" + dataTime);
                demo.setDataTime(DateUtil.parse(dataTime));
                continue;
            }else {
                demo.setDataTime(DateUtil.parse(dataTime));
                System.out.println("数据表：\n" + s);
                // 分割行
                String[] strings = s.split("\n");
                for (int i = 0; i < strings.length; i++) {
                    String string = strings[i];
                    System.out.println(MessageFormat.format("第{0}行:" , i)+ string);
                    // 分析下来 一共 13行 第一行是 币种和报价 第二行是表头 从第三行开始是  不同k线周期的数据
                    switch (i){
                        case 0:
                            //第一行是 币种比和报价 currency prices
                            String[] split1 = string.split("，当前价： ");
                            String currencies = split1[0];
                            String prices = split1[1];
                            // 截取纯报价
                            String pricesValue = prices.split(currencies.split("_")[1])[0];
                            demo.setCurrency(currencies);
                            demo.setPrices(Double.valueOf(pricesValue));
                            break;
                        case 1:
                            //第二行是表头 间隔是一个 tab 用\t 分割
                            tabKeys = Arrays.asList(string.split("\t"));
                            break;
                        default:
                            //数据内容
                            LinkedHashMap<String, String> tabData = new LinkedHashMap<>();
                            String[] tabValue = string.split("\t");
                            for (int n = 0; n < tabValue.length; n++) {
                                tabData.put(tabKeys.get(n), tabValue[n]);
                            }
                            objects.add(tabData);
                            demo.setTabData(objects);
                    }
                }
            }
            demos.add(demo);
        }

        System.out.println("demos : \t" + demos);

    }


    @Data
    static class Demo{
        public Date DataTime;
        public String currency;
        public Double prices;
        public ArrayList<LinkedHashMap<String, String>> tabData;
    }
}
