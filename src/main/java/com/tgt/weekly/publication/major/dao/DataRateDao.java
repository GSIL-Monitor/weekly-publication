package com.tgt.weekly.publication.major.dao;

import org.apache.ibatis.annotations.Mapper;
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
     * 获取事件告警增量
     *
     * @param tableName
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countEventIncrement(@Param("tableName") String tableName, @Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 获取运行快照增量
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countRunIncrement(@Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 获取运行快照增量
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countSimDistributionIncrement(@Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 任务结果
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countTaskIncrement(@Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 绑定关系
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countBindIncrement(@Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * sim操作记录
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countSimOperationIncrement(@Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 业务操作记录
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countBusinessIncrement(@Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 下发指令
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countDistributeIncrement(@Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 接口调用记录
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countApiIncrement(@Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 消息列表记录
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countInfoIncrement(@Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 告警记录
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countAlertIncrement(@Param("startTime") long startTime, @Param("endTime") long endTime);

    /**
     * 获取指定表当前数据
     * @param tableName
     * @return
     */
    Integer countCurrent(@Param("tableName") String tableName);
}
