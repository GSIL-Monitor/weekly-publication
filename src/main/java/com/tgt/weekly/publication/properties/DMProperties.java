package com.tgt.weekly.publication.properties;

import com.tgt.weekly.publication.entity.DM;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/18
 * @Time: 15:15
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Component
@ConfigurationProperties(prefix = "dm.server")
public class DMProperties {

    private List<DM> dmList;

    private long start;

    private long end;

    private int day;

    public List<DM> getDmList() {
        return dmList;
    }

    public long getStart() {
        return start;
    }

    public DMProperties setStart(long start) {
        this.start = start;
        return this;
    }

    public long getEnd() {
        return end;
    }

    public DMProperties setEnd(long end) {
        this.end = end;
        return this;
    }

    public DMProperties setDmList(List<DM> dmList) {
        this.dmList = dmList;
        return this;
    }

    public int getDay() {
        return day;
    }

    public DMProperties setDay(int day) {
        this.day = day;
        return this;
    }
}
