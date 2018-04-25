package com.yunkahui.datacubeper.common.bean;

/**
 * Created by pc1994 on 2018/3/23.
 */

public class HomeItem {

    private int id;
    private int icon;
    private String title;

    public HomeItem() {
    }

    public HomeItem(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
