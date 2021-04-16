package com.evie.autotest.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ContextUtil {

    private static final Logger logger = LogManager.getLogger(ContextUtil.class.getName());


    public static ExtensionContext.Store getClassStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(context.getClass(), context.getRequiredTestClass()));
    }
}
