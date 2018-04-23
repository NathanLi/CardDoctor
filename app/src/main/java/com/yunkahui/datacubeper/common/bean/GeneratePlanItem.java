package com.yunkahui.datacubeper.common.bean;

public class GeneratePlanItem {

    private int type;
    private long timeStamp;
    private int money;
    private String mccType;
    private int group;
    private int section;

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getMccType() {
        return mccType;
    }

    public void setMccType(String mccType) {
        this.mccType = mccType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
