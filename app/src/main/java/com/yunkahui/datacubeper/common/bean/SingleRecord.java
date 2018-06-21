package com.yunkahui.datacubeper.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/6/21 0021
 */

public class SingleRecord implements Parcelable {

    private String time;
    private String money;
    private String status;
    private String action;
    private String remarks;
    private String fee;

    public SingleRecord() {
    }

    public SingleRecord(String time, String money, String status, String action, String remarks, String fee) {
        this.time = time;
        this.money = money;
        this.status = status;
        this.action = action;
        this.remarks = remarks;
        this.fee = fee;
    }

    protected SingleRecord(Parcel in) {
        time = in.readString();
        money = in.readString();
        status = in.readString();
        action = in.readString();
        remarks = in.readString();
        fee = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(time);
        dest.writeString(money);
        dest.writeString(status);
        dest.writeString(action);
        dest.writeString(remarks);
        dest.writeString(fee);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SingleRecord> CREATOR = new Creator<SingleRecord>() {
        @Override
        public SingleRecord createFromParcel(Parcel in) {
            return new SingleRecord(in);
        }

        @Override
        public SingleRecord[] newArray(int size) {
            return new SingleRecord[size];
        }
    };

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
