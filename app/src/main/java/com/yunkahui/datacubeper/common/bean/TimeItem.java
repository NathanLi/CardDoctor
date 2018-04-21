package com.yunkahui.datacubeper.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author WYF on 2018/4/20/020.
 */
public class TimeItem implements Parcelable {

    private int year;
    private int month;
    private int day;
    private boolean isSelected;

    public TimeItem() {
    }

    public TimeItem(int day, boolean isSelected) {
        this.day = day;
        this.isSelected = isSelected;
    }

    public TimeItem(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public TimeItem(int year, int month, int day, boolean isSelected) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.isSelected = isSelected;
    }

    protected TimeItem(Parcel in) {
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<TimeItem> CREATOR = new Creator<TimeItem>() {
        @Override
        public TimeItem createFromParcel(Parcel in) {
            return new TimeItem(in);
        }

        @Override
        public TimeItem[] newArray(int size) {
            return new TimeItem[size];
        }
    };

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(year);
        parcel.writeInt(month);
        parcel.writeInt(day);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeItem) {
            TimeItem t = (TimeItem) obj;
            return year == t.getYear() && month == t.getMonth() && day == t.getDay();
        }
        return super.equals(obj);
    }
}
