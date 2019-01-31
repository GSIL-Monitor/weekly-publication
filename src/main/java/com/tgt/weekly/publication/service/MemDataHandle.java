package com.tgt.weekly.publication.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.tgt.weekly.publication.constant.CommonConstant.MEM;
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
public class MemDataHandle extends PartDataHandle {

    private List<Map<Long, Integer>> mem= new ArrayList<>(3);
    private List<String> paths= new ArrayList<>(3);
    private final static String NAME ="内存使用情况";
    private int flag = -1;

    {
        mem.add(new LinkedHashMap<Long, Integer>(1440));
        mem.add(new LinkedHashMap<Long, Integer>(1440));
        mem.add(new LinkedHashMap<Long, Integer>(1440));
        paths.add(String.format("dm1%smem", SEPARATOR));
        paths.add(String.format("dm2%smem", SEPARATOR));
        paths.add(String.format("dm3%smem", SEPARATOR));
    }

    @Override
    public void handle(String line) {
        if (StringUtils.equals(MEM, line)){
            flag += 1;
            return;
        }
        super.partHandle(line, mem.get(flag / 2));
    }

    public void doWrite() {
        try{
            super.doWrite(mem, paths);
        }catch (IOException e){
            log.error("mem数据写入失败");
            e.printStackTrace();
        }
    }

    public void packageData(int maxDayNum){
        packageData(maxDayNum, paths, NAME, MEM);
    }

    public Map<String, Object> picToBase64() {
        return picToBase64(MEM);
    }
}
