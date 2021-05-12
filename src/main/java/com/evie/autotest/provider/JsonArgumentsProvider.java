package com.evie.autotest.provider;

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
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {

        return getArguments(this.value, this.type).map(Arguments::of);
    }

    @Override
    public void accept(JsonSource jsonSource) {
        this.value = jsonSource.value();
        this.type = jsonSource.type();
    }

    private Stream<Object> getArguments(String value, Class<?> type) throws JsonProcessingException {
        Object jsonObject = objectMapper.readValue(value,type);
        return getObjectStream(jsonObject);
    }
    static Stream<Object> getObjectStream(Object jsonObject) {
        return Stream.of(jsonObject);
    }
}
