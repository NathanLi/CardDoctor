package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * Created by Administrator on 2018/6/26 0026
 */

public class SimpleItemView extends LinearLayout {

    private TextView mTvLeft;
    private TextView mTvRight;

    public SimpleItemView(Context context) {
        this(context,null);
    }

    public SimpleItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.layout_simple_item_view,this);
        mTvLeft = findViewById(R.id.tv_left);
        mTvRight = findViewById(R.id.tv_right);
    }

    public void setTitle(String title) {
        mTvLeft.setText(title);
    }

    public void setValue(String value) {
        mTvRight.setText(value);
    }
}
