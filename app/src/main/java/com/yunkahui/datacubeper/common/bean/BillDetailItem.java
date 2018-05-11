package com.yunkahui.datacubeper.common.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.bill.adapter.ExpandableBillDeatailAdapter;

public class BillDetailItem implements MultiItemEntity {

    private String msg;
    private String time;
    private double money;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public int getItemType() {
        return ExpandableBillDeatailAdapter.TYPE_LEVEL_1;
    }
}
