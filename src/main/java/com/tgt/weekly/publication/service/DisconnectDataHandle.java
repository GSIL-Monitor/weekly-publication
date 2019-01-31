package com.tgt.weekly.publication.service;

import com.tgt.weekly.publication.model.DataModel;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.tgt.weekly.publication.constant.CommonConstant.DISCONNECT;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/19
 * @Time: 13:33
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Slf4j
public class DisconnectDataHandle extends DataHandle {

    private Map<Long,int[]> disconnect= new LinkedHashMap<>(50000);

    private static final String NAME = "断连数";

    @Override
    public void handle(String line) {
        defaultHandle(line, disconnect);
    }

    public void doWrite(){
        try{
            super.doWrite(DISCONNECT, disconnect);
        }catch (IOException e){
            log.error("断连数据写入失败");
            e.printStackTrace();
        }
    }

    public Map<String, Object> convertData(int day) {
        List<DataModel> dataModels = concludeData(day, DISCONNECT);
        return convertData(dataModels, DISCONNECT);
    }

    public void packageData(int maxDayNum){
        packageSecondAndMinuteData(packageHourData(maxDayNum, DISCONNECT), DISCONNECT, NAME);
    }

    public Map<String, Object> picToBase64() {
        return picToBase64(DISCONNECT);
    }

    public Map<Long, int[]> getDisconnect() {
        return disconnect;
    }

}
