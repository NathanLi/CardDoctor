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

    private TextView mTvLeftTitle;
    private TextView mTvRightTitle;
    private TextView mTvLeftValue;
    private TextView mTvRightValue;
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
        mTvLeftTitle = view.findViewById(R.id.tv_left_title);
        mTvRightTitle = view.findViewById(R.id.tv_right_title);
        mTvLeftValue = view.findViewById(R.id.tv_left_value);
        mTvRightValue = view.findViewById(R.id.tv_right_value);
        mLeftBlock = view.findViewById(R.id.ll_left_block);
        mRightBlock = view.findViewById(R.id.ll_right_block);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DoubleBlockView, defStyleAttr, 0);
        if (a.hasValue(R.styleable.DoubleBlockView_leftTitle))
            mTvLeftTitle.setText(a.getString(R.styleable.DoubleBlockView_leftTitle));
        if (a.hasValue(R.styleable.DoubleBlockView_rightTitle))
            mTvRightTitle.setText(a.getString(R.styleable.DoubleBlockView_rightTitle));
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
        mTvLeftTitle.setText(value);
        return this;
    }

    public DoubleBlockView setRightTitle(String value) {
        mTvRightTitle.setText(value);
        return this;
    }

    public DoubleBlockView setLeftValue(String value) {
        mTvLeftValue.setText(value);
        return this;
    }

    public DoubleBlockView setRightValue(String value) {
        mTvRightValue.setText(value);
        return this;
    }
}
