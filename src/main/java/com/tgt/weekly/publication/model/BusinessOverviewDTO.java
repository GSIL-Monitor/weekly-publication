package com.tgt.weekly.publication.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/20
 * @Time: 11:12
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Data
@Builder
public class BusinessOverviewDTO implements Serializable {

    private static final long serialVersionUID = -4703449192485445047L;

    /**
     * 活跃终端
     */
    private Integer activeTerminal;
    /**
     * 活跃卡
     */
    private Integer activeSim;
    /**
     * 在线峰值
     */
    private Integer onlinePeak;

}
