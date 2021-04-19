package com.evie.autotest.provider;


import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.util.Preconditions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * path 是相对路径 如果填绝对路径会抛异常
 */
public class DBCsvFileArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<DBCsvFileSource> {

    private final BiFunction<Class, String, InputStream> inputStreamProvider;

    public static CsvMapper csvMapper = new CsvMapper();

    public static CsvSchema schema = CsvSchema.emptySchema()
            .withHeader()
            .withColumnReordering(false)
            .withNullValue("NULL")
            ;

    private String[] resources;

    public DBCsvFileArgumentsProvider() throws Exception {
        this(Class::getResourceAsStream);
    }

    DBCsvFileArgumentsProvider(BiFunction<Class, String, InputStream> inputStreamProvider) {
        this.inputStreamProvider = inputStreamProvider;
    }

    private static Stream<Object> values(InputStream inputStream) {
        try {

            Iterator<Object> iterator = csvMapper
                    .readerFor(Object.class)
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
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Arrays.stream(resources)
                .map(resource -> openInputStream(context, resource))
                .flatMap(DBCsvFileArgumentsProvider::values)
                .map(Arguments::of);
    }

    private InputStream openInputStream(ExtensionContext context, String resource) {
        Preconditions.notBlank(resource, "Classpath resource [" + resource + "] must not be null or blank");
        Class<?> testClass = context.getRequiredTestClass();
        return Preconditions.notNull(inputStreamProvider.apply(testClass, resource),
                () -> testClass + "\n Classpath resource [" + resource + "] does not exist");
    }
}
