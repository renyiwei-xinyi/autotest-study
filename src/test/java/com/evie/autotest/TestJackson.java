package com.evie.autotest;


import com.evie.autotest.util.JsonUtils;
import com.microsoft.playwright.options.Cookie;
import lombok.Data;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class TestJackson {

    @Data
    static class User{
        String userId;
        String userName;
        String age;
    }

    @Test
    void test_173981() {
        String jsonStr = "{\"name\":\"seven\",\"password\":null}";

        Map<String,Object> jsonObject = JsonUtils.readValue(jsonStr, Map.class);

        System.out.println(jsonObject.get("password") == null);

    }


    @Test
    void test_json_file(){
        String s = JsonUtils.readFile("src/test/resources/example/cookies2.json");
        System.out.println(s);
        Object o = JsonUtils.readValue(s, List.class, Cookie.class);
        // array list 转换 失败
    }

}

