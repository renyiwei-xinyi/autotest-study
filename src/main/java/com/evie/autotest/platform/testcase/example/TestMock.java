package com.evie.autotest.platform.testcase.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ryw@xinyi
 */
public class TestMock {
    @Test
    public void testAnswer1(){
        List mock = Mockito.mock(List.class);
        Mockito.doAnswer(new CustomAnswer()).when(mock).get(Mockito.anyInt());
        assertEquals("大于三", mock.get(4));
        assertEquals("小于三", mock.get(2));
    }


    public static class CustomAnswer implements Answer<String> {
        public String answer(InvocationOnMock invocation){
            Object[] args = invocation.getArguments();
            Integer num = (Integer)args[0];
            if( num>3 ){
                return "大于三";
            } else {
                return "小于三";
            }
        }
    }


}
