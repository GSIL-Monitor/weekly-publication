package com.tgt.weekly.publication.service;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/19
 * @Time: 13:18
 * @Description: To change this template use File | Settings | File Templates.
 **/
public class DataContext {

    private DataHandle dataHandle;

    public DataContext() {
    }

    public DataContext(DataHandle dataHandle) {
        this.dataHandle = dataHandle;
    }

    public void handle(String line){
        dataHandle.handle(line);
    }

    public DataContext setDataHandle(DataHandle dataHandle) {
        this.dataHandle = dataHandle;
        return this;
    }

    public DataHandle getDataHandle() {
        return dataHandle;
    }

    public void handle(int day){
    }
}
