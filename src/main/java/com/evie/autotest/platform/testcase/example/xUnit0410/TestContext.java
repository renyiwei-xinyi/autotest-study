package com.evie.autotest.platform.testcase.example.xUnit0410;

import com.evie.autotest.platform.provider.*;
import com.evie.autotest.util.JsonUtils;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;



@ExtendWith({RandomParameters.class, ContextArgumentsProvider.class, JsonFileArgumentsProvider.class, YamlFileArgumentsProvider.class,})
public class TestContext {

    @ValueSource(strings = "testContext.class")
    @ParameterizedTest
    void test_13718(String a ,Context context) {
        context.getRequest().setValue(a);
    }

    @Test
    void test_127389(Context context, @Random int a){
        Assertions.assertEquals("testContext.class", context.getRequest().getValue());
        context.getRequest().setRandom(a);
    }

    @AfterEach
    void after_each(Context context){
        System.out.println(JsonUtils.parseObjPretty(context.getRequest()));
    }



    @Nested
    class test1{
        @ValueSource(strings = "test1.class")
        @ParameterizedTest
        void test_13718(String a ,Context context) {
            context.getRequest().setValue(a);
        }

    }

    @Nested
    class test2{
        @ValueSource(strings = "test2.class")
        @ParameterizedTest
        void test_13718(String a ,Context context) {
            context.getRequest().setValue(a);
        }

    }

}
