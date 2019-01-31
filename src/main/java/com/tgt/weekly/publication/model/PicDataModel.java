package com.tgt.weekly.publication.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/24
 * @Time: 13:41
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Getter
public class PicDataModel implements Serializable {

    private static final long serialVersionUID = 1398499960668627677L;

    public PicDataModel() {
    }

    public PicDataModel(String time, int frequency) {
        this(time, frequency, null);
    }

    public PicDataModel(String time, int frequency, List<PicDataModel> childData) {
        this.time = time;
        this.frequency = frequency;
        this.childData = childData;
    }

    private String time;

    private int frequency;

    private List<PicDataModel> childData;

    public PicDataModel setTime(String time) {
        this.time = time;
        return this;
    }

    public PicDataModel setFrequency(int frequency) {
        this.frequency = frequency;
        return this;
    }

    public PicDataModel setChildData(List<PicDataModel> childData) {
        this.childData = childData;
        return this;
    }
}
