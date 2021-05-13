package com.evie.autotest.atom.db;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import dnl.utils.text.table.TextTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ryw@xinyi
 */
public class PrintTable {

    protected static final Logger LOGGER = LogManager.getLogger(PrintTable.class.getName());

    public void printAsTable(String tbName, List<DataMap> dataMaps) {
        for (DataMap dataMap : dataMaps) {
            printAsTable(tbName, dataMap);
        }
    }

    public void printAsTable(String tbName, DataMap dataMap) {
        if (null == dataMap) {
            return;
        }
        String[] title = {tbName, "value"};
        String[][] values = new String[dataMap.size()][2];
        int i = 0;
        for (String key : dataMap.keySet()) {
            values[i][0] = key;
            if (null == dataMap.get(key)) {
                values[i][1] = "null";
            } else {
                values[i][1] = dataMap.get(key).toString();
            }
            i++;
        }
        TextTable tt = new TextTable(title, values);
        tt.printTable();
    }

    public boolean printAsTable(String tbName, Map<String, Object> expect, List<DataMap> actual) {
        if (null == actual) {
            return false;
        }
        if (actual.size() < expect.size()) {
// 如果实际查出来的结果，比预期的结果还少，说明有预期的结果不在实际的结果中，返回失败
            LOGGER.error("actual map size is less than except map size of iaqc_ext. actual：" + actual + "\nexpect: " + expect);
            return false;
        }
        boolean result = true;
        for (DataMap act : actual) {
//ext标准红有可能存在期望结果没列进去的，但是实际查出来，所以这里要做一个判断
            if (null != expect.get(act.getStringValue("EXT_TYPE"))) {
                Object ext_type = expect.get(act.getStringValue("EXT_TYPE"));
                boolean res = printAsTable(tbName, (Map<String, String>) ext_type, act);
                if (!res) {
                    result = res;
                }
            }
        }
        return result;
    }

    public boolean printAsTable(String tbName, Map<String, String> expect, DataMap actual) {
        if (null == actual) {
            return false;
        }
        if (null == expect) {
            return false;
        }
        Collection<String> collection = expect.values();
        collection.removeIf(Objects::isNull);
        boolean checkResult = true;
        String[] title = {tbName, "actual", "expect", "result"};
        String[][] values = new String[collection.size()][4];
        int i = 0;
        for (String key : expect.keySet()) {
            if (null != expect.get(key)) {
                values[i][0] = key;
                if (null == actual.get(key)) {
                    values[i][1] = "null";
                } else {
                    values[i][1] = actual.get(key).toString();
                }
                values[i][2] = expect.get(key);
                if (values[i][1].equals(expect.get(key))) {
                    values[i][3] = "true";
                } else if ("NOT NULL".equals(expect.get(key)) && (null != values[i][1] && !"".equals(values[i][1]) && !"null".equals(values[i][1]))) {
                    values[i][3] = "true";
                } else if (expect.get(key).contains("CONTAINS_CHECK:") && values[i][1].contains(expect.get(key).substring(15))) {
                    values[i][3] = "true";
                } else if (expect.get(key).contains("JSON:")) {
                    JSONObject exp = JSONUtil.parseObj(expect.get(key).replaceAll("JSON:", ""));
                    JSONObject act = JSONUtil.parseObj(values[i][1]);
                    if (exp.size() != act.size()) {
                        values[i][3] = "FALSE";
                        checkResult = false;
                        i++;
                        continue;
                    }
                    for (String expKey : exp.keySet()) {
                        boolean match = false;
                        for (String actKey : act.keySet()) {
                            if (expKey.equals(actKey)) {
                                match = true;
                                if (!exp.get(expKey).equals(act.get(actKey))) {
                                    values[i][3] = "FALSE";
                                    checkResult = false;
                                }
                            }
                        }
                        if (!match) {
                            values[i][3] = "FALSE";
                            checkResult = false;
                        }
                    }
                    if (!"FALSE".equals(values[i][3])) {
                        values[i][3] = "true";
                    }
                } else if (!values[i][1].equals(expect.get(key))) {
                    values[i][3] = "FALSE";
                    checkResult = false;
                }
            }
            i++;
        }
        TextTable tt = new TextTable(title, values);
        tt.printTable();
        return checkResult;
    }

    public boolean printAsTable(String tbName, DataMap expect, DataMap actual) {
        if (null == actual) {
            return false;
        }
        if (null == expect) {
            return false;
        }
        Collection<Object> collection = expect.values();
        collection.removeIf(Objects::isNull);
        boolean checkResult = true;
        String[] title = {tbName, "actual", "expect", "result"};
        String[][] values = new String[collection.size()][4];
        int i = 0;
        for (String key : expect.keySet()) {
            if (null != expect.get(key)) {
                values[i][0] = key;
                if (null == actual.get(key)) {
                    values[i][1] = "null";
                } else {
                    values[i][1] = actual.get(key).toString();
                }
                values[i][2] = (String) expect.get(key);
                if (values[i][1].equals(expect.get(key))) {
                    values[i][3] = "true";
                } else if ("NOT NULL".equals(expect.get(key)) && (null != values[i][1] && !"".equals(values[i][1]) && !"null".equals(values[i][1]))) {
                    values[i][3] = "true";
                } else if (expect.getStringValue(key).contains("CONTAINS_CHECK:") && values[i][1].contains(expect.getStringValue(key).substring(15))) {
                    values[i][3] = "true";
                } else if (expect.getStringValue(key).contains("JSON:")) {
                    JSONObject exp = JSONUtil.parseObj(expect.getStringValue(key).replaceAll("JSON:", ""));
                    JSONObject act = JSONUtil.parseObj(values[i][1]);
                    if (exp.size() != act.size()) {
                        values[i][3] = "FALSE";
                        checkResult = false;
                        i++;
                        continue;
                    }
                    for (String expKey : exp.keySet()) {
                        boolean match = false;
                        for (String actKey : act.keySet()) {
                            if (expKey.equals(actKey)) {
                                match = true;
                                if (!exp.get(expKey).equals(act.get(actKey))) {
                                    values[i][3] = "FALSE";
                                    checkResult = false;
                                }
                            }
                        }
                        if (!match) {
                            values[i][3] = "FALSE";
                            checkResult = false;
                        }
                    }
                    if (!"FALSE".equals(values[i][3])) {
                        values[i][3] = "true";
                    }
                } else if (!values[i][1].equals(expect.get(key))) {
                    values[i][3] = "FALSE";
                    checkResult = false;
                }
            }
            i++;
        }
        TextTable tt = new TextTable(title, values);
        tt.printTable();
        return checkResult;
    }
}


