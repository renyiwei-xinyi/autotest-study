package com.evie.autotest.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.jupiter.params.provider.ArgumentsProvider;


import java.lang.reflect.Parameter;
import java.util.stream.Stream;

/**
 * @author ryw@xinyi
 */
public class RandomParameters implements ArgumentsProvider, AnnotationConsumer<Random>, ParameterResolver {



    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.isAnnotated(Random.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return getRandomValue(parameterContext.getParameter(), extensionContext);
    }

    private Object getRandomValue(Parameter parameter, ExtensionContext extensionContext) {
        Class<?> type = parameter.getType();
        java.util.Random random = extensionContext.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent(java.util.Random.class);
        if (int.class.equals(type)) {
            return random.nextInt();
        }
        if (double.class.equals(type)) {
            return random.nextDouble();
        }
        throw new ParameterResolutionException("No random generator implemented for " + type);
    }

    @Override
    public void accept(Random random) {

    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return null;
    }
}
