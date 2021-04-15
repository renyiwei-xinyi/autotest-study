package com.evie.autotest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class HttpUtils {
    private static final Logger LOGGER = LogManager.getLogger(HttpUtils.class.getName());

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static final OkHttpClient client = new OkHttpClient();

    public static final MediaType jsonParse = MediaType.parse("application/json");

    public static String post(HttpUrl baseUrl, Headers headers, Object bodyParameter) {

        String jsonString = printJsonString(bodyParameter);

        assert jsonString != null;
        RequestBody body = RequestBody.create(jsonParse, jsonString);
        //构造请求参数，输出日志
        Request request = new Request.Builder()
                .headers(headers).url(baseUrl).post(body)
                .build();

        LOGGER.info("start send HTTP:" + request);
        printJsonString(bodyParameter);
        //发送请求，输出日志
        try {
            Response response = client
                    .newCall(request)
                    .execute();
            if (response.isSuccessful()){
                LOGGER.info("end send HTTP success:" + response);
                assert response.body() != null;
                String responseBody = response.body().string();
                LOGGER.info(responseBody);
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

        String jsonString = printJsonString(bodyParameter);

        assert jsonString != null;
        RequestBody body = RequestBody.create(jsonParse, jsonString);
        //构造请求参数，输出日志
        Request request = new Request.Builder()
                .url(baseUrl).post(body)
                .build();

        LOGGER.info("start send HTTP:" + request);
        //发送请求，输出日志
        try {
            Response response = client
                    .newCall(request)
                    .execute();
            if (response.isSuccessful()){
                LOGGER.info("end send HTTP success:" + response);
                assert response.body() != null;
                String responseBody = response.body().string();
                LOGGER.info(responseBody);
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
                LOGGER.info("end send HTTP success:" + response);
                assert response.body() != null;
                String responseBody = response.body().string();
                LOGGER.info(responseBody);
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
                LOGGER.info("end send HTTP success:" + response);
                assert response.body() != null;
                String responseBody = response.body().string();
                LOGGER.info(responseBody);
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

    /**
     * 解析为json格式 并格式化输出
     *
     * @param obj 被解析对象
     * @throws Exception 解析错误
     */
    private static String printJsonString(Object obj) {
        String json = null;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            LOGGER.info("jsonParameter:\n" + json);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }

        return null;

    }

}
