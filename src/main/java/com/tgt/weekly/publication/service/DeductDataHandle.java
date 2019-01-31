package com.tgt.weekly.publication.service;

import com.tgt.weekly.publication.model.DataModel;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.tgt.weekly.publication.constant.CommonConstant.DEDUCT;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/19
 * @Time: 13:32
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Slf4j
public class DeductDataHandle extends DataHandle {

    private Map<Long,int[]> deduct= new LinkedHashMap<>(90000);

    private static final String NAME = "流量扣减数";

    @Override
    public void handle(String line) {
       defaultHandle(line, deduct);
    }

    public Map<String, Object> convertData(int day) {
        List<DataModel> dataModels = concludeData(day, DEDUCT);
        return convertData(dataModels, DEDUCT);
    }

    public void doWrite() {
        try{
            super.doWrite(DEDUCT, deduct);
        }catch (IOException e){
            log.error("流量扣减数据写入失败");
            e.printStackTrace();
        }
    }

    public void packageData(int maxDayNum){
        packageSecondAndMinuteData(packageHourData(maxDayNum, DEDUCT), DEDUCT, NAME);
    }

    public Map<String, Object> picToBase64() {
        return picToBase64(DEDUCT);
    }

    public Map<Long, int[]> getDeduct() {
        return deduct;
    }
}
