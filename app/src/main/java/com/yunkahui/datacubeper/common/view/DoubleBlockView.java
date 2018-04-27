package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * Created by YD1 on 2018/4/10
 */
public class DoubleBlockView extends LinearLayout {

    private TextView mTvLeftName;
    private TextView mTvRightName;
    private TextView mTvLeftNum;
    private TextView mTvRightNum;
    private LinearLayout mLeftBlock;
    private LinearLayout mRightBlock;

    public DoubleBlockView(Context context) {
        this(context, null);
    }

    public DoubleBlockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleBlockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_double_block, this);

        mTvLeftName = view.findViewById(R.id.tv_left_title);
        mTvRightName = view.findViewById(R.id.tv_right_title);
        mTvLeftNum = view.findViewById(R.id.tv_left_value);
        mTvRightNum = view.findViewById(R.id.tv_right_value);
        mLeftBlock = view.findViewById(R.id.ll_left_block);
        mRightBlock = view.findViewById(R.id.ll_right_block);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DoubleBlockView, defStyleAttr, 0);
        String leftName = a.getString(R.styleable.DoubleBlockView_leftTitle);
        String rightName = a.getString(R.styleable.DoubleBlockView_rightTitle);
        mTvLeftName.setText(leftName);
        mTvRightName.setText(rightName);
        a.recycle();
    }

    public DoubleBlockView setOnLeftBlockClickListener(OnClickListener l) {
        mLeftBlock.setOnClickListener(l);
        return this;
    }

    public DoubleBlockView setOnRightBlockClickListener(OnClickListener l) {
        mRightBlock.setOnClickListener(l);
        return this;
    }

    public DoubleBlockView setLeftTitle(String value) {
        mTvLeftName.setText(value);
        return this;
    }

    public DoubleBlockView setRightTitle(String value) {
        mTvRightName.setText(value);
        return this;
    }

    public DoubleBlockView setLeftValue(String value) {
        mTvLeftNum.setText(value);
        return this;
    }

    public DoubleBlockView setRightValue(String value) {
        mTvRightNum.setText(value);
        return this;
    }
}
