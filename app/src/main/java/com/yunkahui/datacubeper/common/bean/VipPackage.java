package com.yunkahui.datacubeper.common.bean;

/**
 * VIP套餐
 */

public class VipPackage {

    String commSetting;
    int vpId;
    int commLimitLevel;
    String openState;
    int agentCommLimitLevel;
    long updateTime;
    String type;
    long createTime;
    double price;
    String name;
    String agentCommSetting;
    String alias;
    String shortDesc;
    String orgNumber;
    String agentCommType;
    String desc;

    public String getCommSetting() {
        return commSetting == null ? "" : commSetting;
    }

    public void setCommSetting(String commSetting) {
        this.commSetting = commSetting;
    }

    public int getVpId() {
        return vpId;
    }

    public void setVpId(int vpId) {
        this.vpId = vpId;
    }

    public int getCommLimitLevel() {
        return commLimitLevel;
    }

    public void setCommLimitLevel(int commLimitLevel) {
        this.commLimitLevel = commLimitLevel;
    }

    public String getOpenState() {
        return openState == null ? "" : openState;
    }

    public void setOpenState(String openState) {
        this.openState = openState;
    }

    public int getAgentCommLimitLevel() {
        return agentCommLimitLevel;
    }

    public void setAgentCommLimitLevel(int agentCommLimitLevel) {
        this.agentCommLimitLevel = agentCommLimitLevel;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgentCommSetting() {
        return agentCommSetting == null ? "" : agentCommSetting;
    }

    public void setAgentCommSetting(String agentCommSetting) {
        this.agentCommSetting = agentCommSetting;
    }

    public String getAlias() {
        return alias == null ? "" : alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getShortDesc() {
        return shortDesc == null ? "" : shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getOrgNumber() {
        return orgNumber == null ? "" : orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getAgentCommType() {
        return agentCommType == null ? "" : agentCommType;
    }

    public void setAgentCommType(String agentCommType) {
        this.agentCommType = agentCommType;
    }

    public String getDesc() {
        return desc == null ? "" : desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "VipPackage{" +
                "commSetting='" + commSetting + '\'' +
                ", vpId=" + vpId +
                ", commLimitLevel=" + commLimitLevel +
                ", openState='" + openState + '\'' +
                ", agentCommLimitLevel=" + agentCommLimitLevel +
                ", updateTime=" + updateTime +
                ", type='" + type + '\'' +
                ", createTime=" + createTime +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", agentCommSetting='" + agentCommSetting + '\'' +
                ", alias='" + alias + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", orgNumber='" + orgNumber + '\'' +
                ", agentCommType='" + agentCommType + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
