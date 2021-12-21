package com.evie.autotest.platform.provider;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(YamlFileArgumentsProvider.class)
@ParameterizedTest
public @interface YamlFileSource {
    String[] files(); /* 相对路径 ，绝对路径会抛异常找不到*/

    Class<?> type() default Object.class;
}
