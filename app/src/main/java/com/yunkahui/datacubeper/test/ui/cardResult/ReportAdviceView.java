package com.yunkahui.datacubeper.test.ui.cardResult;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * Created by Administrator on 2018/6/4.
 */

public class ReportAdviceView extends LinearLayout {

    private TextView mTextViewTitle;
    private TextView mTextViewTips;
    private Button mButtonNext1;
    private Button mButtonNext2;

    public ReportAdviceView(Context context) {
        this(context, null);
    }

    public ReportAdviceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReportAdviceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_report_advice_view, this);
        mTextViewTitle = findViewById(R.id.text_view_title);
        mTextViewTips = findViewById(R.id.text_view_tips);
        mButtonNext1 = findViewById(R.id.button_next_1);
        mButtonNext2 = findViewById(R.id.button_next_2);

        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.ReportAdviceView);
        if (ta.hasValue(R.styleable.ReportAdviceView_ra_title)) {
            mTextViewTitle.setText(ta.getString(R.styleable.ReportAdviceView_ra_title));
        }
        if (ta.hasValue(R.styleable.ReportAdviceView_ra_tips)) {
            mTextViewTips.setText(ta.getString(R.styleable.ReportAdviceView_ra_tips));
        }
        ta.recycle();
    }
}
