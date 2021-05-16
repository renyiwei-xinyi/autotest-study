package com.evie.autotest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/*
项目中 hutool - json 不能和 jackson / gson 混用
 */
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

        JSONConfig jsonConfig = JSONConfig.create().setIgnoreNullValue(false);
        JSONObject jsonObject = new JSONObject(jsonStr, jsonConfig);
        System.out.println(jsonObject.get("password") == null);

        JSONObject json = new JSONObject(jsonStr);
        Assertions.assertNotNull(json.get("password"));//hutool json 对象 需要用 JSONObject.isNull 来判断
        System.out.println(json.get("password").getClass());

        Assertions.assertTrue(json.isNull("password")); // 这里的null 如果 toString 会变成 "null" 字符串

        boolean bean = BeanUtil.isBean(Map.class); // map 不是 javaBean
        Assertions.assertFalse(bean);


        Map<String,Object> map = json.toBean(HashMap.class);// 这里转换会成功的，但是 null 已经被转为 JSONNull
        Assertions.assertEquals(map.getClass(), HashMap.class);

        Assertions.assertNotNull(map.get("password"));// 所以这里也依然需要用 JSONUtil.isNull 来判断
        System.out.println(map.get("password").getClass());


        Map<String, Object> jsonToMap = MapUtil.builder(json).map();// 这里转换会失败，依然是 JSONObject
        Assertions.assertEquals(jsonToMap.getClass(), JSONObject.class);
        Assertions.assertNotNull(jsonToMap.get("password"));// 所以这里也依然需要用 JSONObject.isNull 来判断
        Assertions.assertTrue(JSONUtil.isNull(jsonToMap.get("password")));// 所以这里也依然需要用 JSONUtil.isNull 来判断

        System.out.println(jsonToMap.get("password").getClass());

    }

    @Data
    static class demo{
        public String name;
        public String password;
    }



}
