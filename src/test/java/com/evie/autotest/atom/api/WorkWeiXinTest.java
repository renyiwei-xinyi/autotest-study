package com.evie.autotest.atom.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkWeiXinTest {

    @Test
    void getInstance() {
        System.out.println(WorkWeiXin.getInstance().accessToken);
    }
}