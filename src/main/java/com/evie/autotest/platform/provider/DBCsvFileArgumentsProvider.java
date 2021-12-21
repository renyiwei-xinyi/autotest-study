package com.evie.autotest.platform.provider;


import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
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
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * path 是相对路径 如果填绝对路径会抛异常
 */
public class DBCsvFileArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<DBCsvFileSource>, ParameterResolver {

    private final BiFunction<Class, String, InputStream> inputStreamProvider;

    public static final CsvMapper csvMapper = new CsvMapper();

    public static CsvSchema schema = CsvSchema.emptySchema()
            .withHeader()
            .withColumnReordering(false)
            .withNullValue("NULL")
            ;

    private String[] resources;
    private Class<?> type;


    public DBCsvFileArgumentsProvider() throws Exception {
        this(Class::getResourceAsStream);
    }

    DBCsvFileArgumentsProvider(BiFunction<Class, String, InputStream> inputStreamProvider) {
        this.inputStreamProvider = inputStreamProvider;
    }

    private static Stream<Object> values(InputStream inputStream, Class<?> type) {
        try {

            Iterator<Object> iterator = csvMapper
                    .readerFor(type)
                    .with(schema)
                    .readValues(inputStream)
                    .readAll()
                    .iterator()
                    ;

            return getObjectStream(iterator);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Stream<Object> getObjectStream(Iterator<Object> iterator) {

        Iterable<Object> iterable = () -> iterator;

        return StreamSupport.stream(iterable.spliterator(), true);

    }

    @Override
    public void accept(DBCsvFileSource DBCsvFileSource) {
        resources = DBCsvFileSource.files();
        type = DBCsvFileSource.type();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Arrays.stream(resources)
                .map(resource -> openInputStream(context, resource))
                .flatMap(inputStream -> values(inputStream, type))
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
        return parameterContext.isAnnotated(DBCsvFileSource.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getCsvFileValue(parameterContext.getParameter(), extensionContext);
    }

    private Object getCsvFileValue(Parameter parameter, ExtensionContext context) {
        Class<?> type = parameter.getType();

        return Arrays.stream(parameter.getDeclaredAnnotation(DBCsvFileSource.class).files())
                .map(resource -> openInputStream(context, resource))
                .flatMap(inputStream -> values(inputStream, type))
                .collect(Collectors.toList());

    }
}
