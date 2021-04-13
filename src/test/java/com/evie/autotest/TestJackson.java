package com.evie.autotest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class TestJackson {

    @Data
    static class User{
        String userId;
        String userName;
        String age;
    }

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void test_1237817() throws IOException {
        TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};
        List<User> o = mapper.readValue(TestJackson.class.getResourceAsStream("/user.yaml"), typeReference);
    }
}

