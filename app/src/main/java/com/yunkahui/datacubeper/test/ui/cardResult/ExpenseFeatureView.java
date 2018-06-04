package com.yunkahui.datacubeper.test.ui.cardResult;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * Created by Administrator on 2018/6/4.
 */

public class ExpenseFeatureView extends LinearLayout {

    private TextView mTextViewTitle;
    private TextView mTextViewSubResult1;
    private TextView mTextViewSubResult2;
    private TextView mTextViewTips;

    public ExpenseFeatureView(Context context) {
        this(context, null);
    }

    public ExpenseFeatureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpenseFeatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_expense_feature_view, this);
        mTextViewTitle = findViewById(R.id.text_view_title);
        mTextViewSubResult1 = findViewById(R.id.text_view_sub_title_1_result);
        mTextViewSubResult2 = findViewById(R.id.text_view_sub_title_2_result);
        mTextViewTips = findViewById(R.id.text_view_tips);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ExpenseFeatureView);
        if (ta.hasValue(R.styleable.ExpenseFeatureView_ef_title)) {
            mTextViewTitle.setText(ta.getString(R.styleable.ExpenseFeatureView_ef_title));
        }
        if (ta.hasValue(R.styleable.ExpenseFeatureView_ef_tips)) {
            mTextViewTips.setText(ta.getString(R.styleable.ExpenseFeatureView_ef_tips));
        }
        ta.recycle();
    }

    public void setResult1(String result1) {
        mTextViewSubResult1.setText(result1);
    }

    public void setResult2(String result2) {
        mTextViewSubResult2.setText(result2);
    }

}
