package com.evie.autotest;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
}
