package com.evie.autotest;

import com.evie.autotest.extension.TimeExecutionLogger;
import com.evie.autotest.extension.TestLifecycleLogger;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
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
    @Tag("example")
    void test_127389172() {
        //三元运算符
        int i = 1;
        String s = "success";
        String a = "fail";
        String f = i + 2 >= 3 ?
                s : a;
        LOGGER.info(f);

        System.out.println();
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

    @Test
    public void testBigDecimal(){
        BigDecimal num1 = new BigDecimal(100);
        BigDecimal num2 = new BigDecimal("1.5");
        BigDecimal divide = num1.divide(num2, 0, BigDecimal.ROUND_HALF_UP);
        System.out.println(divide);

    }

    @Test
    void test_add(){
        int x=4;
        System.out.println(x++);
    }

    @Test
    void test_add_equal(){
        // 因为在 Java 中 + 操作符的优先级大于 ==，
        // 所以输出部分表达式等于 “s1 == s2 is:runoob” == “runoob”，该表达式计算结果为 false。
        String s1 = "runoob";
        String s2 = "runoob";
        System.out.println("s1 == s2 is:" + s1 == s2);
    }

    @Test
    void test_for_delete(){

        ArrayList<String> list1 = new ArrayList<>();

        list1.add("0");
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        list1.add("5");

        ArrayList<String> list2 = new ArrayList<>();

        list2.add("0");
        list2.add("1");
        list2.add("5");
        list2.add("6");
        list2.add("7");
        list2.add("8");

        // 预期 list1 删除 2 3 4
        list1.removeIf(s -> !list2.contains(s));

        System.out.println(list1);
    }


}
