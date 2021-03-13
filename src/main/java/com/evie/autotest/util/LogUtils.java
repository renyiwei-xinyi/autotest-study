package com.evie.autotest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {

    private static final Logger LOGGER = LogManager.getLogger(LogUtils.class);


    /**
     * 输出JSON信息
     *
     * @param obj
     * @throws Exception
     */
    public static void printJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        LOGGER.info(json);
    }


}
