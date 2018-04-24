package com.yunkahui.datacubeper.common.utils;

import android.graphics.drawable.GradientDrawable;

public class TintUtil {

    public static GradientDrawable createColorShape(int color, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
        drawable.setColor(color);
        return drawable;
    }
}
