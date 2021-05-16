package com.evie.autotest.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.util.ToStringBuilder;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

public class ContextArgumentsProvider implements ParameterResolver {

    private static volatile DefaultContext defaultContext;

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == Context.class;
    }

    @Override
    public Context resolveParameter(ParameterContext parameterContext,
                                    ExtensionContext extensionContext) throws ParameterResolutionException {
        if (defaultContext == null) {
            synchronized (ContextArgumentsProvider.class) {
                if (defaultContext == null) {
                    defaultContext = new DefaultContext(extensionContext);
                }
            }
            //class 级别 共用上下文对象
        } else if (!defaultContext.getTestClass().equals(extensionContext.getTestClass())) {
            synchronized (ContextArgumentsProvider.class) {
                if (!defaultContext.getTestClass().equals(extensionContext.getTestClass())) {
                    defaultContext = new DefaultContext(extensionContext);
                }
            }
        } else {
            synchronized (ContextArgumentsProvider.class) {
                defaultContext.setDisplayName(extensionContext.getDisplayName());
                defaultContext.setTags(extensionContext.getTags());
                defaultContext.setTestMethod(extensionContext.getTestMethod());
            }
        }
        return defaultContext;
    }

    private static class DefaultContext implements Context {
        // 定义 上下文参数 可扩展
        private String displayName;
        private Set<String> tags;
        private Optional<Method> testMethod;

        private final Optional<Class<?>> testClass;
        private final ExternalParameter request;

        DefaultContext(ExtensionContext extensionContext) {
            this.displayName = extensionContext.getDisplayName();
            this.tags = extensionContext.getTags();
            this.testClass = extensionContext.getTestClass();
            this.testMethod = extensionContext.getTestMethod();
            this.request = new ExternalParameter();
        }

        public Optional<Class<?>> getTestClass() {
            return this.testClass;
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public Set<String> getTags() {
            return this.tags;
        }

        public Optional<Method> getTestMethod() {
            return this.testMethod;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public void setTags(Set<String> tags) {
            this.tags = tags;
        }

        public void setTestMethod(Optional<Method> testMethod) {
            this.testMethod = testMethod;
        }

        public ExternalParameter getRequest() {
            return this.request;
        }

        public String toString() {
            return (new ToStringBuilder(this))
                    .append("displayName", this.displayName)
                    .append("tags", this.tags)
                    .append("testClass", nullSafeGet(Optional.ofNullable(this.testClass)))
                    .append("testMethod", nullSafeGet(Optional.ofNullable(this.testMethod)))
                    .append("request", this.request)
                    .toString();
        }

        private static Object nullSafeGet(Optional<?> optional) {
            return optional != null ? optional.orElse(null) : null;
        }
    }
}
