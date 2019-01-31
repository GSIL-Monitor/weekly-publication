package com.tgt.weekly.publication.flow.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/20
 * @Time: 11:28
 * @Description: To change this template use File | Settings | File Templates.
 **/
public interface DataRateDao {

    /**
     * 获取流量扣减增量
     *
     * @param tableName
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countDeductIncrement(@Param("tableName") String tableName, @Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 获取指定表当前数据
     * @param tableName
     * @return
     */
    Integer countCurrent(@Param("tableName") String tableName);
}
