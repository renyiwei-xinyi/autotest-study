package com.evie.autotest;

import cn.hutool.crypto.digest.BCrypt;

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






}
