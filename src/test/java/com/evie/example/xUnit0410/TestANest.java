package com.evie.example.xUnit0410;

import com.evie.autotest.atom.db.DataMap;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @ Nested 是从下往上执行
 */
public class TestANest {

    @Test
    void test_1(){
        System.out.println("我是第一个执行么");

    }

    @Nested
    class TestCNest{
        @Test
        void test_3(){
            System.out.println("我是第三个执行么？");
        }
    }

    @Nested
    class TestBNest{
        @Test
        void test_2(){
            System.out.println("我是第二个执行么？");
        }

    }

    @Nested
    class TestDNest{
        @Test
        void test_4(){
            System.out.println("我是第四个执行么？");
        }

    }

    final static String s = "{\"test\": 123}" ;

    @Test
    public void testGson(){
        Gson gson = new Gson();
        gson.fromJson(s, JsonObject.class).get("test");
    }


}
