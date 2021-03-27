package com.evie.autotest;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import com.evie.autotest.annotation.JsonFileSource;
import com.evie.autotest.util.HttpUtils;
import okhttp3.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.io.IOException;
import java.util.List;

public class TestOkhttp3 {

    final static String url = "http://127.0.0.1:6868/task";

    /**
     * 同步get请求
     */
    @Test
    void syncGet() {
        try {
            // 初始化 OkHttpClient
            OkHttpClient client = new OkHttpClient();
            // 初始化请求体
            Request request = new Request.Builder()
                    .get()
                    .url(url)
                    .build();
            // 得到返回Response
            Response response = client
                    .newCall(request)
                    .execute();
            if (response.isSuccessful()) { // 判断是否成功
                System.out.println(response.body().string()); // 打印数据
            } else {
                System.out.println("失败"); // 链接失败
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Post提交表单
     */
    @Test
    public static void postFromParameters() {
        String url = "http://v.juhe.cn/wepiao/query"; // 请求链接
        String KEY = "9488373060c8483a3ef6333353fdc7fe"; // 请求参数

        OkHttpClient okHttpClient = new OkHttpClient(); // OkHttpClient对象

        RequestBody formBody = new FormBody.Builder()
                .add("key", KEY)
                .build(); // 表单键值对

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build(); // 请求

        okHttpClient.newCall(request).enqueue(new Callback() {// 回调

            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                System.out.println(responseData);//成功后的回调
            }

            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());//失败后的回调
            }
        });
    }


    @Test
    public void test_127831728() {
        //请求列表页
        String listContent = HttpUtil.get("https://www.oschina.net/action/ajax/get_more_news_list?newsType=&p=2");
        System.out.println(listContent); // 打印数据

        //使用正则获取所有标题
        List<String> titles = ReUtil.findAll("<span class=\"text-ellipsis\">(.*?)</span>", listContent, 1);
        for (String title : titles) {
            //打印标题
            Console.log(title);
        }
    }

    @Test
    void test_12123(){

    }

    @ParameterizedTest
    @JsonFileSource(files = "/test/demo.json")
    void test_1231(Object data) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
        HttpUrl httpUrl = HttpUtils.getUrl(url).addQueryParameter("access_token", "5-xVk5M-v57BtQE93Wr_17m6Kt9QHOtWr30qZQP8GdyoBeM-e9oS9xdBeKZhtnsG1Wsa5ua-Yxm5w4ri6rWsxtfkHk0qPLkyY8WFe5s15YsMugBOZWG1dx11_H9-477kcbsxwjQCnuTiY4Y0nNxJdQxcc965RQkFRi_VGyssRFF0A7bIzhCWvAozQpCrebPXzV-AlH87QiyTDgo5s0gilw")
                .build();
        HttpUtils.post(httpUrl, data);

    }

    public static void main(String[] args) {
        postFromParameters();
    }

}
