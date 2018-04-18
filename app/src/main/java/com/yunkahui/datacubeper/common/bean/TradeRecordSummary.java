package com.yunkahui.datacubeper.common.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.home.adapter.ExpandableTradeRecordAdapter;

import java.util.ArrayList;
import java.util.List;

public class TradeRecordSummary extends AbstractExpandableItem<TradeRecordDetail> implements MultiItemEntity {

    private String time;
    private String message;
    private List<TradeRecordDetail> list = new ArrayList<>();

    public TradeRecordSummary() {
    }

    public TradeRecordSummary(String time, String message, List<TradeRecordDetail> list) {
        this.time = time;
        this.message = message;
        this.list = list;
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

    public List<TradeRecordDetail> getList() {
        return list;
    }

    public void setList(List<TradeRecordDetail> list) {
        this.list = list;
    }

    @Override
    public int getItemType() {
        return ExpandableTradeRecordAdapter.TYPE_LEVEL_0;
    }
}
