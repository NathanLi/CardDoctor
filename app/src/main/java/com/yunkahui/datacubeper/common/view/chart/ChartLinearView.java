package com.yunkahui.datacubeper.common.view.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by clint on 2018/1/3.
 */

public class ChartLinearView extends View implements ChartAniInterface {
    private Context mContext;
    private List<Pillar> mPillars = new ArrayList<>();
    private List<Site> mPoints = new ArrayList<>();
    private List<ChartAxis> mChartAxes = new ArrayList<>();
    private float pillarWidth = 0;
    private float pointRadius = 0;
    private float showRatio = 1;
    private float bottomSpace = 0;
    private boolean isShow = false;

    public ChartLinearView(Context context) {
        super(context);
        mContext = context;
    }

    public ChartLinearView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ChartLinearView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measure_w = MeasureSpec.getSize(widthMeasureSpec);
        int measure_h = MeasureSpec.getSize(heightMeasureSpec);
        int measure_h_m = MeasureSpec.getMode(heightMeasureSpec);
        float baseNum = 0;
        for (int index = 0; index < mChartAxes.size(); index++) {
            if (mChartAxes.get(index).getAxis() == ChartAxis.Axis_Y) {
                baseNum = Math.max(baseNum, mChartAxes.get(index).getScalar().size());
            }
        }

        setMeasuredDimension(measure_w, measure_h_m == MeasureSpec.EXACTLY ? measure_h : (int)(ChartUtil.dpToPx(mContext, 28) * (baseNum + 1)));
    }

    public void show() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pillarWidth = ChartUtil.dpToPx(mContext, 10);
        pointRadius = ChartUtil.dpToPx(mContext, 4);
        int xIndex = -1;
        int yLIndex = -1;
        int yRIndex = -1;
        for (int index = 0; index < mChartAxes.size(); index++) {
            ChartAxis tmp = mChartAxes.get(index);
            if (tmp.getAxis() == ChartAxis.Axis_X) {
                xIndex = index;
            }
            if (tmp.getAxis() == ChartAxis.Axis_Y && tmp.isStart()) {
                yLIndex = index;
            }
            if (tmp.getAxis() == ChartAxis.Axis_Y && !tmp.isStart()) {
                yRIndex = index;
            }

        }
        if ((xIndex != -1) && (yLIndex != -1 || yRIndex != -1)) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            //进行绘制
            drawAxis(canvas, xIndex, yLIndex, yRIndex, paint);
        }
    }

    /**
     * 绘制轴线
     * @param canvas
     */
    private void drawAxis(Canvas canvas, int xIndex, int yLIndex, int yRIndex, Paint paint) {
        Paint auxPaint = new Paint();
        auxPaint.setStrokeWidth(1);
        auxPaint.setColor(Color.parseColor("#eeeeee"));
        auxPaint.setStyle(Paint.Style.STROKE);


        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        ChartAxis xAxis = mChartAxes.get(xIndex);
        float leftMargin = ChartUtil.dpToPx(mContext, 2);
        float rightMargin = ChartUtil.dpToPx(mContext, 2);
        float bottomMargin = ChartUtil.dpToPx(mContext, 10) + ChartUtil.getFontHeight(xAxis.getTextSize());

        ChartAxis yLAxis = null;
        ChartAxis yRAxis = null;


        if (yLIndex != -1) {
            List<Float> yPositions = new ArrayList<>();

            yLAxis = mChartAxes.get(yLIndex);
            int tmpWidth = 0;
            for (int index = 0; index < yLAxis.getTextWidths().size(); index++) {
                tmpWidth = Math.max(tmpWidth, yLAxis.getTextWidths().get(index));
            }
            leftMargin += tmpWidth;

            float pointX = leftMargin + xAxis.getStartOffset();
            float startY = getMeasuredHeight() - bottomMargin + yLAxis.getStartOffset();
            float endY = ChartUtil.getFontHeight(yLAxis.getTextSize()) / 2.0f - yLAxis.getEndOffset();

            //绘制y起始轴线
            canvas.drawLine(pointX, startY, pointX, endY, paint);

            yLAxis.setStartP(new PointF(pointX, startY));
            yLAxis.setEndP(new PointF(pointX, endY));

            paint.setTextAlign(Paint.Align.RIGHT);
            paint.setTextSize(yLAxis.getTextSize());
            paint.setStyle(Paint.Style.FILL);
            //绘制y起始轴文字
            float startPadding, space;
            float yContent = getMeasuredHeight() - bottomMargin - ChartUtil.getFontHeight(yLAxis.getTextSize()) / 2.0f;

            if (yLAxis.getStartPadding() >= 0 && yLAxis.getEndPadding() >= 0) {
                startPadding = bottomMargin;
                space = (yContent - yLAxis.getStartPadding() - yLAxis.getEndPadding()) / (yLAxis.getScalar().size() - 1);
            }else if (yLAxis.getStartPadding() < 0 && yLAxis.getEndPadding() < 0) {
                space = (yContent) / (yLAxis.getScalar().size() - yLAxis.getStartPadding() - yLAxis.getEndPadding() - 1);
                startPadding = bottomMargin + space * -1 * yLAxis.getStartPadding();
            }else if (yLAxis.getStartPadding() >= 0 && yLAxis.getEndPadding() < 0) {
                startPadding = bottomMargin + yLAxis.getStartPadding();
                space = (yContent - yLAxis.getStartPadding()) / (yLAxis.getScalar().size() - yLAxis.getEndPadding() - 1);
            }else {
                space = (yContent - yLAxis.getEndPadding()) / (yLAxis.getScalar().size() - yLAxis.getStartPadding() - 1);
                startPadding = bottomMargin + space * -1 * yLAxis.getStartPadding();
            }
            for (int index = 0; index < yLAxis.getScalar().size(); index++) {
                String mess;
                if (yLAxis.isInteger()) {
                    mess = yLAxis.getScalar().get(index).intValue() + yLAxis.getUnit();
                }else {
                    mess = yLAxis.getScalar().get(index) + yLAxis.getUnit();
                }

                canvas.drawText(mess,
                        leftMargin - ChartUtil.dpToPx(mContext, 2),
                        getMeasuredHeight() - (startPadding + space * index - ChartUtil.getFontHeight(yLAxis.getTextSize()) / 4.0f), paint);
                yPositions.add(getMeasuredHeight() - (startPadding + space * index));
            }
            yLAxis.setyPositions(yPositions);
        }
        if (yRIndex != -1) {
            List<Float> yPositions = new ArrayList<>();

            yRAxis = mChartAxes.get(yRIndex);
            int tmpWidth = 0;
            for (int index = 0; index < yRAxis.getTextWidths().size(); index++) {
                tmpWidth = Math.max(tmpWidth, yRAxis.getTextWidths().get(index));
            }
            rightMargin += tmpWidth;

            float pointX = getMeasuredWidth() - rightMargin - xAxis.getEndOffset();
            float startY = getMeasuredHeight() - bottomMargin + yRAxis.getStartOffset();
            float endY = ChartUtil.getFontHeight(yRAxis.getTextSize()) / 2.0f - yRAxis.getEndOffset();

            //绘制y终止轴线
            canvas.drawLine(pointX, startY, pointX, endY, paint);
            yRAxis.setStartP(new PointF(pointX, startY));
            yRAxis.setEndP(new PointF(pointX, endY));

            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(yRAxis.getTextSize());
            paint.setStyle(Paint.Style.FILL);
            //绘制y终止轴文字
            float startPadding, space;
            float yContent = getMeasuredHeight() - bottomMargin - ChartUtil.getFontHeight(yRAxis.getTextSize()) / 2.0f;
            if (yRAxis.getStartPadding() >=0 && yRAxis.getEndPadding() >= 0) {
                startPadding = bottomMargin;
                space = (yContent - yRAxis.getStartPadding() - yRAxis.getEndPadding()) / (yRAxis.getScalar().size() - 1);
            }else if (yRAxis.getStartPadding() < 0 && yRAxis.getEndPadding() < 0) {
                space = (yContent) / (yRAxis.getScalar().size() - yRAxis.getStartPadding() - yRAxis.getEndPadding() - 1);
                startPadding = bottomMargin + space * -1 * yRAxis.getStartPadding();
            }else if (yRAxis.getStartPadding() >= 0 && yRAxis.getEndPadding() < 0) {
                startPadding = bottomMargin + yRAxis.getStartPadding();
                space = (yContent - yRAxis.getStartPadding()) / (yRAxis.getScalar().size() - yRAxis.getEndPadding() - 1);
            }else {
                space = (yContent - yRAxis.getEndPadding()) / (yRAxis.getScalar().size() - yRAxis.getStartPadding() - 1);
                startPadding = bottomMargin + space * -1 * yRAxis.getStartPadding();
            }

            for (int index = 0; index < yRAxis.getScalar().size(); index++) {
                String mess;
                if (yRAxis.isInteger()) {
                    mess = yRAxis.getScalar().get(index).intValue() + yRAxis.getUnit();
                }else {
                    mess = yRAxis.getScalar().get(index) + yRAxis.getUnit();
                }
                canvas.drawText(mess,
                        getMeasuredWidth() - rightMargin + ChartUtil.dpToPx(mContext, 2),
                        getMeasuredHeight() - (startPadding + space * index - ChartUtil.getFontHeight(yRAxis.getTextSize()) / 4.0f), paint);
                yPositions.add(getMeasuredHeight() - (startPadding + space * index));
            }
            yRAxis.setyPositions(yPositions);
        }

        bottomSpace = bottomMargin;
        //绘制X轴线
        canvas.drawLine(leftMargin, getMeasuredHeight() - bottomMargin, getMeasuredWidth() - rightMargin, getMeasuredHeight() - bottomMargin, paint);
        xAxis.setStartP(new PointF(leftMargin, getMeasuredHeight() - bottomMargin));
        xAxis.setEndP(new PointF(getMeasuredWidth() - rightMargin, getMeasuredHeight() - bottomMargin));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(xAxis.getTextSize());
        paint.setStyle(Paint.Style.FILL);

        List<Float> xPosition = new ArrayList<>();
        float startPadding, space;
        float xContent = getMeasuredWidth() - leftMargin - rightMargin - xAxis.getStartOffset() - xAxis.getEndOffset();
        if (xAxis.getStartPadding() >= 0 && xAxis.getEndPadding() >= 0) {
            startPadding = leftMargin + xAxis.getStartPadding() + xAxis.getStartOffset();
            space = (xContent - xAxis.getStartPadding() - xAxis.getEndPadding()) / (xAxis.getxShow().size() - 1);
        }else if (xAxis.getStartPadding() < 0 && xAxis.getEndPadding() < 0) {
            space = xContent / (xAxis.getxShow().size() - xAxis.getStartPadding() - xAxis.getEndPadding() - 1);
            startPadding = leftMargin + space * -1 * xAxis.getStartPadding();
        }else if (xAxis.getStartPadding() >= 0 && xAxis.getEndPadding() < 0) {
            startPadding = leftMargin + xAxis.getStartPadding();
            space = (xContent - xAxis.getStartPadding()) / (xAxis.getxShow().size() - xAxis.getEndPadding() - 1);
        }else {
            space = (xContent - xAxis.getEndPadding()) / (xAxis.getxShow().size() - xAxis.getStartPadding() - 1);
            startPadding = leftMargin + xAxis.getStartPadding() * space;
        }

        Paint.FontMetricsInt fm = paint.getFontMetricsInt();

        for (int index = 0; index < xAxis.getxShow().size(); index++) {
            String mess = xAxis.getxShow().get(index);
            canvas.drawText(mess,
                    startPadding + space * index,
                    getMeasuredHeight() - fm.bottom, paint);
            xPosition.add(startPadding + space * index);
        }
        xAxis.setyPositions(xPosition);

        if (yLAxis != null) {
            if (yLAxis.isAuxiliary()) {
                //绘制辅助线
                for (int index = 0; index < yLAxis.getyPositions().size(); index++) {
                    canvas.drawLine(leftMargin, yLAxis.getyPositions().get(index), getMeasuredWidth() - rightMargin, yLAxis.getyPositions().get(index), auxPaint);
                }
            }
        }

        if (yRAxis != null) {
            if (yRAxis.isAuxiliary()) {
                //绘制辅助线
                for (int index = 0; index < yRAxis.getyPositions().size(); index++) {
                    canvas.drawLine(leftMargin, yRAxis.getyPositions().get(index), getMeasuredWidth() - rightMargin, yRAxis.getyPositions().get(index), auxPaint);
                }
            }
        }

        if (xAxis != null) {
            if (xAxis.isAuxiliary()) {
                //绘制辅助线
                float endY = 0;
                if (yLAxis != null) {
                    endY = Math.max(endY, ChartUtil.getFontHeight(yLAxis.getTextSize()) / 2.0f);
                }
                if (yRAxis != null) {
                    endY = Math.max(endY, ChartUtil.getFontHeight(yRAxis.getTextSize()) / 2.0f);
                }
                for (int index = 0; index < xAxis.getxShow().size(); index++) {
                    canvas.drawLine(xAxis.getyPositions().get(index), getMeasuredHeight() - bottomMargin, xAxis.getyPositions().get(index), endY, auxPaint);
                }
            }
        }

        //绘柱线

        if (yLAxis != null) {
            yLAxis.requestStandard();
        }

        if (yRAxis != null) {
            yRAxis.requestStandard();
        }

        Paint pillarPaint = new Paint();
        for (int index = 0; index < mPillars.size(); index++) {
            Pillar pillar = mPillars.get(index);
            float pillarXPosition = xPosition.get(pillar.getxIndex());
            float base = pillar.getyNumber().size() / 2.0f;
            for (int yIndex = 0; yIndex < pillar.getyNumber().size(); yIndex++) {
                pillarPaint.setColor(pillar.getColor().get(yIndex));

                float zeroY, increment;
                if (pillar.getStart().get(yIndex)) {
                    zeroY = yLAxis.getZeroY();
                    increment = yLAxis.getIncrement();
                }else {
                    zeroY = yRAxis.getZeroY();
                    increment = yRAxis.getIncrement();
                }

                float x = pillarXPosition - pillarWidth * (base - yIndex);
                float top = trueTop(pillar.getyNumber().get(yIndex), zeroY, increment);
                canvas.drawRect(new RectF(x, top, x + pillarWidth, getMeasuredHeight() - bottomMargin - 1), pillarPaint);
            }
        }
        //绘点
        Paint pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pointPaint.setStrokeWidth(2);
        for (int index = 0; index < mPoints.size(); index++) {
            Site tmpSite = mPoints.get(index);
            pointPaint.setColor(tmpSite.getColor());
            float zeroY, increment;
            if (tmpSite.isStart()) {
                zeroY = yLAxis.getZeroY();
                increment = yLAxis.getIncrement();
            }else {
                zeroY = yRAxis.getZeroY();
                increment = yRAxis.getIncrement();
            }
            int showPointIndex = -1;
            for (int nIndex = 0; nIndex < tmpSite.getyNumber().size(); nIndex++) {
                float tmpY = tmpSite.getyNumber().get(nIndex);
                if (tmpY != -1) {
                    canvas.drawCircle(xPosition.get(nIndex), trueTop(tmpY, zeroY, increment), pointRadius, pointPaint);
                    if (showPointIndex != -1) {
                        canvas.drawLine(
                                xPosition.get(showPointIndex), trueTop(tmpSite.getyNumber().get(showPointIndex), zeroY, increment),
                                xPosition.get(nIndex), trueTop(tmpY, zeroY, increment), pointPaint);
                    }
                    showPointIndex = nIndex;
                }
            }
        }

        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        if (xAxis != null) {
            canvas.drawLine(xAxis.getStartP().x, xAxis.getStartP().y, xAxis.getEndP().x, xAxis.getEndP().y, paint);
        }
        if (yLAxis != null) {
            canvas.drawLine(yLAxis.getStartP().x, yLAxis.getStartP().y, yLAxis.getEndP().x, yLAxis.getEndP().y, paint);
        }
        if (yRAxis != null) {
            canvas.drawLine(yRAxis.getStartP().x, yRAxis.getStartP().y, yRAxis.getEndP().x, yRAxis.getEndP().y, paint);
        }

        isShow = true;

    }

    private float trueTop(float yPosition, float zero, float increment) {
        return (getMeasuredHeight() - bottomSpace) - (getMeasuredHeight() - bottomSpace - (zero + increment * yPosition)) * showRatio;
    }

    public boolean isShow() {
        return isShow;
    }

    /**
     * 增加轴线
     * @param axis 轴线参数
     * @param start 是否起始
     */
    public void addAxis(ChartAxis axis, boolean start) {
        axis.setStart(start);
        int equalIndex = -1;
        for (int index = 0; index < mChartAxes.size(); index++) {
            if (mChartAxes.get(index).equalAxis(axis)) {
                equalIndex = index;
                break;
            }
        }
        if (equalIndex != -1) {
            mChartAxes.remove(equalIndex);
        }
        mChartAxes.add(axis);
    }

    /**
     * 添加多个点
     */
    public void addPoints(float[] numbers, int color, boolean start) {
        Float[] tmpNumber = new Float[numbers.length];
        for (int index = 0; index < numbers.length; index++) {
            tmpNumber[index] = numbers[index];
        }

        mPoints.add(new Site(new ArrayList<Float>(Arrays.asList(tmpNumber)), color, start));
    }

    public void addPillars(float[] numbers, int color, boolean start) {

        for (int index = 0; index < numbers.length; index++) {
            for (int pIndex = 0; pIndex < mPillars.size(); pIndex++) {
                if (mPillars.get(pIndex).getxIndex() == index) {
                    List<Float> tmpY = mPillars.get(pIndex).getyNumber();
                    tmpY.add(numbers[index]);
                    numbers[index] = -1;

                    List<Integer> colors = mPillars.get(pIndex).getColor();
                    colors.add(color);

                    List<Boolean> starts = mPillars.get(pIndex).getStart();
                    starts.add(start);
                }
            }
        }

        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index] != -1) {
                Pillar pillar = new Pillar(index, new Float[]{numbers[index]}, new Integer[]{color}, new Boolean[]{start});
                mPillars.add(pillar);
            }
        }
    }

    /**
     * 添加多个柱
     * @param pillars
     */
    public void addPillars(Pillar[] pillars) {
        mPillars.addAll(Arrays.asList(pillars));
    }



    @Override
    public void showAni() {
        ValueAnimator animator = ValueAnimator.ofFloat(0.1f, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                showRatio = (float)animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.start();
    }
}
