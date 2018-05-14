package com.yunkahui.datacubeper.common.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.home.adapter.ExpandableTradeRecordAdapter;

public class TradeRecordDetail implements MultiItemEntity {

    private String title;
    private String time;
    private String money;
    private long timeStamp;
    private String orderStatus;
    private String tradeType;
    private String result;
    private String remark;
    private int type;   //支付or收入

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public int getItemType() {
        return ExpandableTradeRecordAdapter.TYPE_LEVEL_1;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResult() {
        return result == null ? "" : result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TradeRecordDetail{" +
                "title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", money='" + money + '\'' +
                ", timeStamp=" + timeStamp +
                ", orderStatus='" + orderStatus + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", result='" + result + '\'' +
                ", remark='" + remark + '\'' +
                ", type=" + type +
                '}';
    }
}
