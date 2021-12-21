package com.evie.autotest.platform.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

public class JsonArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<JsonSource> {

    private String value;
    private Class<?> type;
    private boolean isArrayType;

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

        return getArguments(this.value, this.type, this.isArrayType).map(Arguments::of);
    }

    @Override
    public void accept(JsonSource jsonSource) {
        this.value = jsonSource.value();
        this.type = jsonSource.type();
        this.isArrayType = jsonSource.isArrayType();
    }

    private Stream<Object> getArguments(String value, Class<?> type, boolean isArrayType) {

        type = isArrayType ? JSONArray.class : type;

        Object jsonObject = JSON.parseObject(value, type);

        return getObjectStream(jsonObject);
    }
    static Stream<Object> getObjectStream(Object jsonObject) {
        return Stream.of(jsonObject);
    }
}
