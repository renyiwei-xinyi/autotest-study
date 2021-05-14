package com.evie.autotest;


import com.evie.autotest.util.JsonUtils;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class TestJackson {

    @Data
    static class User{
        String userId;
        String userName;
        String age;
    }

    @Test
    void test_173981() throws JSONException {
        String jsonStr = "{\"name\":\"seven\",\"password\":null}";

        Map<String,Object> jsonObject = JsonUtils.readValue(jsonStr, Map.class);

        System.out.println(jsonObject.get("password") == null);

    }

}

