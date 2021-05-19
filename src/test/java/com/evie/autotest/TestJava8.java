package com.evie.autotest;

import com.evie.autotest.extension.TimeExecutionLogger;
import com.evie.autotest.extension.TestLifecycleLogger;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

    /**
     * 5 stream forEach 遍历的是entry 所以是一个参数
     * stream forEach java8 lambda
     * @param map
     */
    public void streamForEachTest(Map<Integer, Integer> map){
        long before = System.currentTimeMillis();
        //map.entrySet().stream().forEach( (entry) -> {System.out.println("Key = " + entry.getKey()+ ", Value = "+ entry.getValue());} );
        //map.entrySet().stream().forEach( System.out::println);
        map.entrySet().stream().forEach( (entry) -> {} );
        long after = System.currentTimeMillis();
        System.out.println("map.entrySet().stream().forEach time=\t"  + (after - before));

        // 一个参数可以省略()  一条语句可以省略{}
        //map.entrySet().stream().forEach(entry -> System.out.println("Key = " + entry.getKey()+ ", Value = "+ entry.getValue()) );
    }

    /**
     * 4 forEach 遍历的是键值，所以是两个参数
     * forEach java8 lambda
     * @param map
     */
    public void forEachTest(Map<Integer, Integer> map){
        long before = System.currentTimeMillis();
        //map.forEach( (key,value) -> {System.out.println("Key = " + key+ ", Value = "+ value);} );
        map.forEach( (key,value) -> {} );
        long after = System.currentTimeMillis();
        System.out.println("map.forEach time=\t"  + (after - before));

        // 一条语句可以省略{}
        //map.forEach( (key,value) -> System.out.println("Key = " + key+ ", Value = "+ value) );
    }

    /**
     * 3（1）iterator type
     * 迭代器 带泛型
     * @param map
     */
    public void iteratorTypeTest(Map<Integer, Integer> map){
        long before = System.currentTimeMillis();
        Iterator<Map.Entry<Integer, Integer>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, Integer> entry = entries.next();
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        long after = System.currentTimeMillis();
        System.out.println("map.entrySet().iterator() time=\t"  + (after - before));
    }


    @Test
    public void StringBuilder(){
        StringBuilder mock = new StringBuilder("test");
        mock.append(",test2");
        String x = mock.toString();
        System.out.println(x);
    }


}
