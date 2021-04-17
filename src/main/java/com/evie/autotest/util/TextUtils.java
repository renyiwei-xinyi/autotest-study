package com.evie.autotest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.yaml.snakeyaml.Yaml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

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
     * 将对象内容写入json文件
     * @param path
     * @param obj
     */
    public static void jsonWriteTo(String path, Object obj){
        try {
            OutputStream outputStream= new FileOutputStream(path);
            TextUtils.objectMapper.writeValue(outputStream, obj);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void yamlWriteTo(String path, Object obj){
        try {
            OutputStream outputStream= new FileOutputStream(path);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            yaml.dump(obj, outputStreamWriter);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
