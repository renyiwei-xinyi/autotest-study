package com.evie.autotest.platform.testcase.example;


import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestJMH {

    public static Options options;

    @BeforeAll
    public static void JMH() {
        options = new OptionsBuilder()
                .include("JMHTest")//测试类：需要在测试的方法加注解 @Benchmark
                .timeUnit(TimeUnit.MILLISECONDS)//设置输出统计的单位
                .timeout(TimeValue.minutes(3))//设置超时时间
                .mode(Mode.All)//统计类型：平均每次调用时间
                .measurementIterations(2)//迭代次数，即 测试几次
                .warmupIterations(1)//预热次数
                .threads(5)//并发线程数量
                .forks(1)//开启的进程数量
                .output("logs/JMH1.log")//日志产生的 地址
                .build();
    }

    @Disabled
    @Test
    //性能测试启动器
    public void JMHTestRun() throws RunnerException {

        new Runner(options).run();
    }

    public static class JMHTest {
        final static String url = "https://www.oschina.net/action/ajax/get_more_news_list?newsType=&p=2";

        final static String s = "{\"test\": 123}" ;

        /**
         * 同步get请求
         */
        //@Benchmark
        public void syncGet() {
            try {
                // 初始化 OkHttpClient
                OkHttpClient client = new OkHttpClient();
                // 初始化请求体
                Request request = new Request.Builder()
                        .get()
                        .url(url)
                        .build();
                // 得到返回Response
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) { // 判断是否成功
                    /**获取返回的数据，可通过response.body().string()获取，默认返回的是utf-8格式；
                     * string()适用于获取小数据信息，如果返回的数据超过1M，建议使用stream()获取返回的数据，
                     * 因为string() 方法会将整个文档加载到内存中。*/
                    System.out.println(response.body().string());
                } else {
                    System.out.println("失败"); // 链接失败
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Benchmark
        public String test_hutool_json(){
            return JSONUtil.parseObj(s).getStr("test");
        }


        @Benchmark
        public String test_gson(){
            Gson gson = new Gson();
            return gson.fromJson(s, JsonObject.class).get("test").getAsString();
        }


    }
}
