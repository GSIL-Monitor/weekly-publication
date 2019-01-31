package com.tgt.weekly.publication.major.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/20
 * @Time: 11:28
 * @Description: To change this template use File | Settings | File Templates.
 **/
public interface BusinessOverviewDao {

    /**
     * 获取天活跃终端
     *
     * @param tableName
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countActiveTerminal(@Param("tableName") String tableName, @Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 获取天活跃卡
     *
     * @param tableName
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countActiveSim(@Param("tableName") String tableName, @Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 获取天活跃卡
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countOnlinePeak(@Param("startTime") long startTime, @Param("endTime")long endTime);
}
