package com.evie.autotest.platform.testcase.example.xUnit0410;

import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONSupport;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.evie.autotest.util.db.DataMap;
import com.evie.autotest.platform.provider.*;
import com.evie.autotest.util.db.PrintTable;
import com.evie.autotest.platform.extension.IndicativeSentences;
import com.evie.autotest.platform.extension.TestLifecycleLogger;
import com.evie.autotest.platform.extension.TimeExecutionLogger;
import com.evie.autotest.platform.provider.Random;
import com.evie.autotest.util.JsonUtils;
import com.evie.autotest.util.YamlUtils;
import com.evie.autotest.util.RetryHandler;
import com.microsoft.playwright.options.Cookie;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * @author ryw@xinyi
 * 集成测试用例中 一个 @Test 是一个 步骤 对应一个基础的校验； 一个 class 就是一个集成测试用例 有依赖的顺序
 */


@ExtendWith({RandomParameters.class, JsonFileArgumentsProvider.class, YamlFileArgumentsProvider.class})
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

        assertTrue(printTable.printAsTable("test", map, dataMap),
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
     *
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
     *
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
     *
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
     *
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

            LOGGER.info("是否要执行！");

            assertNotEquals(i, j);

        }

        @RepeatedTest(100)
        void injectsDouble(@Random double d) {
            LOGGER.info(d);

            assertEquals(0.0, d, 1.0);

        }

    }

    final List<String> fruits = Arrays.asList("apple", "banana", "lemon");

    /**
     * 扩展功能 测试模板上下文
     * @param fruit
     */
    @TestTemplate
    @ExtendWith(TestTemplateContextProvider.class)
    void testTemplate(String fruit){
        assertTrue(fruits.contains(fruit));

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
    void test_12989(String test) {
        LOGGER.info(test);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test"})
    void test_12912189(String test) {
        LOGGER.info(test);
    }

    @ParameterizedTest
    @MethodSource("test_12731972")
    void test_12731972(Object testDate) {
        System.out.println(JSON.toJSONString(testDate));
    }

    static Stream<Map<String, Object>> test_12731972() {
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("str", 12312);
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(jsonObject);

        return maps.stream();
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/test/csv.csv")
    void test_12781279(ArgumentsAccessor arguments) {
        // 对于 参数源有多个参数情况 可以用ArgumentsAccessor 来代替
        for (int i = 0; i < arguments.size(); i = i + 1) {
            Object o = arguments.get(i);
            LOGGER.info(o);
        }
    }

    @Test
    void test_1209809809(){
        Object a = YamlUtils.readFile("src/main/resources/test/csv.csv");
        LOGGER.info(a);
    }


    @ParameterizedTest
    @CsvFileSource(files = {
            "src/test/resources/test/test5.csv",
            "src/test/resources/test/test6.csv"
    })
    void test_127812279(ArgumentsAccessor arguments) {
        // 对于 参数源有多个参数情况 可以用ArgumentsAccessor 来代替
        for (int i = 0; i < arguments.size(); i = i + 1) {
            Object o = arguments.get(i);
            LOGGER.info(o);
        }
    }

    @DBCsvFileSource(files = "/test/exceltext.csv")
    void test_1278129(Map<String, Object> arguments) {
        // 对于 参数源有多个参数情况 可以用ArgumentsAccessor 来代替
        System.out.println(arguments.size());

        System.out.println(arguments);

        Object o = arguments.get("12121_title");
        System.out.println(o);
    }


    @Data
    @EqualsAndHashCode(callSuper=false)
    static class dataJson extends JSONSupport {
        public boolean boolean1111;
        public int test;
        public String string;

    }

    @DisplayName("将json对象转换为指定的java对象")
    @JsonFileSource(files = "/test/json.json", type = dataJson.class)
    void test_1273127(dataJson test) {
        System.out.println(test);
        Assertions.assertTrue(test.isBoolean1111());
    }

    @DisplayName("将json字符串对象转换为指定的java对象")
    @JsonSource(value = "{\"boolean1111\": true,\"test\": 12312,\"string\": \"qweqoweu\"}",
            type = dataJson.class)
    void test_1273122127(dataJson test) {
        System.out.println(test);
        Assertions.assertTrue(test.isBoolean1111());
    }

    @Data
    static class dataYaml {
        public List<Boolean> booleans;
        public List<Float> floats;
        public List<Integer> ints;

        public Map<String, Object> nulls;
        public List<String> string;
        public List<Date> date;
        public List<DateTime> datetime;

    }

    @DisplayName("将yaml对象转换为指定的java对象")
    @YamlFileSource(files = "/test/yaml.yaml", type = dataYaml.class)
    void test_1223273127(dataYaml test) {

        System.out.println(test.getClass());

    }

    @DisplayName("将csv对象转换为指定的java对象")
    @DBCsvFileSource(files = "/test/exceltext.csv", type = DataMap.class)
    void test_1724917(DataMap data){
        HashMap<String, String> map = new HashMap<>();
        System.out.println(data.getClass());
        System.out.println(data.get("qwe_title"));

        map.put("qwe_title", "qweqw");
        assertEquals(1,1);

        assertTrue(printTable.printAsTable("test", map, data),
                "校验数据失败！");

    }


    @JsonFileSource(files = {"/test/json.json", "/test/json2.json"})
    void test_127381273987(Map<String, Object> test) {
        JsonUtils.printJson(test);
    }


    @JsonSource(value = "[1,2]", isArrayType = true)
    void test_12739172(List<Integer> jsonObject) {
        JsonUtils.printJson(jsonObject);
    }


    @YamlFileSource(files = {
            "/test/moreYaml.yaml",
            "/test/yaml.yaml"
    })
    // 是否多线程执行用例
    @Execution(ExecutionMode.SAME_THREAD)//单线程执行/顺序执行
    void test_1273812987(Object test) {
        LOGGER.info(test);
//        RetryHandler.retryTillNoError(() -> {
//            assertEquals(1, 2);
//        }, 5);
    }

    //@Disabled
    @ParameterizedTest
    @ValueSource(strings = {"test", "121", "1212", "21233", "qweqwe  asda"})
    @Execution(ExecutionMode.CONCURRENT)//并行执行
    void test_skip(String test) {
        LOGGER.info(test);
        RetryHandler.retryTillNoError(() -> {

        }, 5);

    }

    @Disabled
    @Test
    void test_skip_test() {
        LOGGER.info("test");
    }

    @Test
    @Timeout(value = 5)
    public void test_123718() throws InterruptedException {
        int i = 0;
        while (2 == 2 + i) {
            Thread.sleep(250); // custom poll interval
            LOGGER.info(i);
            i = i+1;
        }
    }

    @JsonFileSource(files = "/test/json.json")
        //@YamlFileSource(files = "/test/yaml.yaml")
    void test_12973189(Object test) {
        JSONObject jsonObject = JSONUtil.parseObj(test);
        jsonObject.set("test", 456);

        System.out.println(test);
        System.out.println(jsonObject);
    }


    @ParameterizedTest
    @RepeatedTest(20)
    @ValueSource(ints = 12)
        // 重复测试和 参数化测试无法组合使用
    void test(@Random int s) {
        LOGGER.info(s);
        LOGGER.info(s);

    }
    //======================================重复执行==============================

    StringBuilder s = new StringBuilder("nihao");

    @RepeatedTest(10)
    void test_1273917() {
        LOGGER.info("我是一个重复执行的测试方法，我会执行好多次。。。");
        LOGGER.info(s);
    }

    @BeforeEach
    void beforeEach() {
        s.append("*");
        LOGGER.error("在重复测试中 我每次都要执行么？");
    }


    @JsonFileSource(files = "/example/cookies2.json")
    void test_12397979(Object o) {

        YamlUtils.writeFile("src/test/resources/example/cookie3.yaml", o);
    }

    @YamlFileSource(files = "/test/yaml.yaml")
    @JsonFileSource(files = "/example/cookies2.json")
    void test_12738(Object o) {
        System.out.println(o);

    }

    @RepeatedTest(100)
    void test_1271982797() {
        java.util.Random random = new java.util.Random();
        // 调节比例
        int i = random.nextInt(3);

        String[] test = {"111", "2222"};
        System.out.println(i + "\n" + test.length);
    }

    @Test
    void test_790790709(@JsonFileSource(files = "/example/cookies2.json") List<Object> JsonData,
                        @YamlFileSource(files = "/test/yaml.yaml") List<Object> yamlData,
                        @Random int a) {


        LOGGER.info(JsonData);
        LOGGER.info(yamlData);
        LOGGER.info(a);
    }

    @JsonFileSource(files = "/example/cookies2.json", isArrayType = true)
    void test_7907925323509(List<Cookie> JsonData) {

        LOGGER.info(JsonData);
    }

    @Test
    @Timeout(5)
        // Poll at most 5 seconds
    void pollUntil() throws InterruptedException {
        int i = 0;
        while (true) {
            Thread.sleep(250); // custom poll interval 5000 / 250 = 20 time

            i++;
            if (i == 5){
                continue;// 终止本次执行 继续下个执行
            }else {
                LOGGER.info("sleep");
            }
            if (i == 10){
                break;// 跳出循环
            }
        }
        return; // 结束方法
        // Obtain the asynchronous result and perform assertions
    }

    @Data
    static class MemberInfo {

        public String name;
        public String alias;
        public String id;
        public String phone;
        public String landline;
        public String email;
        public String address;
        public String jobTitle;
    }

    @Timeout(5)
    @YamlFileSource(files = "/example/web0414/memberInfo.yaml", type = MemberInfo.class)
    void test_1239(MemberInfo memberInfo){
        System.out.println(memberInfo.getClass());

    }



    @Test
    void test_12378(){
        int[] a = {1,2};
        int length = a.length;
    }

    public int[] primes = {8,89,6,3,77,55,9};

    @Test
    public void nthSuperUglyNumber() {
        // 1、数组排列问题
        // 简单选择排序
        // 基本思想：在要排序的一组数中，选出最小的一个数与第一个位置的数交换；
        //
        //然后在剩下的数当中再找最小的与第二个位置的数交换，如此循环到倒数第二个数和最后一个数比较为止。
        int a = 0;
        for(int i = 0; i < primes.length-1; i++){
            // 假设第一个是最小的
            // 获取当前 下标
            a = i;
            // 获取当前 值
            int temp = primes[i];

            int n = i+1;

            for (;n<primes.length;n++){
                // 如果下一位 比当前位 小 那么 记录一下 值 和 下标
                if(temp > primes[n]){
                    temp = primes[n];
                    a = n;

                }
            }

            // 互换位置
            // 当前位置的数 给到 最小的数
            primes[a] = primes[i];
            // 最小的数 给到 当前位置的数
            primes[i] = temp;


        }
        System.out.println(Arrays.toString(primes));
        // 2、以质数数组 为 因子 构建 一个 超级丑数 数组
        // 3、当构建到 第n个时 return
        // 4、超级丑数在 32-bit  带符号证书范围内

    }






}
