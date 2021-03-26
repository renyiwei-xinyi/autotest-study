package com.evie.autotest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

public class Demo {


    @BeforeEach
    void before(TestInfo source){
        System.out.println(source);
    }

    @CsvFileSource(files = "src/test/resources/test/ihometest.csv")
    @ParameterizedTest
    void test(ArgumentsAccessor arguments){



    }
}
