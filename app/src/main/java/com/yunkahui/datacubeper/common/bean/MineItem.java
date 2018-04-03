package com.yunkahui.datacubeper.common.bean;

/**
 * Created by YD1 on 2018/4/3
 */
public class MineItem {

    private int icon;
    private String title;
    private String detail;
    private boolean isShow;

    public MineItem(int icon, String title, boolean isShow) {
        this.icon = icon;
        this.title = title;
        this.isShow = isShow;
    }

    public MineItem(int icon, String title, String detail, boolean isShow) {
        this.icon = icon;
        this.title = title;
        this.detail = detail;
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
