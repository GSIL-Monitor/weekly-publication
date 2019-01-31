package com.tgt.weekly.publication.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/20
 * @Time: 16:10
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Data
@Builder
public class DataRateCurrentDTO implements Serializable {

    private static final long serialVersionUID = -8048204074192539306L;

    /**
     * 事件告警
     */
    private Integer eventCurrent;
    /**
     * 流量扣减
     */
    private Integer deductCurrent;
    /**
     * 运行管理
     */
    private Integer runCurrent;
    /**
     * Sim分配率
     */
    private Integer simDistributionCurrent;
    /**
     * 任务结果
     */
    private Integer taskCurrent;
    /**
     * 绑定解绑
     */
    private Integer bindCurrent;
    /**
     * sim操作记录
     */
    private Integer simOperationCurrent;
    /**
     * 业务操作记录
     */
    private Integer businessCurrent;
    /**
     * 下发指令
     */
    private Integer distributeCurrent;
    /**
     * 接口调用
     */
    private Integer apiCurrent;
    /**
     * 消息列表
     */
    private Integer infoCurrent;
    /**
     * 告警记录
     */
    private Integer alertCurrent;
}
