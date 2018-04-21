package com.yunkahui.datacubeper.common.view.chart;

import android.content.Context;
import android.graphics.Paint;

/**
 * Created by clint on 2018/1/3.
 */

public class ChartUtil {

    public static float dpToPx(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        return Math.abs(fm.top) + Math.abs(fm.bottom);
//        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }

    public static int getFontBaseLine(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        return Math.abs(fm.top);
    }
}
