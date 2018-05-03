package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by pc1994 on 2018/3/24
 */
public class CardTestView extends FrameLayout {

    private String[] scaleValue = {"0", "10", "20", "30", "40", "较差", "中等", "良好", "优秀", "极好", "100"};
    private int radius = 14;
    private ScoreView scoreView;
    private Paint borderPaint;
    private Paint scalePaint;
    private int width;
    private int height;
    private int rectfWidth;
    private int rectfHeight;
    private double score = 0;
    private PointView pointView;
    private Paint pointPaint;
    private float currentAngle = 73;
    private RectF mRectf;
    private DashBoardView dashBoardView;
    private Thread thread;

    public CardTestView(Context context) {
        this(context, null);
    }

    public CardTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBorderPaint();
        initScalePaint();
        initPointPaint();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dashBoardView = new DashBoardView(context, attrs);
        addView(dashBoardView, layoutParams);
        scoreView = new ScoreView(context, attrs);
        addView(scoreView, layoutParams);
        pointView = new PointView(context, attrs);
        addView(pointView, layoutParams);
    }

    private void initPointPaint() {
        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.WHITE);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void initScalePaint() {
        scalePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        scalePaint.setStyle(Paint.Style.STROKE);
        scalePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void initBorderPaint() {
        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
    }

    public void runScore(final int destScore) {
        if (thread != null) {
            thread.interrupt();
        }
        currentAngle = 72;
        score = 0;
        //******** 防止越界 ********
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (score < destScore) {
                        Thread.sleep(50);
                        score += 0.8;
                        currentAngle += 1.7;
                        if (score >= destScore) { //******** 防止越界 ********
                            score = destScore;
                        }
                        pointView.postInvalidate();
                        scoreView.postInvalidate();
                        dashBoardView.postInvalidate();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private class PointView extends View {

        public PointView(Context context) {
            this(context, null);
        }

        public PointView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onDraw(final Canvas canvas) {
            super.onDraw(canvas);
            //绘制圆点
            canvas.save();

            canvas.translate(width / 2, rectfHeight / 2 + height * 2 / 103);
            canvas.rotate(currentAngle);
            borderPaint.setStrokeWidth(width / 100);
            borderPaint.setColor(Color.RED);
            if (currentAngle < 145) {
                canvas.drawCircle(0, width / 2 - (width / 2 - rectfWidth / 2) - 3, radius, pointPaint);
            } else if (currentAngle > 145 && currentAngle < 218) {
                canvas.drawCircle(0, width / 2 - (width / 2 - rectfWidth / 2) - 5, radius, pointPaint);
            } else {
                canvas.drawCircle(0, width / 2 - (width / 2 - rectfWidth / 2) - 3, radius, pointPaint);
            }
            canvas.restore();
        }
    }

    private class ScoreView extends View {

        public ScoreView(Context context) {
            this(context, null);
        }

        public ScoreView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            scalePaint.setColor(Color.WHITE);
            scalePaint.setTextSize(width / 10);
            String s = String.valueOf((int) (score + 0.5));
            canvas.drawText(s, width / 2 - scalePaint.measureText(s) / 2, (float) (rectfHeight * 2.7 / 7), scalePaint);
        }
    }

    private class DashBoardView extends View {

        public DashBoardView(Context context) {
            this(context, null);
        }

        public DashBoardView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            width = getMeasuredWidth() - CardTestView.this.getPaddingLeft() - CardTestView.this.getPaddingRight();
            //height = getMeasuredHeight() - CardTestView.this.getPaddingTop() - CardTestView.this.getPaddingBottom();
            height = (int) (width * 1.47);
            //******** 根据宽高获取矩形大小，确定仪表盘大小 ********
            rectfWidth = width * 264 / 375;
            rectfHeight = height * 314 / 667;
            int rectfMarginLeft = (width - rectfWidth) / 2;
            borderPaint.setStrokeWidth(width / 136);
            borderPaint.setColor(Color.parseColor("#66B5FF"));
            mRectf = new RectF(0, 0, rectfWidth, rectfHeight);
            mRectf.offset(rectfMarginLeft, 30);

            //******** 绘制边框 ********
            canvas.drawArc(mRectf, 160, 220, false, borderPaint);

            borderPaint.setColor(Color.WHITE);
            borderPaint.setStrokeWidth(width / 60);
            canvas.drawArc(mRectf, 161, currentAngle - 72, false, borderPaint);

            //******** 绘制刻度数值 ********
            scalePaint.setTextSize(width / 28);
            int startAngle = -108;
            for (int i = 0; i < scaleValue.length; i++) {
                scalePaint.setColor(Color.parseColor("#66B5FF"));
                if ((int) (score / 10) == i)
                    scalePaint.setColor(Color.WHITE);
                drawScaleValue(canvas, scaleValue[i], startAngle);
                if (i == 0) {
                    startAngle += 15;
                } else if (i < 5 || i == scaleValue.length - 2) {
                    startAngle += 20;
                } else {
                    startAngle += 25;
                }
            }
        }

        private void drawScaleValue(Canvas canvas, String text, float angle) {
            canvas.rotate(angle, width / 2, rectfHeight / 2 + height * 2 / 103);
            canvas.drawText(text, width / 2 - scalePaint.measureText(text) / 2,
                    height * 2 / 103 + 100, scalePaint);
            canvas.rotate(-angle, width / 2, rectfHeight / 2 + height * 2 / 103);
        }
    }
}
