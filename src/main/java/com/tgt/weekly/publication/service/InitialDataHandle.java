package com.tgt.weekly.publication.service;

import com.tgt.weekly.publication.model.DataModel;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.tgt.weekly.publication.constant.CommonConstant.INITIAL;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/19
 * @Time: 13:34
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Slf4j
public class InitialDataHandle extends DataHandle {

    private Map<Long,int[]> initial= new LinkedHashMap<>(10000);

    private static final String NAME = "设备初始化数";

    @Override
    public void handle(String line) {
        defaultHandle(line, initial);
    }

    public Map<String, Object> convertData(int day) {
        List<DataModel> dataModels = concludeData(day, INITIAL);
        return convertData(dataModels, INITIAL);
    }

    public void doWrite(){
        try{
            super.doWrite(INITIAL, initial);
        }catch (IOException e){
            log.error("初始化数据写入失败");
            e.printStackTrace();
        }
    }

    public void packageData(int maxDayNum){
        packageSecondAndMinuteData(packageHourData(maxDayNum, INITIAL), INITIAL, NAME);
    }

    public Map<String, Object> picToBase64() {
        return picToBase64(INITIAL);
    }

    public Map<Long, int[]> getInitial() {
        return initial;
    }
}
