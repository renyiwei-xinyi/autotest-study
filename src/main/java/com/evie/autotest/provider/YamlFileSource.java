package com.evie.autotest.provider;

import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(YamlFileArgumentsProvider.class)
public @interface YamlFileSource {
    String[] files(); /* 相对路径 ，绝对路径会抛异常找不到*/
}
