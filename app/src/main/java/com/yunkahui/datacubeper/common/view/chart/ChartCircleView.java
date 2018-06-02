package com.yunkahui.datacubeper.common.view.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.yunkahui.datacubeper.R;

/**
 * Created by clint on 2018/1/3.
 */

public class ChartCircleView extends View implements ChartAniInterface {
    private Context mContext;
    private int lightColor, darkColor, centerColor;
    private float lightRatio, circleWidth;
    private CharSequence centerTitle;
    private float centerSize;
    float showRatio = 0;

    public ChartCircleView(Context context) {
        super(context);
        mContext = context;
    }

    public ChartCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartCircleView);
        lightColor = typedArray.getColor(R.styleable.ChartCircleView_lightColor, Color.parseColor("#5AC8FA"));
        darkColor = typedArray.getColor(R.styleable.ChartCircleView_darkColor, Color.parseColor("#D8D8D8"));
        centerColor = typedArray.getColor(R.styleable.ChartCircleView_centerColor, Color.parseColor("#666666"));
        lightRatio = typedArray.getFloat(R.styleable.ChartCircleView_lightRatio, 0);
        circleWidth = typedArray.getDimension(R.styleable.ChartCircleView_circleWidth, ChartUtil.dpToPx(mContext, 8));
        centerTitle = typedArray.getString(R.styleable.ChartCircleView_centerTitle);
        if (centerTitle == null) {
            centerTitle = "";
        }
        centerSize = typedArray.getDimension(R.styleable.ChartCircleView_centerSize, ChartUtil.dpToPx(mContext, 12));
        showRatio = lightRatio;

        typedArray.recycle();
    }

    public ChartCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measure_w = MeasureSpec.getSize(widthMeasureSpec);
        int measure_h = MeasureSpec.getSize(heightMeasureSpec);
        int measure_w_m = MeasureSpec.getMode(widthMeasureSpec);
        int measure_h_m = MeasureSpec.getMode(heightMeasureSpec);
        setMeasuredDimension(
                measure_w_m == MeasureSpec.EXACTLY ? measure_w : (int) ChartUtil.dpToPx(mContext, 100),
                measure_h_m == MeasureSpec.EXACTLY ? measure_h : (int) ChartUtil.dpToPx(mContext, 100));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawWithPosition(canvas, showRatio);
    }

    public int getLightColor() {
        return lightColor;
    }

    public void setLightColor(int lightColor) {
        this.lightColor = lightColor;
    }

    public int getDarkColor() {
        return darkColor;
    }

    public void setDarkColor(int darkColor) {
        this.darkColor = darkColor;
    }

    public int getCenterColor() {
        return centerColor;
    }

    public void setCenterColor(int centerColor) {
        this.centerColor = centerColor;
    }

    public float getLightRatio() {
        return lightRatio;
    }

    public void setLightRatio(float lightRatio) {
        this.lightRatio = lightRatio;
        this.showRatio = lightRatio;
    }

    public float getCircleWidth() {
        return circleWidth;
    }

    public void setCircleWidth(float circleWidth) {
        this.circleWidth = circleWidth;
    }

    public String getCenterTitle() {
        return centerTitle.toString();
    }

    public void setCenterTitle(CharSequence centerTitle) {
        this.centerTitle = centerTitle;
    }

    public float getCenterSize() {
        return centerSize;
    }

    public void setCenterSize(float centerSize) {
        this.centerSize = centerSize;
    }

    private void drawWithPosition(Canvas canvas, float ratio) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(circleWidth);
        paint.setAntiAlias(true);
        float min = Math.min(getMeasuredWidth() - circleWidth, getMeasuredHeight() - circleWidth);
        RectF rectF = new RectF(
                (getMeasuredWidth() - min) / 2.0f,
                (getMeasuredHeight() - min) / 2.0f,
                (getMeasuredWidth() + min) / 2.0f,
                (getMeasuredHeight() + min) / 2.0f
        );
        paint.setColor(darkColor);
        canvas.drawArc(rectF, 0, 360, false, paint);
        paint.setColor(lightColor);
        canvas.drawArc(rectF, -90, 360 * ratio, false, paint);

//        paint.setTextSize(centerSize);
//        paint.setColor(centerColor);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setTextAlign(Paint.Align.CENTER);
//        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
//        int baseLine = (getMeasuredHeight() - fontMetrics.bottom - fontMetrics.top) / 2;
//        canvas.drawText(centerTitle, getMeasuredWidth() / 2.0f, baseLine, paint);

        TextPaint tp = new TextPaint();
        tp.setColor(centerColor);
        tp.setStyle(Paint.Style.FILL);
        tp.setTextSize(centerSize);
        tp.setAntiAlias(true);
        StaticLayout layout = new StaticLayout(centerTitle, tp, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.translate(0, getMeasuredHeight() / 2.0f - layout.getHeight() / 2.0f);
        layout.draw(canvas);


//        TextPaint textPaint = new TextPaint();
//        textPaint.setColor(centerColor);
//        textPaint.setStyle(Paint.Style.FILL);
//        textPaint.setTextSize(centerSize);
//        textPaint.setAntiAlias(true);
//        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
//        int baseLine = (getMeasuredHeight() - fontMetrics.bottom - fontMetrics.top) / 2;
//        StaticLayout layout = new StaticLayout(centerTitle, textPaint, (int) min, Layout.Alignment.ALIGN_NORMAL, 0, 0, true);
//        canvas.save();
////        canvas.translate(getMeasuredWidth() / 2.0f, baseLine);
//        layout.draw(canvas);
//        canvas.restore();
    }

    @Override
    public void showAni() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, lightRatio);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                showRatio = (float)animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(1000);
        animator.start();
    }
}
