package com.evie.autotest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class HttpUtils {
    private static final Logger LOGGER = LogManager.getLogger(HttpUtils.class.getName());

    HttpUtils() {
    }

    static final ObjectMapper objectMapper = new ObjectMapper();


    static final OkHttpClient client = new OkHttpClient();

    static final MediaType jsonParse = MediaType.parse("application/json");

    public static ResponseBody post(HttpUrl baseUrl, Headers headers, Object bodyParameter) {

        String jsonString = printJsonString(bodyParameter);

        assert jsonString != null;
        RequestBody body = RequestBody.create(jsonParse, jsonString);
        //构造请求参数，输出日志
        Request request = new Request.Builder()
                .headers(headers).url(baseUrl).post(body)
                .build();

        LOGGER.info("HTTP:" + request);
        printJsonString(bodyParameter);
        //发送请求，输出日志
        try {
            Response response = client
                    .newCall(request)
                    .execute();
            LOGGER.info("end send HTTP:" + response);
            assert response.body() != null;
            LOGGER.info(response.body().string());
            return response.body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static ResponseBody post(HttpUrl baseUrl, Object bodyParameter) {

        String jsonString = printJsonString(bodyParameter);

        assert jsonString != null;
        RequestBody body = RequestBody.create(jsonParse, jsonString);
        //构造请求参数，输出日志
        Request request = new Request.Builder()
                .url(baseUrl).post(body)
                .build();

        LOGGER.info("HTTP:" + request);
        //发送请求，输出日志
        try {
            Response response = client
                    .newCall(request)
                    .execute();
            LOGGER.info("end send HTTP:" + response);
            assert response.body() != null;
            LOGGER.info(response.body().string());

            return response.body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static HttpUrl.Builder getUrl(String baseUrl) {
        return HttpUrl.get(baseUrl).newBuilder();
    }

    /**
     * 输出JSON信息
     *
     * @param obj
     * @throws Exception
     */
    public static String printJsonString(Object obj) {
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
