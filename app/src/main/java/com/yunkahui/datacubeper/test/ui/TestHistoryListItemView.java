package com.yunkahui.datacubeper.test.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * Created by Administrator on 2018/4/24.
 */

public class TestHistoryListItemView extends RelativeLayout {

    private TextView mTextViewTitle;
    private TextView mTextViewTime;
    private TextView mTextViewResult;


    public TestHistoryListItemView(Context context) {
        this(context, null);
    }

    public TestHistoryListItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestHistoryListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_list_item_test_history, this);
        mTextViewTitle = findViewById(R.id.text_view_title);
        mTextViewTime = findViewById(R.id.text_view_time);
        mTextViewResult = findViewById(R.id.text_view_result);
    }
}
