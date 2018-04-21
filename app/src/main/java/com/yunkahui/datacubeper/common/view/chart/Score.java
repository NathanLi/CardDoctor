package com.yunkahui.datacubeper.common.view.chart;

/**
 * Created by clint on 2018/1/5.
 */

public class Score {
    private int color;
    private boolean select;
    private String title;
    private String mess;

    public Score(int color, boolean select, String title, String mess) {
        this.color = color;
        this.select = select;
        this.title = title;
        this.mess = mess;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}
