package com.evie.autotest.extension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.*;


import java.lang.reflect.Method;

public class TimingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback,BeforeAllCallback, AfterAllCallback {

    private static final Logger logger = LogManager.getLogger(TimingExtension.class.getName());

    private static final String METHOD_START_TIME = "method start time";

    private static final String ALL_TIME = "all time";

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        getStore(context).put(METHOD_START_TIME, System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        long startTime = getStore(context).remove(METHOD_START_TIME, long.class);
        long duration = System.currentTimeMillis() - startTime;

        logger.info(
                String.format("******** Method [-%s-] took %s ms. ********", testMethod.getName(), duration));
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        getClassStore(context).put(ALL_TIME, System.currentTimeMillis());

    }

    @Override
    public void afterAll(ExtensionContext context) {
        long allTime = getClassStore(context).remove(ALL_TIME, long.class);
        long duration = System.currentTimeMillis() - allTime;
        logger.info(
                String.format("******** All Test took %s ms. ********", duration)
        );

    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
    }

    private ExtensionContext.Store getClassStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestClass()));
    }



}
