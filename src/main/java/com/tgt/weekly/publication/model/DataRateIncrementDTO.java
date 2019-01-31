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
public class DataRateIncrementDTO implements Serializable {

    private static final long serialVersionUID = -8048204074192539306L;

    /**
     * 事件告警
     */
    private Integer eventIncrement;
    /**
     * 流量扣减
     */
    private Integer deductIncrement;
    /**
     * 运行管理
     */
    private Integer runIncrement;
    /**
     * Sim分配率
     */
    private Integer simDistributionIncrement;
    /**
     * 任务结果
     */
    private Integer taskIncrement;
    /**
     * 绑定解绑
     */
    private Integer bindIncrement;
    /**
     * sim操作记录
     */
    private Integer simOperationIncrement;
    /**
     * 业务操作记录
     */
    private Integer businessIncrement;
    /**
     * 下发指令
     */
    private Integer distributeIncrement;
    /**
     * 接口调用
     */
    private Integer apiIncrement;
    /**
     * 消息列表
     */
    private Integer infoIncrement;
    /**
     * 告警记录
     */
    private Integer alertIncrement;
}
