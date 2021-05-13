package com.evie.autotest;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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

        System.out.println(JSONUtil.parseObj(s).get("test"));

    }

    @Test
    void test_127987(){
        String jsonStr = "{\"name\":\"seven\",\"password\":null}";

        JSONObject json = new JSONObject(jsonStr);
        System.out.println(json.get("password")==null);
        System.out.println(json.get("password").getClass());

        Map<String,String> map = new JSONObject(jsonStr).toBean(Map.class);
        System.out.println(map.get("password")==null);
        System.out.println(map.get("password").getClass());

        System.out.println(json.isNull("password"));
    }



}
