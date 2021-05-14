package com.evie.autotest.util;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class JsonUtilsTest {

    @Data
    static class User{
        private Integer id;
        private String email;
        private String password;
        private String phone;
    }

    @Test
    void printJson() {
    }

    @Test
    void parseObj() {
    }

    @Test
    void parseObjPretty() {
    }

    @Test
    void readValue() {
    }

    @Test
    void testReadValue() {
    }

    @Test
    void testReadValue1() {
    }

    @Test
    void testReadValue2() {
    }

    @Test
    void testReadValue3() {
    }

    @Test
    void readFile() {
    }

    @Test
    void testReadFile() {
    }

    @Test
    void writeFile() {
    }

    @Test
    void test(){
        User user1 = new User();
        user1.setId(1);
        user1.setEmail("chenhaifei@163.com");
        String userJsonstr = JsonUtils.parseObj(user1);
        String userJsonPretty = JsonUtils.parseObjPretty(user1);
        log.info("userJson: {}", userJsonPretty);

        User user2 = JsonUtils.readValue(userJsonstr, User.class);
        user2.setId(2);
        user2.setEmail("tianxiaorui@126.com");

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        String userListJson = JsonUtils.parseObj(userList);
        List<User> userListBean = JsonUtils.readValue(userListJson, new TypeReference<List<User>>() {});
        if (userListBean != null) {
            userListBean.forEach(user -> {
                System.out.println(user.getId() + " : " + user.getEmail());
            });
        }
        List<User> userListBean2 = JsonUtils.readValue(userListJson, List.class, User.class);
        if (userListBean2 != null) {
            userListBean2.forEach(user -> {
                System.out.println(user.getId() + " : " + user.getEmail());
            });
        }
    }
}