package com.evie.autotest.platform;

import com.evie.autotest.provider.ExternalParameter;
import com.evie.autotest.provider.TestContext;

public class RunTestClass {

    public TestContext context;

    public RunTestClass(){
        this.context = new TestContext();
        System.out.println("执行了么？");
    }

    public ExternalParameter parameter = new ExternalParameter();


    public void test(){

        System.out.println("method test!");
    }


}
