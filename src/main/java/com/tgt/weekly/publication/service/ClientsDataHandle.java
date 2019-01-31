package com.tgt.weekly.publication.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.tgt.weekly.publication.constant.CommonConstant.CLIENTS;
import static com.tgt.weekly.publication.constant.CommonConstant.SEPARATOR;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/25
 * @Time: 11:17
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Slf4j
public class ClientsDataHandle extends PartDataHandle {

    private List<Map<Long, Integer>> clients= new ArrayList<>(3);
    private List<String> paths= new ArrayList<>(3);
    private final static String NAME ="客户端数目";
    private int flag = -1;

    {
        clients.add(new LinkedHashMap<Long, Integer>(1440));
        clients.add(new LinkedHashMap<Long, Integer>(1440));
        clients.add(new LinkedHashMap<Long, Integer>(1440));
        paths.add(String.format("dm1%sclients", SEPARATOR));
        paths.add(String.format("dm2%sclients", SEPARATOR));
        paths.add(String.format("dm3%sclients", SEPARATOR));
    }

    @Override
    public void handle(String line) {
        if (StringUtils.equals(CLIENTS, line)){
            flag += 1;
            return;
        }
        super.partHandle(line, clients.get(flag / 2));
    }

    public void doWrite() {
        try{
            super.doWrite(clients, paths);
        }catch (IOException e){
            log.error("clients数据写入失败");
            e.printStackTrace();
        }
    }

    public void packageData(int maxDayNum){
        packageData(maxDayNum, paths, NAME, CLIENTS);
    }

    public Map<String, Object> picToBase64() {
        return picToBase64(CLIENTS);
    }
}
