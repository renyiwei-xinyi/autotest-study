package com.evie.autotest.atom.db;

import cn.hutool.core.map.CaseInsensitiveMap;
import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author ryw@xinyi
 * 不区分大小写的key
 */
public class DataMap extends CaseInsensitiveMap<String, Object> {

    private static final long serialVersionUID = 9164967756576939731L;

    public String getStringValue(String key) {
        String value = null;
        if (this.containsKey(key)) {
            Object obj = this.get(key);
            if (null == obj) {
                return null;
            } else if (obj instanceof BigDecimal) {
                value = obj.toString();
            } else {
                value = String.valueOf(obj);
            }
        }
        return value;
    }

    public int getIntValue(String key) {
        String stringValue = this.getStringValue(key);
        int value = Integer.parseInt(stringValue);
        return value;
    }

    public long getLongValue(String key) {
        String stringValue = this.getStringValue(key);
        long value = Long.parseLong(stringValue);
        return value;
    }

    public static String convertKey(String key) {
        return StrUtil.isBlank(key) ? null : key.toUpperCase();
    }

    public Object get(String key) {
        return super.get(key);
    }
}



