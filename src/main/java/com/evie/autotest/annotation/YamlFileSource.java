package com.evie.autotest.annotation;

import com.evie.autotest.extension.YamlFileArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(YamlFileArgumentsProvider.class)
public @interface YamlFileSource {
    String[] files();
}
