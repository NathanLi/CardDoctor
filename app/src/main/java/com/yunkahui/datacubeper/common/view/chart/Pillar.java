package com.yunkahui.datacubeper.common.view.chart;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by clint on 2018/1/3.
 */

public class Pillar {
    private int xIndex;
    private ArrayList<Float> yNumber;
    private ArrayList<Integer> color;
    private ArrayList<Boolean> start;

    public Pillar(int xIndex, Float[] yNumber, Integer[] color, Boolean[] start) {
        this.xIndex = xIndex;
        this.yNumber = new ArrayList<>(Arrays.asList(yNumber));
        this.color = new ArrayList<>(Arrays.asList(color));
        this.start = new ArrayList<>(Arrays.asList(start));
    }

    public Pillar(int xIndex, ArrayList<Float> yNumber, ArrayList<Integer> color, ArrayList<Boolean> start) {
        this.xIndex = xIndex;
        this.yNumber = yNumber;
        this.color = color;
        this.start = start;
    }

    public int getxIndex() {
        return xIndex;
    }

    public void setxIndex(int xIndex) {
        this.xIndex = xIndex;
    }

    public ArrayList<Float> getyNumber() {
        return yNumber;
    }

    public void setyNumber(ArrayList<Float> yNumber) {
        this.yNumber = yNumber;
    }

    public ArrayList<Integer> getColor() {
        return color;
    }

    public void setColor(ArrayList<Integer> color) {
        this.color = color;
    }

    public ArrayList<Boolean> getStart() {
        return start;
    }

    public void setStart(ArrayList<Boolean> start) {
        this.start = start;
    }
}
