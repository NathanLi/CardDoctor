package com.yunkahui.datacubeper.common.view.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yunkahui.datacubeper.R;

/**
 * Created by clint on 2018/1/3.
 */

public class ChartSingleView extends View implements ChartAniInterface {
    private Context mContext;
    int lightColor, darkColor;
    float lightRatio, cornerRadius;
    float showRatio = 0;

    public ChartSingleView(Context context) {
        super(context);
        mContext = context;
    }

    public ChartSingleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartSingleView);
        lightColor = typedArray.getColor(R.styleable.ChartSingleView_lightColor, Color.parseColor("#ff4a90e2"));
        darkColor = typedArray.getColor(R.styleable.ChartSingleView_darkColor, Color.parseColor("#334a90e2"));
        lightRatio = typedArray.getFloat(R.styleable.ChartSingleView_lightRatio, 0);
        cornerRadius = typedArray.getDimension(R.styleable.ChartSingleView_cornerRadius, -1);
        showRatio = lightRatio;
        typedArray.recycle();
    }

    public ChartSingleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measure_w = MeasureSpec.getSize(widthMeasureSpec);
        int measure_h = MeasureSpec.getSize(heightMeasureSpec);
        int measure_h_m = MeasureSpec.getMode(heightMeasureSpec);
        setMeasuredDimension(measure_w, measure_h_m == MeasureSpec.EXACTLY ? measure_h : (int) ChartUtil.dpToPx(mContext, 10));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawWithPosition(canvas, showRatio);
    }

    public void setLightColor(int lightColor) {
        this.lightColor = lightColor;
    }

    public void setDarkColor(int darkColor) {
        this.darkColor = darkColor;
    }

    public void setLightRatio(float lightRatio) {
        this.lightRatio = lightRatio;
        this.showRatio = lightRatio;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setShowRatio(float showRatio) {
        this.showRatio = showRatio;
    }

    private void drawWithPosition(Canvas canvas, float ratio) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(darkColor);
        paint.setStyle(Paint.Style.FILL);
        float width = ratio * getMeasuredWidth();
        float corner = (cornerRadius == -1 ? getMeasuredHeight() / 2.0f : (cornerRadius > getMeasuredHeight() / 2.0f ? getMeasuredHeight() / 2.0f : cornerRadius));
        canvas.drawRoundRect(new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight()), corner, corner, paint);
        paint.setColor(lightColor);
        canvas.drawRoundRect(new RectF(0, 0, width, getMeasuredHeight()), corner, corner, paint);
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
