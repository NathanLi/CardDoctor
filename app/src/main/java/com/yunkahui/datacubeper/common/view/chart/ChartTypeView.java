package com.yunkahui.datacubeper.common.view.chart;

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
 * Created by clint on 2018/1/5.
 */

public class ChartTypeView extends View {
    private Context mContext;
    private int lightColor;
    private int colorType;

    public ChartTypeView(Context context) {
        super(context);
        mContext = context;
    }

    public ChartTypeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartTypeView);
        lightColor = typedArray.getColor(R.styleable.ChartTypeView_lightColor, Color.parseColor("#ff4a90e2"));
        colorType = typedArray.getInt(R.styleable.ChartTypeView_colorType, 0x00000000);
        typedArray.recycle();
    }

    public ChartTypeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        if (colorType == 1) {
            setMeasuredDimension(
                    measure_w_m == MeasureSpec.EXACTLY ? measure_w : (int)ChartUtil.dpToPx(mContext, 20),
                    measure_h_m == MeasureSpec.EXACTLY ? measure_h : (int) ChartUtil.dpToPx(mContext, 8));
        }else {
            setMeasuredDimension(
                    measure_w_m == MeasureSpec.EXACTLY ? measure_w : (int)ChartUtil.dpToPx(mContext, 10),
                    measure_h_m == MeasureSpec.EXACTLY ? measure_h : (int) ChartUtil.dpToPx(mContext, 10));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        paint.setColor(lightColor);

        if (colorType == 1) {
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawLine(0, getMeasuredHeight() / 2.0f, getMeasuredWidth(), getMeasuredHeight() / 2.0f, paint);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(getMeasuredWidth() /2.0f, getMeasuredHeight() / 2.0f, getMeasuredHeight() / 2.0f, paint);
        }else {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight()), paint);
        }
    }
}
