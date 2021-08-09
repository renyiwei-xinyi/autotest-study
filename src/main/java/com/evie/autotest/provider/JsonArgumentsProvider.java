package com.evie.autotest.provider;

import com.evie.autotest.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

public class JsonArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<JsonSource> {

    private String value;
    private Class<?> type;

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

        return getArguments(this.value, this.type).map(Arguments::of);
    }

    @Override
    public void accept(JsonSource jsonSource) {
        this.value = jsonSource.value();
        this.type = jsonSource.type();
    }

    private Stream<Object> getArguments(String value, Class<?> type) {

        Object jsonObject = JsonUtils.readValue(value, type);

        return getObjectStream(jsonObject);
    }
    static Stream<Object> getObjectStream(Object jsonObject) {
        return Stream.of(jsonObject);
    }
}
