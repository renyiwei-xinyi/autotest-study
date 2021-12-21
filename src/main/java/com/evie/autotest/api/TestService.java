package com.evie.autotest.api;


public interface TestService {

    String run(String className, String... methodName);

    String run(String className);


}
