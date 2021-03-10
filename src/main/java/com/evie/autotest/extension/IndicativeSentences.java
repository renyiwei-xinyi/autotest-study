package com.evie.autotest.extension;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

public class IndicativeSentences extends DisplayNameGenerator.ReplaceUnderscores{
    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        return super.generateDisplayNameForClass(testClass);
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
        return super.generateDisplayNameForNestedClass(nestedClass) + "...";
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        String name = testClass.getSimpleName() + ' ' + testMethod.getName();
        return name.replace('_', ' ') + '.';
    }

}
