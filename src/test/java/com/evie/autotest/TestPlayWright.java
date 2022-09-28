package com.evie.autotest;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.evie.autotest.util.RetryHandler;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ColorScheme;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Consumer;
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
    void test_network() throws InterruptedException {
        Page page = browser.newContext(options).newPage();

        page.onRequest(
                request ->
                {
                    String url = request.url();
                    Pattern pattern = Pattern.compile("(\\.png)|(\\.jpg)|(\\.gif)|(\\.svg)");
                    page.route(pattern, Route::abort);
                    System.out.println(">> " + request.method() + " " + url + request.headers());
                }
        );
        page.onResponse(
                response ->
                {
                    String url = response.url();

                    Pattern pattern = Pattern.compile("(\\.png)|(\\.jpg)|(\\.gif)|(\\.svg)");
                    page.route(pattern, Route::abort);

                    System.out.println("<<" + response.status() + " " + url + response.headers());
                }
        );
        page.navigate("https://www.fmz.com/robot/171548");


        while (true){

            ElementHandle id = page.querySelector("id=summary-tbl");
            String x = id.innerText();

            String[] split = x.split("币安：");
            ArrayList<Demo> demos = new ArrayList<>();
            String dataTime = null;
            for (String s : split) {
                Demo demo = new Demo();
                if (s.contains("当前时间:")){
                    dataTime = s.split("==================当前时间:")[1].split("==================")[0];
//                    System.out.println("数据时间：" + dataTime);
                    demo.setDataTime(DateUtil.parse(dataTime));
                    continue;
                }else {
                    List<String> tabKeys = null;
                    JSONArray objects = new JSONArray();
                    demo.setDataTime(DateUtil.parse(dataTime));
//                    System.out.println("数据表：\n" + s);
                    // 分割行
                    String[] strings = s.split("\n");
                    for (int i = 0; i < strings.length; i++) {
                        String string = strings[i];
//                        System.out.println(MessageFormat.format("第{0}行:" , i)+ string);
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
                                //映射表头
                                for (String tabKey : tabKeys) {
                                    switch (tabKey){
                                        case "K线周期":
                                            tabKeys.set(tabKeys.indexOf(tabKey), "k_period");
                                            break;
                                        case "趋势：指数":
                                            tabKeys.set(tabKeys.indexOf(tabKey), "trend_index");
                                            break;
                                        case "提醒开仓价":
                                            tabKeys.set(tabKeys.indexOf(tabKey), "remind_opening_price");
                                            break;
                                        case "价格浮动率":
                                            tabKeys.set(tabKeys.indexOf(tabKey), "price_floating_rate");
                                            break;
                                        case "当前20倍盈利":
                                            tabKeys.set(tabKeys.indexOf(tabKey), "profit_20_times");
                                            break;
                                        case "期间最(高/低)价":
                                            tabKeys.set(tabKeys.indexOf(tabKey), "max_or_min_price");
                                            break;
                                        case "20倍最大盈利":
                                            tabKeys.set(tabKeys.indexOf(tabKey), "max_profit_20_times");
                                            break;
                                        case "提醒开仓时间":
                                            tabKeys.set(tabKeys.indexOf(tabKey), "opening_time");
                                            break;
                                        case "K线数量":
                                            tabKeys.set(tabKeys.indexOf(tabKey), "k_count");
                                            break;
                                    }
                                }
                                break;
                            default:
                                //数据内容
                                LinkedHashMap<String, String> tabData = new LinkedHashMap<>();
                                String[] tabValue = string.split("\t");
                                //处理中文字段值
                                for (int n = 0; n < tabValue.length; n++) {
                                    String value = tabValue[n];
                                    switch (value){
                                        case "1分钟":
                                            tabData.put(tabKeys.get(n), "1_min");
                                            break;
                                        case "3分钟":
                                            tabData.put(tabKeys.get(n), "3_min");
                                            break;
                                        case "5分钟":
                                            tabData.put(tabKeys.get(n), "5_min");
                                            break;
                                        case "15分钟":
                                            tabData.put(tabKeys.get(n), "15_min");
                                            break;
                                        case "30分钟":
                                            tabData.put(tabKeys.get(n), "30_min");
                                            break;
                                        case "1小时":
                                            tabData.put(tabKeys.get(n), "1_hour");
                                            break;
                                        case "2小时":
                                            tabData.put(tabKeys.get(n), "2_hour");
                                            break;
                                        case "4小时":
                                            tabData.put(tabKeys.get(n), "4_hour");
                                            break;
                                        case "6小时":
                                            tabData.put(tabKeys.get(n), "6_hour");
                                            break;
                                        case "12小时":
                                            tabData.put(tabKeys.get(n), "12_hour");
                                            break;
                                        case "1天":
                                            tabData.put(tabKeys.get(n), "1_day");
                                            break;
                                        case "1周":
                                            tabData.put(tabKeys.get(n), "1_week");
                                            break;
                                        default:
                                            if (value.contains("下跌")){
                                                tabData.put(tabKeys.get(n), value.replace("下跌：", "down:"));
                                                break;
                                            }
                                            if (value.contains("上升")){
                                                tabData.put(tabKeys.get(n), value.replace("上升：", "up:"));
                                                break;
                                            }
                                            if (value.contains(" %"))
                                            tabData.put(tabKeys.get(n), value);
                                    }

                                }
                                objects.add(tabData);
                                demo.setTabData(objects);
                        }
                    }
                }
                demos.add(demo);
            }

            for (Demo demo : demos) {
                System.out.println("demos : \t" + demo);
                System.out.println("====================================");
            }
            // todo: 这里应该如何合理进行等待
            Thread.sleep(60 * 1000L);
        }

    }


    @Data
    static class Demo{
        public Date DataTime;
        public String currency;
        public Double prices;
        public JSONArray tabData;
    }

}
