package com.yunkahui.datacubeper.common.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2018/5/17.
 */

public class CommonItemDecoration extends RecyclerView.ItemDecoration {

    private int top;
    private int left;
    private int right;
    private int bottom;

    public CommonItemDecoration(int bottom) {
        this.bottom = bottom;
    }

    public CommonItemDecoration(int top, int bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    public CommonItemDecoration(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        outRect.top = top;
        outRect.left = left;
        outRect.right = right;
        outRect.bottom = bottom;
    }
}
