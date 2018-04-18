package com.yunkahui.datacubeper.common.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.home.adapter.ExpandableTradeRecordAdapter;

public class TradeRecordSummary extends AbstractExpandableItem<TradeRecordDetail> implements MultiItemEntity {

    private String time;
    private String message;

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

    @Override
    public int getItemType() {
        return ExpandableTradeRecordAdapter.TYPE_LEVEL_0;
    }
}
