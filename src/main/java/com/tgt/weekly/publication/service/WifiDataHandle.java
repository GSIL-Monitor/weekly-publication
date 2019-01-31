package com.tgt.weekly.publication.service;

import com.tgt.weekly.publication.model.DataModel;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.tgt.weekly.publication.constant.CommonConstant.WIFI;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/19
 * @Time: 13:35
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Slf4j
public class WifiDataHandle extends DataHandle {

    private Map<Long,int[]> wifi= new LinkedHashMap<>(100);

    private static final String NAME = "wifi上报数";

    @Override
    public void handle(String line) {
        defaultHandle(line, wifi);
    }

    public void doWrite(){
        try{
            super.doWrite(WIFI, wifi);
        }catch (IOException e){
            log.error("wifi数据写入失败");
            e.printStackTrace();
        }
    }

    public Map<String, Object> convertData(int day) {
        List<DataModel> dataModels = concludeData(day, WIFI);
        return convertData(dataModels, WIFI);
    }

    public void packageData(int maxDayNum){
        packageSecondAndMinuteData(packageHourData(maxDayNum, WIFI), WIFI, NAME);
    }

    public Map<String, Object> picToBase64() {
        return picToBase64(WIFI);
    }

    public Map<Long, int[]> getWifi() {
        return wifi;
    }
}
