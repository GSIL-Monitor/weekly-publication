package com.tgt.weekly.publication.service;

import com.tgt.weekly.publication.model.DataModel;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.tgt.weekly.publication.constant.CommonConstant.CERTIFY;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/19
 * @Time: 13:36
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Slf4j
public class CertifyDataHandle extends DataHandle {

    private Map<Long,int[]> certify= new LinkedHashMap<>(20000);

    private static final String NAME = "设备认证数";

    @Override
    public void handle(String line) {
        defaultHandle(line, certify);
    }

    public void doWrite(){
        try{
            super.doWrite(CERTIFY, certify);
        }catch (IOException e){
            log.error("认证数据写入失败");
            e.printStackTrace();
        }
    }

    public Map<String, Object> convertData(int day) {
        List<DataModel> dataModels = concludeData(day, CERTIFY);
        return convertData(dataModels, CERTIFY);
    }

    public void packageData(int maxDayNum){
        packageSecondAndMinuteData(packageHourData(maxDayNum, CERTIFY), CERTIFY, NAME);
    }

    public Map<String, Object> picToBase64() {
        return picToBase64(CERTIFY);
    }

    public Map<Long, int[]> getCertify() {
        return certify;
    }
}
