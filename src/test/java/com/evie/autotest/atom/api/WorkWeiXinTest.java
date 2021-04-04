package com.evie.autotest.atom.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkWeiXinTest {

    @Test
    void getInstance() {
        assertNotNull(WorkWeiXin.getInstance().getAccessToken());
        System.out.println(WorkWeiXin.getInstance().getAccessToken());
        System.out.println(WorkWeiXin.getInstance().getAccessToken());
        System.out.println(WorkWeiXin.getInstance().getAccessToken());

    }
}