package com.tgt.weekly.publication.utils;

import java.util.*;

import static com.tgt.weekly.publication.constant.CommonConstant.FILE_PATH;
import static com.tgt.weekly.publication.constant.CommonConstant.SEPARATOR;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/24
 * @Time: 15:01
 * @Description: To change this template use File | Settings | File Templates.
 **/
public class HighChartsUtils {

    protected static final String DAY_JSON = "day.json";
    protected static final String DAY_FTL = "day.ftl";
    protected static final String HOUR_JSON = "hour.json";
    protected static final String HOUR_FTL = "hour.ftl";
    protected static final String MINUTE_JSON = "minute.json";
    protected static final String MINUTE_FTL = "minute.ftl";
    protected static final String COMPARISON_JSON = "comparison.json";
    protected static final String COMPARISON_FTL = "comparison.ftl";

    public static void createDayJson(Map<String, Integer> map, String type, String name){
        createJSON(map, type, name, DAY_FTL, DAY_JSON);
    }


    public static void createHourJson(Map<String, Integer> map, String type, String name){
        createJSON(map, type, name, HOUR_FTL, HOUR_JSON);

    }

    public static void createMinuteJson(Map<String, Integer> map, String type, String name){
        createJSON(map, type, name, MINUTE_FTL, MINUTE_JSON);
    }

    public static void createComparisonJson(String type, String name, List<ArrayList<Integer>> dataList, Set<String> time){
        Map root = new HashMap(5);
        root.put("name", name);
        root.put("time", time);
        root.put("dm1", dataList.get(0));
        root.put("dm2", dataList.get(1));
        root.put("dm3", dataList.get(2));
        new FreemarkerUtil().createWordTemplate(root, COMPARISON_FTL, String.format("%s%s%s", FILE_PATH, SEPARATOR, type), COMPARISON_JSON);
    }

    private static void createJSON(Map<String, Integer> map, String type, String name, String minuteFtl, String json) {
        Map root = new HashMap(3);
        root.put("name", name);
        root.put("time", map.keySet());
        root.put("data", new ArrayList<>(map.values()));
        new FreemarkerUtil().createWordTemplate(root, minuteFtl, String.format("%s%s%s", FILE_PATH, SEPARATOR, type), json);
    }
}
