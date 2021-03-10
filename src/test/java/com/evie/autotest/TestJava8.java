package com.evie.autotest;

import com.evie.autotest.interfaces.TimeExecutionLogger;
import com.evie.autotest.interfaces.TestLifecycleLogger;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class TestJava8 implements TestLifecycleLogger, TimeExecutionLogger {



    public static void  printValur(String str){
        System.out.println("print value : "+str);

    }

    List<String> al = Arrays.asList("a","b","c","d");


    @Test
    void main_a() {
        for (String a: al) {
            TestJava8.printValur(a);
        }

    }

    @Test
    void main_b() {
        al.forEach(x->{
            TestJava8.printValur(x);
        });
    }

    @Test
    void main_c() {
        al.forEach(TestJava8::printValur);

    }

    @Test
    void main_d(){
        Consumer<String> methodParam = TestJava8::printValur; //方法参数
        al.forEach(x -> methodParam.accept(x));//方法执行accept
    }

    @Test
    void main_e(){
        Consumer<String> methodParam = TestJava8::printValur; //方法参数
        al.forEach(methodParam);//方法执行accept
    }
}
