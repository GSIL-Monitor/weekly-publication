package com.tgt.weekly.publication.service;

import com.tgt.weekly.publication.properties.DMProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.tgt.weekly.publication.utils.MapBeanUtils.beanToMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/21
 * @Time: 9:47
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Slf4j
@Service
public class ReportServiceImpl {

    @Autowired
    DMProperties dmProperties;

    @Autowired
    BusinessOverview businessOverview;

    private Map<String, Object> map = new HashMap<>(700);
    private int maxDayNum = 0;

    /**
     * todo? 有更好方案？
     */
    private final HeartDataHandle heartDataHandle = new HeartDataHandle();
    private final DeductDataHandle deductDataHandle = new DeductDataHandle();
    private final CertifyDataHandle certifyDataHandle = new CertifyDataHandle();
    private final InitialDataHandle initialDataHandle = new InitialDataHandle();
    private final DisconnectDataHandle disconnectDataHandle = new DisconnectDataHandle();
    private final WifiDataHandle wifiDataHandle = new WifiDataHandle();
    private final CpuDataHandle cpuDataHandle = new CpuDataHandle();
    private final MemDataHandle memDataHandle = new MemDataHandle();
    private final ClientsDataHandle clientsDataHandle = new ClientsDataHandle();

    /**
     * 得到总的模板数据
     *
     * @return
     */
    public Map<String, Object> handleCombineData(){
        getDetails();
        map.putAll(beanToMap(businessOverview.getOverview()));
        map.putAll(beanToMap(businessOverview.getIncrementDataRate()));
        map.putAll(beanToMap(businessOverview.getCurrentDataRate()));
        getMaxDay();
        packageData();
        generatePictures();
        getBase64Pic();
        return map;
    }

    /**
     * 得到详情
     *
     * @return
     */
    private Map<String, Object> getDetails(){
        map.putAll(heartDataHandle.convertData(dmProperties.getDay()));
        map.putAll(deductDataHandle.convertData(dmProperties.getDay()));
        map.putAll(certifyDataHandle.convertData(dmProperties.getDay()));
        map.putAll(wifiDataHandle.convertData(dmProperties.getDay()));
        map.putAll(initialDataHandle.convertData(dmProperties.getDay()));
        map.putAll(disconnectDataHandle.convertData(dmProperties.getDay()));
        return map;
    }

    private Map<String, Object> getBase64Pic(){
        map.putAll(heartDataHandle.picToBase64());
        map.putAll(deductDataHandle.picToBase64());
        map.putAll(certifyDataHandle.picToBase64());
        map.putAll(wifiDataHandle.picToBase64());
        map.putAll(initialDataHandle.picToBase64());
        map.putAll(disconnectDataHandle.picToBase64());
        map.putAll(cpuDataHandle.picToBase64());
        map.putAll(memDataHandle.picToBase64());
        map.putAll(clientsDataHandle.picToBase64());
        return map;
    }

    private void packageData(){
        heartDataHandle.packageData(maxDayNum);
        deductDataHandle.packageData(maxDayNum);
        certifyDataHandle.packageData(maxDayNum);
        wifiDataHandle.packageData(maxDayNum);
        initialDataHandle.packageData(maxDayNum);
        disconnectDataHandle.packageData(maxDayNum);
        cpuDataHandle.packageData(maxDayNum);
        clientsDataHandle.packageData(maxDayNum);
        memDataHandle.packageData(maxDayNum);
    }

    private void generatePictures(){
        try {
            Runtime run = Runtime.getRuntime();
            Process p = run.exec("sh /home/domain/weekly-new/pic.sh");
//            Process p = run.exec("cmd.exe /c F:\\work\\weekly-publication\\src\\main\\resources\\pic.bat");
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader( in ));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                log.info(lineStr);
            }
            inBr.close();
            in .close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getMaxDay(){
        if (dmProperties.getDay() <= 1){
            return;
        }
        int get = getTotal(0);
        for (int i = 1; i < dmProperties.getDay(); i++){
            if (get < getTotal(i)){
                get = getTotal(i);
                maxDayNum = i;
            }
        }
    }

    private int getTotal(int i){
        return getHeartbeatTotal(i)
                + getDeductTotal(i)
                + getCertifyTotal(i)
                + getInitialTotal(i)
                + getDisconnTotal(i)
                + getWifiTotal(i);
    }

    private int getHeartbeatTotal(int i){
        return (int) map.get(String.format("heartbeatTotal%d", i));
    }

    private int getDeductTotal(int i){
        return (int) map.get(String.format("deductTotal%d", i));
    }

    private int getCertifyTotal(int i){
        return (int) map.get(String.format("certifyTotal%d", i));
    }

    private int getInitialTotal(int i){
        return (int) map.get(String.format("initialTotal%d", i));
    }

    private int getDisconnTotal(int i){
        return (int) map.get(String.format("disconnTotal%d", i));
    }

    private int getWifiTotal(int i){
        return (int) map.get(String.format("wifiTotal%d", i));
    }

}
