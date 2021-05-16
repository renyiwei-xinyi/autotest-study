package com.evie.autotest.provider;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;


public interface Context {

    String getDisplayName();

    Set<String> getTags();

    Optional<Class<?>> getTestClass();

    Optional<Method> getTestMethod();
    /*
    上下文是 类级别的
     */
    ExternalParameter getRequest();

}
