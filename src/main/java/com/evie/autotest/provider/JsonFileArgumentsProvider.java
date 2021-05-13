package com.evie.autotest.provider;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.util.Preconditions;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * path 是相对路径 如果填绝对路径会抛异常
 */
public class JsonFileArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<JsonFileSource>, ParameterResolver {

    private final BiFunction<Class, String, InputStream> inputStreamProvider;

    private static final ObjectMapper mapper = new ObjectMapper();


    private String[] resources;

    private static Class<?> type;


    public JsonFileArgumentsProvider() throws Exception {
        this(Class::getResourceAsStream);
    }

    JsonFileArgumentsProvider(BiFunction<Class, String, InputStream> inputStreamProvider) {
        this.inputStreamProvider = inputStreamProvider;
    }

    private static Stream<Object> values(InputStream inputStream) {
        Object jsonObject = null;
        try {
            //为了处理Date属性，需要调用 findAndRegisterModules 方法
            mapper.findAndRegisterModules();
            jsonObject = mapper.readValue(inputStream, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getObjectStream(jsonObject);
    }

    static Stream<Object> getObjectStream(Object jsonObject) {

        return Stream.of(jsonObject);
    }

    @Override
    public void accept(JsonFileSource jsonFileSource) {
        resources = jsonFileSource.files();
        type = jsonFileSource.type();

    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Arrays.stream(resources)
                .map(resource -> openInputStream(context, resource))
                .flatMap(JsonFileArgumentsProvider::values)
                .map(Arguments::of);
    }

    private InputStream openInputStream(ExtensionContext context, String resource) {
        Preconditions.notBlank(resource, "Classpath resource [" + resource + "] must not be null or blank");
        Class<?> testClass = context.getRequiredTestClass();
        return Preconditions.notNull(inputStreamProvider.apply(testClass, resource),
                () -> testClass + "\n Classpath resource [" + resource + "] does not exist");
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(JsonFileSource.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getJsonFileValue(parameterContext.getParameter(), extensionContext);
    }

    private Object getJsonFileValue(Parameter parameter, ExtensionContext context) {
        Class<?> type = parameter.getType();

        return Arrays.stream(parameter.getDeclaredAnnotation(JsonFileSource.class).files())
                .map(resource -> openInputStream(context, resource))
                .flatMap(JsonFileArgumentsProvider::values)
                .collect(Collectors.toList());

    }
}
