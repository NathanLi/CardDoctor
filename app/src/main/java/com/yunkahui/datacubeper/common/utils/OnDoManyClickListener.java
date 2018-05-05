package com.yunkahui.datacubeper.common.utils;

import android.view.View;

/**
 * Created by Administrator on 2018/5/5.
 */

public abstract class OnDoManyClickListener implements View.OnClickListener {

    private final int TIME_INTERVAL = 1000;
    private long mClickTime;

    public abstract void onDoManyClick(View view);

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - mClickTime >= TIME_INTERVAL) {
            mClickTime = System.currentTimeMillis();
            onDoManyClick(v);
        }
    }
}
