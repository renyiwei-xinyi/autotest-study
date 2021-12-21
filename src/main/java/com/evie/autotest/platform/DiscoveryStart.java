package com.evie.autotest.platform;

import com.evie.autotest.example.xUnit0410.TestANest;
import com.evie.autotest.example.xUnit0410.TestContext;
import com.evie.autotest.example.xUnit0410.TestJunit5Example;
import com.evie.autotest.example.xUnit0410.TestLife;
import org.junit.platform.commons.util.Preconditions;
import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.engine.discovery.MethodSelector;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.*;

/**
 * @author evie
 */
public class DiscoveryStart {

    private final String className;
    private final String[] methodName;

    public DiscoveryStart(String className, String[] methodName){
     this.className = className;
     this.methodName = methodName;
    }

    public LauncherDiscoveryRequest request(){
        //筛选用例
        return LauncherDiscoveryRequestBuilder.request()
                .selectors(getMethodByName(getClassByName(this.className), this.methodName))
                // 自己定义一个 过滤器
                .filters()
                .build();
    }

    public static Class<?> getClassByName(String className) {

        switch (className) {
            case "TestLife":
                return TestLife.class;
            case "TestANest":
                return TestANest.class;
            case "TestContext":
                return TestContext.class;
            case "TestJunit5Example":
                return TestJunit5Example.class;
        }

        return null;

    }

    public static List<MethodSelector> getMethodByName(Class<?> testClass, String... methodName) {
        Preconditions.notNull(testClass, "Class must not be null");


        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(method -> Arrays.asList(methodName).contains(method.getName()))
                .map(method -> selectMethod(testClass, method))
                .collect(Collectors.toList());

    }



}
