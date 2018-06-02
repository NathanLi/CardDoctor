package com.yunkahui.datacubeper.test.ui.cardResult;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.view.chart.ChartCircleView;

/**
 * 套现风险评测Item
 */

public class RiskTestView extends LinearLayout {

    private TextView mTextViewTitle;
    private ChartCircleView mChartCircleView;
    private LinearLayout mLinearLayoutResult;
    private TextView mTextViewResultTitle;
    private TextView mTextViewResultContent;
    private TextView mTextViewTips;

    public RiskTestView(Context context) {
        this(context, null);
    }

    public RiskTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RiskTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_risk_test_view, this);

        mTextViewTitle = findViewById(R.id.text_view_title);
        mChartCircleView = findViewById(R.id.chart_circle_view);
        mLinearLayoutResult = findViewById(R.id.linear_layout_result);
        mTextViewResultTitle = findViewById(R.id.text_view_result_title);
        mTextViewResultContent = findViewById(R.id.text_view_result_content);
        mTextViewTips = findViewById(R.id.text_view_tips);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RiskTestView);
        if (ta.hasValue(R.styleable.RiskTestView_risk_title)) {
            mTextViewTitle.setText(ta.getString(R.styleable.RiskTestView_risk_title));
        }
        if (ta.hasValue(R.styleable.RiskTestView_risk_tips)) {
            mTextViewTips.setText(ta.getString(R.styleable.RiskTestView_risk_tips));
        }
        if (ta.hasValue(R.styleable.RiskTestView_risk_result_background)) {
            mLinearLayoutResult.setBackgroundResource(ta.getResourceId(R.styleable.RiskTestView_risk_result_background, 0));
        }

        ta.recycle();

    }


    public void setCircleColor(int color) {
        mChartCircleView.setLightColor(color);
    }

    /**
     * 0-1
     */
    public void setCircleRadio(float radio) {
        mChartCircleView.setLightRatio(radio);
        mChartCircleView.showAni();
    }

    public void setTitle(String title) {
        mTextViewTitle.setText(title);
    }

    public void setResultTitle(String title) {
        mTextViewResultTitle.setText(title);
    }

    public void setResultContent(String text) {
        mTextViewResultContent.setText(text);
    }

    public void setCenterText(CharSequence text) {
        mChartCircleView.setCenterTitle(text);
    }

    public void setCenterTextColor(int color) {
        mChartCircleView.setCenterColor(color);
    }

    public void setCenterTextSize(float size) {
        mChartCircleView.setCenterSize(size);
    }

}
