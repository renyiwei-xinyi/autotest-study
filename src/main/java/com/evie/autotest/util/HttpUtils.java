package com.evie.autotest.util;

import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class HttpUtils {
    private static final Logger LOGGER = LogManager.getLogger(HttpUtils.class.getName());

    public static final OkHttpClient client = new OkHttpClient();

    public static final MediaType jsonParse = MediaType.parse("application/json");

    public static String post(HttpUrl baseUrl, Headers headers, Object bodyParameter) {

        String jsonString = JsonUtils.parseObjPretty(bodyParameter);


        assert jsonString != null;
        RequestBody body = RequestBody.create(jsonParse, jsonString);
        //构造请求参数，输出日志
        Request request = new Request.Builder()
                .headers(headers).url(baseUrl).post(body)
                .build();

        LOGGER.info("start send HTTP:" + request);
        LOGGER.info("jsonParameter:\n" + jsonString);

        //发送请求，输出日志
        try {
            Response response = client
                    .newCall(request)
                    .execute();
            if (response.isSuccessful()){
                assert response.body() != null;
                String responseBody = response.body().string();
                LOGGER.info("responseBody:" + responseBody);
                LOGGER.info("end send HTTP success:" + response);
                return responseBody;
            }else {
                LOGGER.info("end send HTTP fail:" + response);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String post(HttpUrl baseUrl, Object bodyParameter) {

        String jsonString = JsonUtils.parseObjPretty(bodyParameter);

        assert jsonString != null;
        RequestBody body = RequestBody.create(jsonParse, jsonString);
        //构造请求参数，输出日志
        Request request = new Request.Builder()
                .url(baseUrl).post(body)
                .build();

        LOGGER.info("start send HTTP:" + request);
        LOGGER.info("jsonParameter:\n" + jsonString);

        //发送请求，输出日志
        try {
            Response response = client
                    .newCall(request)
                    .execute();
            if (response.isSuccessful()){
                assert response.body() != null;
                String responseBody = response.body().string();
                LOGGER.info("responseBody:" + responseBody);
                LOGGER.info("end send HTTP success:" + response);
                return responseBody;
            }else {
                LOGGER.info("end send HTTP fail:" + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String get(HttpUrl baseUrl, Headers headers){

        //构造请求参数，输出日志
        Request request = new Request.Builder()
                .headers(headers).url(baseUrl).get()
                .build();

        LOGGER.info("start send HTTP:" + request);
        //发送请求，输出日志
        try {
            Response response = client
                    .newCall(request)
                    .execute();
            if (response.isSuccessful()){
                assert response.body() != null;
                String responseBody = response.body().string();
                LOGGER.info("responseBody:" + responseBody);
                LOGGER.info("end send HTTP success:" + response);
                return responseBody;
            }else {
                LOGGER.info("end send HTTP fail:" + response);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String get(HttpUrl baseUrl){

        //构造请求参数，输出日志
        Request request = new Request.Builder()
                .url(baseUrl).get()
                .build();

        LOGGER.info("start send HTTP:" + request);
        //发送请求，输出日志
        try {
            Response response = client
                    .newCall(request)
                    .execute();
            if (response.isSuccessful()){
                assert response.body() != null;
                String responseBody = response.body().string();
                LOGGER.info("responseBody:" + responseBody);
                LOGGER.info("end send HTTP success:" + response);
                return responseBody;
            }else {
                LOGGER.info("end send HTTP fail:" + response);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String get(String baseUrl){

        //构造请求参数，输出日志
        Request request = new Request.Builder()
                .url(baseUrl).get()
                .build();

        LOGGER.info("start send HTTP:" + request);
        //发送请求，输出日志
        try {
            Response response = client
                    .newCall(request)
                    .execute();
            if (response.isSuccessful()){
                assert response.body() != null;
                String responseBody = response.body().string();
                LOGGER.info("responseBody:" + responseBody);
                LOGGER.info("end send HTTP success:" + response);
                return responseBody;
            }else {
                LOGGER.info("end send HTTP fail:" + response);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建并返回一个baseUrl
     * 可以直接再方法末尾链式 .add****
     * @param baseUrl
     * @return
     */
    public static HttpUrl.Builder getBaseUrl(String baseUrl) {
        return HttpUrl.get(baseUrl).newBuilder();
    }


}
