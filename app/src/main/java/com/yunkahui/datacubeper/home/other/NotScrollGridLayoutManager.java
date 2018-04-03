package com.yunkahui.datacubeper.home.other;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/3/27
 */

public class NotScrollGridLayoutManager extends GridLayoutManager {

    private boolean mIsScrollEnabled = false;

    public NotScrollGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public NotScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public NotScrollGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollVertically() {

        return mIsScrollEnabled && super.canScrollVertically();
    }
}
