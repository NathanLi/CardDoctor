package com.yunkahui.datacubeper.common.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.home.adapter.ExpandableTradeRecordAdapter;

public class TradeRecordSummary extends AbstractExpandableItem<TradeRecordDetail> implements MultiItemEntity {

    private String time;
    private String message;
    private String year;
    private String month;
    private String back;
    private String pay;

    public TradeRecordSummary() {
    }

    public TradeRecordSummary(String time, String message) {
        this.time = time;
        this.message = message;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    @Override
    public int getItemType() {
        return ExpandableTradeRecordAdapter.TYPE_LEVEL_0;
    }
}
