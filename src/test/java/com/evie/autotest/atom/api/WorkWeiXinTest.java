package com.evie.autotest.atom.api;

import com.evie.autotest.provider.YamlFileSource;
import com.evie.autotest.util.HttpUtils;
import okhttp3.HttpUrl;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorkWeiXinTest {

    @Test
    void getInstance() {
        assertNotNull(WorkWeiXin.getInstance().getAccessToken());
        assertEquals(WorkWeiXin.getInstance().getAccessToken(), WorkWeiXin.getInstance().getAccessToken());
    }

    @Test
    void login_test(){

    }


    @YamlFileSource(files = "/atom/api/case/send_message_test.yaml")
    void send_message_test(Map template){
        Object testDate = template.get("testDate");
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
        HttpUrl httpUrl = HttpUtils.getBaseUrl(url)
                .addQueryParameter("access_token", WorkWeiXin.getInstance().getAccessToken())
                .build();

        HttpUtils.post(httpUrl, testDate);
    }
}