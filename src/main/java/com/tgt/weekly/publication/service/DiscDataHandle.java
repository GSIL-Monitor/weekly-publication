package com.tgt.weekly.publication.service;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.tgt.weekly.publication.constant.CommonConstant.DISC;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/25
 * @Time: 15:07
 * @Description: To change this template use File | Settings | File Templates.
 **/
public class DiscDataHandle extends PartDataHandle {

    private List<Map<Long, Integer>> disc= new ArrayList<>(3);

    private int flag = -1;

    {
        disc.add(new LinkedHashMap<Long, Integer>(1440));
        disc.add(new LinkedHashMap<Long, Integer>(1440));
        disc.add(new LinkedHashMap<Long, Integer>(1440));
    }

    @Override
    public void handle(String line) {
        if (StringUtils.equals(DISC, line)){
            flag += 1;
            return;
        }
        super.partHandle(line, disc.get(flag / 2));
    }

}
