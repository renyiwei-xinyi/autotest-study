package com.evie.autotest.util;

import org.yaml.snakeyaml.Yaml;

import java.io.*;

public class YamlUtils {

    public static final Yaml yaml = new Yaml();

    /**
     * 从文件读取yaml
     *
     * @param path
     * @return
     */
    public static Object readFile(String path) {
        try {
            FileInputStream inputStream = new FileInputStream(path);

            return yaml.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将对象内容写入Yaml文件
     *
     * @param path
     * @param obj
     */
    public static void writeFile(String path, Object obj) {
        try {
            OutputStream outputStream = new FileOutputStream(path);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            yaml.dump(obj, outputStreamWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
