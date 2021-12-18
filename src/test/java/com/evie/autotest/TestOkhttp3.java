package com.evie.autotest;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.evie.autotest.provider.JsonFileSource;
import com.evie.autotest.atom.api.WorkWeiXin;
import com.evie.autotest.util.HttpUtils;
import com.evie.autotest.util.JsonUtils;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestOkhttp3 {

    final static String url = "http://www.baidu.com";

    final static String url_test = "https://miniapps.ryxsg.qq.com:8080/xsg_prod/minigame/wxUpdateItemData";
    final static String url_test_app_1 = "https://miniapps.ryxsg.qq.com:8080/xsg_prod/minigame/wxUpdatePointData";
    final static String url_test_app_2 = "https://miniapps.ryxsg.qq.com:8080/xsg_prod/minigame/wxPointsToItems";


    // 0 一天可以刷一次
    // 1 一天可以刷五十多个 add 2 / 5
    @JsonFileSource(files = "/test/addt0.json")
    void test_12123(Map data) throws InterruptedException {

        HttpUrl url = HttpUtils
                .getBaseUrl(url_test)
                .build();
        HttpUrl url_1 = HttpUtils
                .getBaseUrl(url_test_app_1)
                .build();
        HttpUrl url_2 = HttpUtils
                .getBaseUrl(url_test_app_2)
                .build();

        Headers headers = new Headers
                .Builder()
                .add("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; vivo X7 Build/LMY47V; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/86.0.4240.99 XWEB/3135 MMWEBSDK/20210601 Mobile Safari/537.36 MMWEBID/2438 MicroMessenger/8.0.11.1980(0x28000B59) Process/appbrand1 WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64 MiniProgramEnv/android")
                .add("Referer", "https://servicewechat.com/wx3c8c1309511bca2f/14/page-frame.html")
                .add("Accept-Encoding", "gzip,compress,br,deflate")
                .add("content-type", "application/json")
                .add("charset", "utf-8")
                .add("Connection", "keep-alive")
                .add("Host", "miniapps.ryxsg.qq.com:8080")
                .build();
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("token", data.get("token"));
        jsonObject.set("add", 10);
        jsonObject.set("way", -100);
        int i = 1989;
        int item = 0;
        while (item < i){
            String post = HttpUtils.post(url, headers, jsonObject);
//            HttpUtils.post(url_1, headers, jsonObject);
//            String post = HttpUtils.post(url_2, headers, data);
            JSON parse = JSONUtil.parse(post);
            if (parse.getByPath("data") == null){
                jsonObject.set("way", Integer.valueOf(jsonObject.get("way").toString()) + 1);
                continue;
            }
            String s = parse.getByPath("data.item").toString();
            System.out.println("令牌数为：" + s);
            item = item + Integer.parseInt(s);
            Thread.sleep(500);
        }



    }
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
    public  void postFromParameters() {
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





    @JsonFileSource(files = "/test/demo.json")
    void test_1231(Object data) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
        HttpUrl httpUrl = HttpUtils
                .getBaseUrl(url)
                .addQueryParameter("access_token", WorkWeiXin.getInstance().getAccessToken())
                .build();

        HttpUtils.post(httpUrl, data);

    }


    @JsonFileSource(files = "/test/demo.json")
    void test_234798237(Object json){
        HttpUrl.Builder baseUrl = HttpUtils.getBaseUrl(url);
        JSONObject object = JSON.parseObject(JSON.toJSONString(json));

        object.forEach((s, o) -> baseUrl.addQueryParameter(s, String.valueOf(o)));


        Headers.Builder builder = new Headers.Builder();
        builder.add("accept: */*");
        HttpUtils.get(baseUrl.build(),builder.build());



    }
}
