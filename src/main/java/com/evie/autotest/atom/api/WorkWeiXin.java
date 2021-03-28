package com.evie.autotest.atom.api;

import com.evie.autotest.util.HttpUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.HttpUrl;

import java.io.IOException;

public class WorkWeiXin {
    static WorkWeiXin workWeiXin;

    public String corpId = "ww56a0dac84fe980f3";

    public String secretDemo = "_89HY84tEe5RFe4rYjW5acrEbYPZ-PZjr3X9WGgn65Y";

    public String agentId = "1000002";

    public String accessToken = "";

    public static WorkWeiXin getInstance() {
        if (workWeiXin == null) {
            workWeiXin = new WorkWeiXin();
            workWeiXin.setToken();
        }
        return workWeiXin;
    }

    private void setToken() {
        HttpUrl baseUrl = HttpUtils.getBaseUrl("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .addQueryParameter("corpid", corpId)
                .addQueryParameter("corpsecret", secretDemo)
                .build();
        try {
            String string = HttpUtils.get(baseUrl);
            accessToken = HttpUtils.objectMapper
                    .readValue(string, ObjectNode.class)
                    .get("access_token")
                    .asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
