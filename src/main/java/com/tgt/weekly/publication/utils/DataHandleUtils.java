package com.tgt.weekly.publication.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.tgt.weekly.publication.constant.CommonConstant.FILE_PATH;
import static com.tgt.weekly.publication.constant.CommonConstant.SEPARATOR;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/25
 * @Time: 15:27
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Slf4j
public class DataHandleUtils {

    protected static final int TEN = 10;
    protected static final int SIXTY = 60;
    protected static final int SIX_HUNDRED = 600;
    protected static final int TWENTY_FOUR = 24;
    protected static final int MAX_MINUTE = 1440;

    public File[] getFiles(String type){
        File file=new File(String.format("%s%s%s", FILE_PATH, SEPARATOR, type));
        if (!file.exists()){
            return null;
        }
        File[] files = file.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.lastModified() - o2.lastModified() == 0 ? o1.getName().compareTo(o2.getName()) : (int) (o1.lastModified() - o2.lastModified());
            }
        });
        return files;
    }

    public Map getSecondsMap(){
        return getMap(SIX_HUNDRED, TEN);
    }

    public Map getMaxMinuteMap(){
        return getMap(MAX_MINUTE, TWENTY_FOUR);
    }

    public Map getMinutesMap(){
        return getMap(SIXTY);
    }

    public Map getHourMap(){
        return getMap(TWENTY_FOUR);
    }

    private Map getMap(int size) {
        Map map = new LinkedHashMap(size);
        for (int i = 0; i < size; i++){
            map.put(String.format("%02d", i), 0);
        }
        return map;
    }

    private Map getMap(int size, int minute) {
        Map map = new LinkedHashMap<>(size);
        for (int i = 0; i < minute; i++){
            for (int j = 0; j < SIXTY; j++){
                map.put(String.format("%02d:%02d", i, j), 0);
            }
        }
        return map;
    }
}
