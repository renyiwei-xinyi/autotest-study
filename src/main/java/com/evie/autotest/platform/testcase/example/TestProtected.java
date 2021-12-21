package com.evie.autotest.platform.testcase.example;

import org.junit.jupiter.api.Test;

public class TestProtected {

     static class test1{
         protected boolean isTrue(){
             return false;
         }
     }

     static class test2 extends test1{
         protected boolean isTrue(){
             return true;
         }

         @Test
         void test_127381(){
             System.out.println(isTrue());
         }
     }


     @Test
     void test_173189(){
         test2 test2 = new test2();
         test1 test1 = new test1();
         System.out.println(test2.isTrue());
         System.out.println(test1.isTrue());
     }
}
