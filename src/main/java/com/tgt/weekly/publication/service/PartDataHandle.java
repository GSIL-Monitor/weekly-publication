package com.tgt.weekly.publication.service;


import java.io.*;
import java.util.*;

import static com.tgt.weekly.publication.constant.CommonConstant.FILE_PATH;
import static com.tgt.weekly.publication.constant.CommonConstant.SEPARATOR;
import static com.tgt.weekly.publication.utils.Base64Utils.getImageBase;
import static com.tgt.weekly.publication.utils.DateUtils.getHourAndMinute;
import static com.tgt.weekly.publication.utils.HighChartsUtils.createComparisonJson;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/25
 * @Time: 14:56
 * @Description: To change this template use File | Settings | File Templates.
 **/
public abstract class PartDataHandle extends DataHandle {

    protected static final String COMPARISON = "comparison.png";

    /**
     * 处理cpu、客户端数等信息
     *
     * @param line
     * @param map
     */
    protected void partHandle(String line, Map<Long, Integer> map){
        String[] strData = line.split(" ");
        if (strData.length != 2){
            return;
        }
        long key = Long.parseLong(strData[0]);
        if (map.containsKey(key)){
            map.put(key, Integer.parseInt(strData[1]) + map.get(key));
            return;
        }
        map.put(key, Integer.parseInt(strData[1]));
    }

    public void doWrite(List<Map<Long, Integer>> list, List<String> paths) throws IOException {
            super.doWrite(paths.get(0), list.get(0), true);
            super.doWrite(paths.get(1), list.get(1), true);
            super.doWrite(paths.get(2), list.get(2), true);
    }

    public void packageData(int maxDayNum, List<String> paths, String name, String picPath) {
        createComparisonJson(
                picPath,
                name,
                Arrays.asList(new ArrayList<>(packageMinuteData(maxDayNum, paths.get(0)).values()),
                        new ArrayList<>(packageMinuteData(maxDayNum, paths.get(1)).values()) ,
                        new ArrayList<>(packageMinuteData(maxDayNum, paths.get(2)).values())),
                dataHandleUtils.getMaxMinuteMap().keySet()
        );
    }

    private Map<String, Integer> packageMinuteData(int maxDayNum, String type){
        File[] files = dataHandleUtils.getFiles(type);
        Map<String, Integer> minutes = dataHandleUtils.getMaxMinuteMap();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(files[maxDayNum])));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strData = line.split(" ");
                minutes.put(getHourAndMinute(Long.parseLong(strData[0])), Integer.parseInt(strData[1]));
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return minutes;
    }

    @Override
    public Map<String, Object> picToBase64(String type){
        Map<String, Object> picMap = new HashMap<>(1);
        picMap.put(String.format("%s%s", type, "img1"), getImageBase(String.format("%s%s%s%s%s", FILE_PATH, SEPARATOR, type, SEPARATOR, COMPARISON)));
        return picMap;
    }
}
