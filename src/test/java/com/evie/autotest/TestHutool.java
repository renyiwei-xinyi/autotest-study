package com.evie.autotest;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONUtil;
import com.evie.autotest.util.JsonLogUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestHutool {

    /**
     * 用新的加密方式获取密码和盐
     *
     * @param password
     * @return
     */
    public static Map<String, Object> getPassword(String password) {
        String salt = BCrypt.gensalt();
        String cryptPwd = BCrypt.hashpw(password, salt);
        HashMap<String, Object> passWord = new HashMap<>();
        passWord.put("salt",salt);
        passWord.put("pwd",cryptPwd);
        return passWord;
    }

    String s = "{\"test\": 123}" ;
    @Test
    void test_132(){

        String s1 = JsonLogUtils.jsonTo(s, ObjectNode.class).get("test").asText();
        System.out.println(s1);

        System.out.println(JSONUtil.toBean(s, HashMap.class).get("test"));


    }



}
