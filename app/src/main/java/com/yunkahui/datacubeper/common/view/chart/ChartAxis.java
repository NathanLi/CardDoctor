package com.yunkahui.datacubeper.common.view.chart;

import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clint on 2018/1/3.
 */

public class ChartAxis {
    public static final int Axis_X = 0;
    public static final int Axis_Y = 1;

    /** 轴向 */
    private int axis;
    /** 单位，Y轴标量显示 */
    private String unit;
    /** 标量组，用于Y轴 */
    private List<Float> scalar;
    /** 显示内容，用于X轴 */
    private List<String> xShow;
    /** 线的起始偏移量 */
    private float startOffset;
    /** 线的终止偏移量 */
    private float endOffset;
    /** 文字尺寸*/
    private float textSize;
    /** 是否为顺序 */
    private boolean start;

    /** 起始内容内收量，正数为尺寸，负数为项区 */
    private float startPadding;
    /** 终止内容内收量，正数为尺寸，负数为项区 */
    private float endPadding;

    /** 是否有辅助线 */
    private boolean auxiliary;

    private List<Float> yPositions;

    private List<Integer> textWidths;

    private PointF startP;
    private PointF endP;
    private boolean isInteger;

    //0位置
    private float zeroY;
    //单位增量
    private float increment;

    @Override
    public String toString() {
        return "ChartAxis{" +
                "axis=" + axis +
                ", start=" + start +
                '}';
    }

    /**
     * 求取单位量
     */
    public void requestStandard() {
        float startScalar = scalar.get(0);
        float endScalar = scalar.get(scalar.size() - 1);

        float startY = yPositions.get(0);
        float endY = yPositions.get(yPositions.size() - 1);

        increment = (endY - startY) / (endScalar - startScalar);
        zeroY = startY - startScalar * increment;
    }

    public ChartAxis() {
        this.axis = Axis_X;
        this.unit = "";
        this.startOffset = 0;
        this.endOffset = 0;
        this.scalar = new ArrayList<>();
        this.xShow = new ArrayList<>();
        this.textSize = 20;
        this.startPadding = 0;
        this.endPadding = 0;
        auxiliary = false;
        textWidths = new ArrayList<>();
        yPositions = new ArrayList<>();
        isInteger = false;
    }

    public float getZeroY() {
        return zeroY;
    }

    public float getIncrement() {
        return increment;
    }

    public boolean equalAxis(ChartAxis chartAxis) {
        boolean equal;
        if (chartAxis.axis == Axis_X && this.axis == Axis_X) {
            equal = true;
        }else {
            equal = ((this.start && chartAxis.start) && (this.axis == chartAxis.axis));
        }
        return equal;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public static int getAxis_X() {
        return Axis_X;
    }

    public static int getAxis_Y() {
        return Axis_Y;
    }

    public int getAxis() {
        return axis;
    }

    public String getUnit() {
        return unit;
    }

    public List<Integer> getTextWidths() {
        return textWidths;
    }

    public List<Float> getScalar() {
        return scalar;
    }

    public float getStartOffset() {
        return startOffset;
    }

    public float getEndOffset() {
        return endOffset;
    }

    public List<String> getxShow() {
        return xShow;
    }

    public float getTextSize() {
        return textSize;
    }

    public boolean isStart() {
        return start;
    }

    public float getStartPadding() {
        return startPadding;
    }

    public float getEndPadding() {
        return endPadding;
    }

    public List<Float> getyPositions() {
        return yPositions;
    }

    public boolean isAuxiliary() {
        return auxiliary;
    }

    public void setyPositions(List<Float> yPositions) {
        this.yPositions = yPositions;
    }

    public PointF getStartP() {
        return startP;
    }

    public void setStartP(PointF startP) {
        this.startP = startP;
    }

    public PointF getEndP() {
        return endP;
    }

    public void setEndP(PointF endP) {
        this.endP = endP;
    }

    public boolean isInteger() {
        return isInteger;
    }

    public static class Builder {
        private ChartAxis chartAxis;

        public Builder(float start, int num, float capacity) {
            chartAxis = new ChartAxis();
            chartAxis.axis = Axis_Y;
            List<Float> tmpList = new ArrayList<>();
            tmpList.add(start);
            for (int index = 0; index < num; index++) {
                tmpList.add(start + (index + 1) * capacity);
            }
            chartAxis.scalar = tmpList;
        }

        public Builder(List<String> mxShow) {
            chartAxis = new ChartAxis();
            chartAxis.axis = Axis_X;
            chartAxis.xShow = mxShow;
        }

        public Builder unit(String unit) {
            chartAxis.unit = unit;
            return this;
        }

        public Builder startPadding(float startPadding) {
            chartAxis.startPadding = startPadding;
            return this;
        }

        public Builder endPadding(float endPadding) {
            chartAxis.endPadding = endPadding;
            return this;
        }

        public Builder startOffset(float startOffset) {
            chartAxis.startOffset = startOffset;
            return this;
        }

        public Builder endOffset(float endOffset) {
            chartAxis.endOffset = endOffset;
            return this;
        }

        public Builder textSize(float textSize) {
            chartAxis.textSize = textSize;
            return this;
        }

        public Builder start(boolean start) {
            chartAxis.start = start;
            return this;
        }

        public Builder auxiliary(boolean auxiliary) {
            chartAxis.auxiliary = auxiliary;
            return this;
        }

        public Builder isInteger(boolean isInteger) {
            chartAxis.isInteger = isInteger;
            return this;
        }

        public ChartAxis create() {
            Paint paint = new Paint();
            paint.setTextSize(chartAxis.textSize);
            
            if (chartAxis.axis == Axis_X) {
                List<Integer> tmp = new ArrayList<>();
                for (int index = 0; index < chartAxis.xShow.size(); index++) {
                    String tmpStr = chartAxis.xShow.get(index);

                    tmp.add((int) paint.measureText(tmpStr));
                }
                chartAxis.textWidths = tmp;
            }else {
                List<Integer> tmp = new ArrayList<>();
                for (int index = 0; index < chartAxis.scalar.size(); index++) {
                    String tmpStr = chartAxis.scalar.get(index) + chartAxis.unit;
                    tmp.add((int) paint.measureText(tmpStr));
                }
                chartAxis.textWidths = tmp;
            }

            return chartAxis;
        }

    }
}
