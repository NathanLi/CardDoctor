package com.yunkahui.datacubeper.test.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.yunkahui.datacubeper.R;

/**
 * Created by Administrator on 2018/4/24.
 */

public class TestHistoryListItemView extends RelativeLayout {

    public TestHistoryListItemView(Context context) {
        this(context,null);
    }

    public TestHistoryListItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestHistoryListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_lsit_item_test_history,this);
    }
}
