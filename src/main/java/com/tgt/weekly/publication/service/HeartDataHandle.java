package com.tgt.weekly.publication.service;

import com.alibaba.fastjson.JSON;
import com.tgt.weekly.publication.model.DataModel;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;

import static com.tgt.weekly.publication.constant.CommonConstant.HEART;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/19
 * @Time: 13:21
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Slf4j
public class HeartDataHandle extends DataHandle {

    private Map<Long,int[]> heartbeat= new LinkedHashMap<>(8000000);

    private static final String NAME = "心跳数";

    @Override
    public void handle(String line) {
       defaultHandle(line, heartbeat);
    }

    public void doWrite() {
        try{
            super.doWrite(HEART, heartbeat);
        }catch (IOException e){
            log.error("心跳数据写入失败");
            e.printStackTrace();
        }
    }

    public Map<String, Object> convertData(int day) {
        List<DataModel> dataModels = concludeData(day, HEART);
        return convertData(dataModels, HEART);
    }

    public void packageData(int maxDayNum) {
        packageSecondAndMinuteData(packageHourData(maxDayNum, HEART), HEART, NAME);
    }

    public Map<String, Object> picToBase64() {
        return picToBase64(HEART);
    }

    public Map<Long, int[]> getHeartbeat() {
        return heartbeat;
    }

}
