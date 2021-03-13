package com.evie.autotest.extension;

import com.evie.autotest.annotation.JsonSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

public class JsonArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<JsonSource> {

    private String value;
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        Stream<Object> arguments = getArguments(this.value);
        if(arguments == null) {
            throw new Exception("json 入参有错误");
        }
        return getArguments(this.value).map(Arguments::of);
    }

    @Override
    public void accept(JsonSource jsonSource) {
        this.value = jsonSource.value();
    }

    private Stream<Object> getArguments(String value) throws JsonProcessingException {
        Object jsonObject = objectMapper.readValue(value,Object.class);
        return getObjectStream(jsonObject);
    }
    static Stream<Object> getObjectStream(Object jsonObject) {
        return Stream.of(jsonObject);
    }
}
