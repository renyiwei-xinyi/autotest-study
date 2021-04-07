package com.evie.autotest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonLogUtils {

    private static final Logger LOGGER = LogManager.getLogger(JsonLogUtils.class);

    public static final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 输出JSON信息
     *
     * @param obj
     * @throws Exception
     */
    public static void printJsonString(Object obj) {
        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            LOGGER.info("\n" + json);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将json对象转换为java对象
     *
     * @param json
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T jsonTo(Object json, Class<T> beanClass) {
        try {
            String s = json.getClass().getName().contains("String") ?
                    (String)json : objectMapper.writeValueAsString(json);
            return objectMapper.readValue(s, beanClass);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

    }

}
