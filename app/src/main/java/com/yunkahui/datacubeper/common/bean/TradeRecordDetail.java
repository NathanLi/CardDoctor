package com.yunkahui.datacubeper.common.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.home.adapter.ExpandableTradeRecordAdapter;

public class TradeRecordDetail implements MultiItemEntity {

    private String title;
    private String time;
    private String money;
    private long timeStamp;
    private String tradeType;

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

    @Override
    public int getItemType() {
        return ExpandableTradeRecordAdapter.TYPE_LEVEL_1;
    }
}