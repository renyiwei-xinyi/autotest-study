package com.evie.example.xUnit0410;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.evie.autotest.atom.db.DataMap;
import com.evie.autotest.provider.*;
import com.evie.autotest.atom.db.PrintTable;
import com.evie.autotest.extension.IndicativeSentences;
import com.evie.autotest.extension.TestLifecycleLogger;
import com.evie.autotest.extension.TimeExecutionLogger;
import com.evie.autotest.provider.Random;
import com.evie.autotest.util.TextUtils;
import com.evie.autotest.util.RetryHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.provider.*;
import org.junit.platform.commons.util.StringUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * @author ryw@xinyi
 */


@ExtendWith(RandomParameters.class)
public class TestJunit5Example implements TestLifecycleLogger, TimeExecutionLogger {

    private static final Logger LOGGER = LogManager.getLogger(TestJunit5Example.class);

    private static final PrintTable printTable = new PrintTable();


    //====================================================== source ====================================================

    static class ClassCase {

        int case01(int a, int b) {

            try {

                int c = a / b;

                return c;

            } catch (ArithmeticException e) {

                throw new ArithmeticException("错误！！！除数为0！！！");

            }
        }
    }

    enum EnumCase {

        SPRING("春天"),

        SUMMER("夏天"),

        FALL("秋天"),

        WINTER("冬天");

        EnumCase(Object s) {

        }
    }

    //====================================================== test start ================================================


    @Test
    void test_a_2_a_1() {

        HashMap<String, String> map = new HashMap<>();

        DataMap dataMap = new DataMap();

        map.put("userId", "123456789");

        dataMap.put("USERID", "1321321");

        assertTrue(printTable.printAsTable("test", dataMap, map),
                "校验数据失败！");
    }

    @Disabled
    @Test
    void test_a_1_a_1() {

        assertEquals(1, 1 + 2, "test asserts");

        LOGGER.info("junit 5 ! Hello! ");

    }

    @Test
    void test_a_1_a_2() {

        //================================
        ClassCase aCase = new ClassCase();
        ArithmeticException arithmeticException = assertThrows(
                ArithmeticException.class, () -> {
                    int i = aCase.case01(2, 0);
                }
        );
        LOGGER.error(arithmeticException);
        //====================================
        assertDoesNotThrow(() -> {
                    aCase.case01(2, 2);
                }
        );

    }

    @Test
    void test_a_1_a_3() {

        String a = "123";
        int b = 123;
        assumingThat(a.equals(b),
                () -> {
                    assertEquals(b, 123);
                    LOGGER.info("假设成功");
                });
        LOGGER.error("假设失败");
        assumingThat(b == 123,
                () -> {
                    assertEquals(1 + 1, 2);
                    LOGGER.info("假设成功");
                }
        );

    }

    //---------------------------------------------------参数化测试--------------------------------------------

    /**
     * 参数化测试 空 null 参数
     * @param o
     */
    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("参数化测试;my third test case; learn parameterize ")
    void test_a_1_b_1(List<Integer> o) {

        assertNull(o);

    }

    /**
     * 参数化测试 基本数据类型 value null&empty组合
     * @param o
     */
    @ParameterizedTest
    @ValueSource(strings = {"RYW", "TJY", "123"})
    @NullAndEmptySource
    @DisplayName("参数化测试;my fourth test case; learn parameterize")
    void test_a_1_b_2(String o) {

        assertEquals("RYW", o, "测试参数化 string 字符串的传递");

        LOGGER.info(o);
    }

    /**
     * 参数化测试 枚举数据测试 - 指定枚举
     * @param o
     */
    @ParameterizedTest
    @EnumSource(value = EnumCase.class, mode = EnumSource.Mode.EXCLUDE, names = {"SPRING"})
    @DisplayName("参数化测试;my fifth test case; learn parameterize")
    void test_a_1_b_3(EnumCase o) {

        boolean contains = EnumSet.of(EnumCase.SPRING).contains(o);

        LOGGER.warn(o + String.valueOf(contains));

    }

    /**
     * 参数化测试 枚举数据测试 - 正则匹配
     * @param timeUnit
     */
    @ParameterizedTest
    @EnumSource(value = TimeUnit.class, mode = EnumSource.Mode.MATCH_ALL, names = "^(M|N).+SECONDS$")
    void testWithEnumSourceRegex(TimeUnit timeUnit) {

        String name = timeUnit.name();

        assertTrue(name.startsWith("M") || name.startsWith("N"));

        assertTrue(name.endsWith("SECONDS"));

    }


    /**
     * 参数类型转换器
     * 显示转换
     * ，junit-jupiter-params仅仅提供了一个可以作为参考实现的显式参数转换器：JavaTimeArgumentConverter
     *
     * @param argument
     */
    @ParameterizedTest
    @ValueSource(strings = {"2017.01.01", "2017.12.31"})
    void testWithExplicitJavaTimeConverter(@JavaTimeConversionPattern("yyyy.MM.dd") LocalDate argument) {
        assertEquals(2017, argument.getYear());

    }

    @Test
    void reportSingleValue(TestReporter testReporter) {

        testReporter.publishEntry("a key", "a value");

    }

    @Test
    void reportSeveralValues(TestReporter testReporter) {

        HashMap<String, String> values = new HashMap<>();

        values.put("user name", "dk38");

        values.put("award year", "1974");

        testReporter.publishEntry(values);//会在控制台输出

    }

    /**
     * 扩展功能 随机数输入
     */
    @ExtendWith(RandomParameters.class)
    static class MyRandomParametersTest implements TestLifecycleLogger, TimeExecutionLogger {


        @RepeatedTest(1)
        void injectsInteger(@Random int i, @Random int j) {
            LOGGER.info(i);
            LOGGER.info(j);

            assertNotEquals(i, j);

        }

        @RepeatedTest(100)
        void injectsDouble(@Random double d) {
            LOGGER.info(d);

            assertEquals(0.0, d, 1.0);

        }

    }

    static class A_year_is_not_supported {

        @Test
        void if_it_is_zero() {
        }

        @DisplayName("A negative value for year is not supported by the leap year computation.")
        @ParameterizedTest(name = "For example, year {0} is not supported.")
        @ValueSource(ints = {-1, -4})
        void if_it_is_negative(int year) {
        }

    }

    @DisplayNameGeneration(IndicativeSentences.class)
    static class A_year_is_a_leap_year {

        @Test
        void if_it_is_divisible_by_4_but_not_by_100() {
        }

        @ParameterizedTest(name = "Year {0} is a leap year.")
        @ValueSource(ints = {2016, 2020, 2048})
        void if_it_is_one_of_the_following_years(int year) {
        }

    }





    private static Stack<Object> stack;

    @Test
    @DisplayName("is instantiated with new Stack()")
    void isInstantiatedWithNew() {
        new Stack<>();
    }

    // Nested
    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createNewStack() {
            stack = new Stack<>();
        }

        @Test
        @DisplayName("is empty")
        void isEmpty() {
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("throws EmptyStackException when popped")
        void throwsExceptionWhenPopped() {
            assertThrows(EmptyStackException.class, stack::pop);
        }

        @Test
        @DisplayName("throws EmptyStackException when peeked")
        void throwsExceptionWhenPeeked() {
            assertThrows(EmptyStackException.class, stack::peek);
        }

        @Nested
        @DisplayName("after pushing an element")
        class AfterPushing {

            String anElement = "an element";

            @BeforeEach
            void pushAnElement() {
                stack.push(anElement);
            }

            @Test
            @DisplayName("it is no longer empty")
            void isNotEmpty() {
                assertFalse(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when popped and is empty")
            void returnElementWhenPopped() {
                assertEquals(anElement, stack.pop());
                assertTrue(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when peeked but remains not empty")
            void returnElementWhenPeeked() {
                assertEquals(anElement, stack.peek());
                assertFalse(stack.isEmpty());
            }
        }
    }

    @Test
    void test_1273891() {
        RetryHandler.retryTillNoError(() -> {
            assertEquals(1, 2);
        }, 5);
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

    @ParameterizedTest
    @ValueSource(strings = {"test"})
    void test_12989(String test){
        assertTrue(StringUtils.isBlank(test));
    }

    @ParameterizedTest
    @MethodSource
    void test_12731972(Object testDate) {
        TextUtils.printJsonString(testDate);
    }

    static Stream<Map<String, Object>> test_12731972(){
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("str",12312);
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(jsonObject);

        return maps.stream();
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/test/csv.csv")
    void test_12781279(ArgumentsAccessor arguments){
        // 对于 参数源有多个参数情况 可以用ArgumentsAccessor 来代替
        for (int i = 0; i<arguments.size();i=i+1){
            Object o = arguments.get(i);
            LOGGER.info(o);
        }
    }

    @ParameterizedTest
    @CsvFileSource(files = {
            "src/test/resources/test/test5.csv",
            "src/test/resources/test/test6.csv"
    })
    void test_127812279(ArgumentsAccessor arguments){
        // 对于 参数源有多个参数情况 可以用ArgumentsAccessor 来代替
        for (int i = 0; i<arguments.size();i=i+1){
            Object o = arguments.get(i);
            LOGGER.info(o);
        }
    }

    @ParameterizedTest
    @DBCsvFileSource(files = "/test/exceltext.csv")
    void test_1278129(Map<String, Object> arguments){
        // 对于 参数源有多个参数情况 可以用ArgumentsAccessor 来代替
        System.out.println(arguments.size());

        System.out.println(arguments);

        Object o = arguments.get("12121_title");
        System.out.println(o);
    }


    @ParameterizedTest
    @JsonFileSource(files = {"/test/json.json","/test/json2.json"})
    void test_127381273987(Object test) {
        TextUtils.printJsonString(test);
    }



    @ParameterizedTest
    @JsonSource(value = "[1,2]")
    void test_12739172(Object jsonObject) {
        TextUtils.printJsonString(jsonObject);
    }

    @ParameterizedTest
    @YamlFileSource(files = {
            "/test/moreYaml.yaml",
            "/test/yaml.yaml"
    })
    // 是否多线程执行用例
    @Execution(ExecutionMode.SAME_THREAD)
    void test_1273812987(Object test)  {
        LOGGER.info(test);
//        RetryHandler.retryTillNoError(() -> {
//            assertEquals(1, 2);
//        }, 5);
    }

    //@Disabled
    @ParameterizedTest
    @ValueSource(strings = {"test","121","1212","21233"})
    @Execution(ExecutionMode.CONCURRENT)
    void test_skip(Object test)  {
        LOGGER.info(test);
        RetryHandler.retryTillNoError(() -> {

        }, 5);
        //此处有BUG 用例实际是跳过没有执行 但是结果是 pass
    }

    @Disabled
    @Test
    void test_skip_test(){
        LOGGER.info("test");
    }

    @Test
    @Timeout(value = 5)
    public void test_123718() throws InterruptedException {
        int i = 0;
        while (2 == 2 + i) {
            Thread.sleep(250); // custom poll interval
            logger.info(i);
            i = i+1;
        }
    }

    @JsonFileSource(files = "/test/json.json")
    //@YamlFileSource(files = "/test/yaml.yaml")
    @ParameterizedTest
    void test_12973189(Object test){
        JSONObject jsonObject = JSONUtil.parseObj(test);
        jsonObject.set("test", 456);

        System.out.println(test);
        System.out.println(jsonObject);
    }



    @ParameterizedTest
    @RepeatedTest(20)
    @ValueSource(ints = 12) // 重复测试和 参数化测试无法组合使用
    void test(@Random int s){
        LOGGER.info(s);
        LOGGER.info(s);

    }
    //======================================重复执行==============================

    StringBuilder s = new StringBuilder("nihao");

    @RepeatedTest(10)
    void test_1273917(){
        LOGGER.info("我是一个重复执行的测试方法，我会执行好多次。。。");
        LOGGER.info(s);
    }

    @BeforeEach
    void beforeEach(){
        s.append("*");
        LOGGER.error("在重复测试中 我每次都要执行么？");
    }


    @JsonFileSource(files = "/example/cookies2.json")
    @ParameterizedTest
    void test_12397979(Object o) {

        TextUtils.yamlWriteTo("src/test/resources/example/cookie3.yaml", o);
    }





}
