package com.evie.autotest.extension;


import com.evie.autotest.annotation.JsonFileSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.util.Preconditions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Stream;


public class JsonFileArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<JsonFileSource> {

    private final BiFunction<Class, String, InputStream> inputStreamProvider;

    private static final ObjectMapper mapper = new ObjectMapper();


    private String[] resources;

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
            jsonObject = mapper.readValue(IOUtils.toString(inputStream, StandardCharsets.UTF_8), Object.class);
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
                () -> "Classpath resource [" + resource + "] does not exist");
    }
}
