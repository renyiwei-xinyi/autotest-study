package com.evie.autotest.provider;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(JsonArgumentsProvider.class)
@ParameterizedTest
public @interface JsonSource {
    String value();

    Class<?> type() default JSONObject.class;

    boolean isArrayType() default false;
}
