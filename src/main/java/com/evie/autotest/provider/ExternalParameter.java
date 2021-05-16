package com.evie.autotest.provider;

import lombok.Data;

@Data
public class ExternalParameter {

    private Object json;

    private Object yaml;

    private Object random;

    private Object csv;

    private Object value;

    private Class<?> javaBean;

}
