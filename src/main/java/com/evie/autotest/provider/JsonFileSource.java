package com.evie.autotest.provider;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;


import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(JsonFileArgumentsProvider.class)
@ParameterizedTest
public @interface JsonFileSource {
    String[] files(); /* 相对路径 ，绝对路径会抛异常找不到*/

    Class<?> type() default Object.class;

    boolean isArrayType() default false;
}
