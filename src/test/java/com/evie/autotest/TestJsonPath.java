package com.evie.autotest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.evie.autotest.provider.JsonFileSource;
import com.evie.autotest.util.JsonUtils;
import org.junit.jupiter.api.Test;

public class TestJsonPath {


    @JsonFileSource(files = "/test/demo.json")
    void test(JSONObject a){

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("$..test1", "你好");
        jsonObject.put("$..test2", "再见");

        JSONArray read = (JSONArray)JSONPath.read(a.toString(), "$text.list");

        for (int i = 0; i < 2; i++) {
            JSONObject array = JSON.parseObject(read.getString(0));
            jsonObject.forEach((s, o) -> JSONPath.set(array, s, o));

            jsonArray.add(array);

        }

        JSONPath.set(a, "$text.list", jsonArray);

        System.out.println(JSON.toJSONString(a, true));


    }
}
