package com.evie.autotest.util;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssertUtil {

    private static final Logger LOGGER = LogManager.getLogger(AssertUtil.class);


    /*
     * 判断送入的字符串是否为数值（包括正数，负数，含小数位的数）
     */
    public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 校验字符串开头不为空
     * @param string
     * @return
     */
    public static boolean isBeginNotBlank(String string) {
        return StringUtils.isNotBlank(string.substring(0, 1));
    }


    /**
     * 校验字符串结尾不为空
     * @param string
     * @return
     */
    public static boolean isEndNotBlank(String string) {
        //反转字符
        String s = new StringBuilder(string).reverse().toString();
        return StringUtils.isNotBlank(s.substring(0, 1));
    }



}
