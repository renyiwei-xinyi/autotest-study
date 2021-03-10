package com.evie.autotest.annotation;

import com.evie.autotest.extension.JsonFileArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;


import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(JsonFileArgumentsProvider.class)
public @interface JsonFileSource {
    String[] files();
}
