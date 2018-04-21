package com.yunkahui.datacubeper.common.view.chart;

import java.util.ArrayList;

/**
 * Created by clint on 2018/1/4.
 */

public class Site {
    private ArrayList<Float> yNumber;
    private int color;
    private boolean start;

    public Site(ArrayList<Float> yNumber, int color, boolean start) {
        this.yNumber = yNumber;
        this.color = color;
        this.start = start;
    }

    public ArrayList<Float> getyNumber() {
        return yNumber;
    }

    public int getColor() {
        return color;
    }

    public boolean isStart() {
        return start;
    }
}
