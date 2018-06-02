package com.yunkahui.datacubeper.common.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clint on 2018/1/5.
 */

public class ChartScoreView extends View {
    private Context mContext;
    private List<Score> mScores = new ArrayList<>();
    private float mTitleSize;
    private float mMessSize;

    public ChartScoreView(Context context) {
        super(context);
        mContext = context;
    }

    public ChartScoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mTitleSize = ChartUtil.dpToPx(mContext, 12);
        mMessSize = ChartUtil.dpToPx(mContext, 14);
    }

    public ChartScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measure_w = MeasureSpec.getSize(widthMeasureSpec);
        int measure_h = MeasureSpec.getSize(heightMeasureSpec);
        int measure_h_m = MeasureSpec.getMode(heightMeasureSpec);

        float height = ChartUtil.getFontHeight(mTitleSize) + ChartUtil.getFontHeight(mMessSize) + ChartUtil.dpToPx(mContext, 10);
        setMeasuredDimension(
                measure_w,
                measure_h_m == MeasureSpec.EXACTLY ? measure_h : (int)height);
    }

    public void setmScores(List<Score> mScores) {
        this.mScores = mScores;
    }

    public List<Score> getScores(){
        return mScores;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mScores.size() > 0) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);

            float height = ChartUtil.getFontHeight(mTitleSize);

            float width = (getMeasuredWidth() - (mScores.size() - 1)) / mScores.size();
            Log.d(ChartScoreView.class.getName(), "onDraw: " + mScores.size());
            for (int index = 0; index < mScores.size(); index++) {
                Log.d(ChartScoreView.class.getName(), "onDraw: ");
                Score tmpScore = mScores.get(index);
                paint.setColor(tmpScore.getColor());
                if (tmpScore.isSelect()) {
                    canvas.drawRect(new RectF((width + 1) * index, 0, (width + 1) * index + width, height + ChartUtil.dpToPx(mContext, 4)), paint);
                }else {
                    canvas.drawRect(new RectF((width + 1) * index, ChartUtil.dpToPx(mContext, 2), (width + 1) * index + width, height + ChartUtil.dpToPx(mContext, 2)), paint);
                }
                paint.setColor(Color.WHITE);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(mTitleSize);
                Paint.FontMetricsInt fm = paint.getFontMetricsInt();
                float baseLine = (height + ChartUtil.dpToPx(mContext, 4) - fm.bottom - fm.top) / 2.0f;
                canvas.drawText(tmpScore.getTitle(),(width + 1) * index + width / 2.0f, baseLine, paint);

                paint.setTextSize(mMessSize);
                float messBaseLine = ChartUtil.getFontHeight(mTitleSize) + ChartUtil.getFontBaseLine(mMessSize) + ChartUtil.dpToPx(mContext, 10);
                if (tmpScore.isSelect()) {
                    paint.setColor(Color.parseColor("#FF9500"));
                }else {
                    paint.setColor(Color.parseColor("#000000"));
                }
                canvas.drawText(tmpScore.getMess(), (width + 1) * index + width / 2.0f, messBaseLine, paint);
            }
        }
    }
}
