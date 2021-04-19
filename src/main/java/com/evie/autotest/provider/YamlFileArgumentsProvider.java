package com.evie.autotest.provider;


import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.util.Preconditions;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * path 是相对路径 如果填绝对路径会抛异常
 */
public class YamlFileArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<YamlFileSource> {

    private final BiFunction<Class, String, InputStream> inputStreamProvider;

    private String[] resources;


    public YamlFileArgumentsProvider() throws Exception {
        this(Class::getResourceAsStream);
    }

    YamlFileArgumentsProvider(BiFunction<Class, String, InputStream> inputStreamProvider) {
        this.inputStreamProvider = inputStreamProvider;
    }

    private static Stream<Object> values(InputStream inputStream) {
        Iterable<Object> yamlObjects = null;
        Yaml yaml = new Yaml();
        yamlObjects = yaml.loadAll(inputStream);
        assert yamlObjects != null;
        return getObjectStream(yamlObjects);
    }

    static Stream<Object> getObjectStream(Iterable<Object> yamlObject) {

        return StreamSupport.stream(yamlObject.spliterator(), true);

    }

    @Override
    public void accept(YamlFileSource yamlFileSource) {
        resources = yamlFileSource.files();

    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Arrays.stream(resources)
                .map(resource -> openInputStream(context, resource))
                .flatMap(YamlFileArgumentsProvider::values)
                .map(Arguments::of);
    }

    private InputStream openInputStream(ExtensionContext context, String resource) {
        Preconditions.notBlank(resource, "Classpath resource [" + resource + "] must not be null or blank");
        Class<?> testClass = context.getRequiredTestClass();
        return Preconditions.notNull(inputStreamProvider.apply(testClass, resource),
                () -> "Classpath resource [" + resource + "] does not exist");
    }
}
