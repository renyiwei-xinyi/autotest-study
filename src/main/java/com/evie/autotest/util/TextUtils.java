package com.evie.autotest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.List;
import java.util.Map;

public class TextUtils {

    private static final Logger LOGGER = LogManager.getLogger(TextUtils.class);

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static final Yaml yaml = new Yaml();


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
     * 从文件读取json
     *
     * @param path
     * @return
     */
    public static Object jsonReadFor(String path) {
        try {
            FileInputStream inputStream = new FileInputStream(path);

            return objectMapper.readValue(inputStream, Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 从文件读取yaml
     *
     * @param path
     * @return
     */
    public static Object yamlReadFor(String path) {
        try {
            FileInputStream inputStream = new FileInputStream(path);

            return yaml.loadAll(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象内容写入json文件
     *
     * @param path
     * @param obj
     */
    public static void jsonWriteTo(String path, Object obj) {
        try {
            OutputStream outputStream = new FileOutputStream(path);
            objectMapper.writeValue(outputStream, obj);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将对象内容写入Yaml文件
     *
     * @param path
     * @param obj
     */
    public static void yamlWriteTo(String path, Object obj) {
        try {
            OutputStream outputStream = new FileOutputStream(path);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            yaml.dump(obj, outputStreamWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map parseToMap(String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T parseToBean(String jsonStr, Class<T> valueType) {
        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map> parseToArray(String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, new TypeReference<List<Map>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T parse(String jsonStr, TypeReference<T> beanClass) {
        try {
            return objectMapper.readValue(jsonStr, beanClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
