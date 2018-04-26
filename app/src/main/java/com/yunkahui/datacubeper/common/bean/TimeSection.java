package com.yunkahui.datacubeper.common.bean;

/**
 * @author WYF on 2018/4/25/025.
 */
public class TimeSection {

    private long startTimeStamp;
    private long endTimeStamp;

    public TimeSection() {
    }

    public TimeSection(long startTimeStamp, long endTimeStamp) {
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
    }

    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }
}
