package com.evie.autotest.platform;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TestClaseStart {



    public static void run(Class<?> clazz){

        try {

            Method method1 = Arrays.stream(clazz.getMethods()).filter(method -> method.getName().equals("test")).collect(Collectors.toList()).get(0);
            Object o = clazz.newInstance();

            method1.invoke(o);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
