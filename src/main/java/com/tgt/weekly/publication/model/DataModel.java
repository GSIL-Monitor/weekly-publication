package com.tgt.weekly.publication.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/21
 * @Time: 11:41
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Getter
public class DataModel implements Serializable {

    private static final long serialVersionUID = -4609940897408418396L;

    private int total;

    private int maxMinute;

    private int maxSecond;

    private int avgTime;

    private int maxTime;

    private int minTime;

    private int throughput;

    private int fiveNum;

    private int tenNum;

    private int fifteenNum;

    private int twentyNum;

    private int twentyFiveNum;

    private int thirtyNum;

    private int overNum;

    public DataModel setTotal(int total) {
        this.total = total;
        return this;
    }

    public DataModel setMaxMinute(Integer maxMinute) {
        this.maxMinute = maxMinute;
        return this;
    }

    public DataModel setMaxSecond(Integer maxSecond) {
        this.maxSecond = maxSecond;
        return this;
    }

    public DataModel setAvgTime(Integer avgTime) {
        this.avgTime = avgTime;
        return this;
    }

    public DataModel setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    public DataModel setMinTime(Integer minTime) {
        this.minTime = minTime;
        return this;
    }

    public DataModel setThroughput(Integer throughput) {
        this.throughput = throughput;
        return this;
    }

    public DataModel setFiveNum(Integer fiveNum) {
        this.fiveNum = fiveNum;
        return this;
    }

    public DataModel setTenNum(Integer tenNum) {
        this.tenNum = tenNum;
        return this;
    }

    public DataModel setFifteenNum(Integer fifteenNum) {
        this.fifteenNum = fifteenNum;
        return this;
    }

    public DataModel setTwentyNum(Integer twentyNum) {
        this.twentyNum = twentyNum;
        return this;
    }

    public DataModel setTwentyFiveNum(Integer twentyFiveNum) {
        this.twentyFiveNum = twentyFiveNum;
        return this;
    }

    public DataModel setThirtyNum(Integer thirtyNum) {
        this.thirtyNum = thirtyNum;
        return this;
    }

    public DataModel setOverNum(Integer overNum) {
        this.overNum = overNum;
        return this;
    }
}
