package com.yunkahui.datacubeper.common.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.bill.adapter.ExpandableBillDeatailAdapter;

public class BillDetailSummary extends AbstractExpandableItem<BillDetailItem> implements MultiItemEntity {

    private String mess;
    private int year;
    private String startDate;
    private String endDate;

    public BillDetailSummary() {
    }

    public BillDetailSummary(String mess, int year, String startDate, String endDate) {
        this.mess = mess;
        this.year = year;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public int getItemType() {
        return ExpandableBillDeatailAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}

