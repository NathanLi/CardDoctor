package com.yunkahui.datacubeper.test.ui.cardResult;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.utils.SizeUtils;
import com.yunkahui.datacubeper.common.view.chart.ChartAxis;
import com.yunkahui.datacubeper.common.view.chart.ChartLinearView;

import java.util.Arrays;

/**
 * 测评报告--产生利息
 */

public class TestInterestView extends LinearLayout {

    private TextView mTextViewTitle;
    private TextView mTextViewStatus;
    private TextView mTextViewInterest;
    private ChartLinearView mChartLinearView;

    public TestInterestView(Context context) {
        this(context, null);
    }

    public TestInterestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestInterestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_test_interest_view, this);

        mTextViewTitle = findViewById(R.id.text_view_title);
        mTextViewStatus = findViewById(R.id.text_view_status);
        mTextViewInterest = findViewById(R.id.text_view_interest);
        mChartLinearView = findViewById(R.id.chart_liner_view_interest);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TestInterestView);
        if (ta.hasValue(R.styleable.TestInterestView_interest_title)) {
            mTextViewTitle.setText(ta.getString(R.styleable.TestInterestView_interest_title));
        }
        ta.recycle();
        initChartLinearView();
    }

    public void initChartLinearView() {

        String[] times = {"12月", "1月", "2月", "3月", "4月", "5月"};
        float[] moneys = new float[6];
        moneys[0] = 200;
        moneys[1] = 1000;
        moneys[2] = 800;
        moneys[3] = 1300;
        moneys[4] = 1500;
        moneys[5] = 600;
        int base = 5;
        float space = 400;  //Y轴值间隔量

        mChartLinearView.addAxis(new ChartAxis.Builder(0, base, space).startOffset(SizeUtils.transFromDip(4))
                .unit("").auxiliary(true).create(), true);
        mChartLinearView.addAxis(new ChartAxis.Builder(Arrays.asList(times))
                .startPadding(-1).endPadding(-1)
                .startOffset(SizeUtils.transFromDip(5)).endOffset(SizeUtils.transFromDip(5))
                .create(), true);
        mChartLinearView.addPillars(moneys, Color.parseColor("#ff5900"), true);
        mChartLinearView.showAni();

    }


    public void setStatus(String status, int color) {
        mTextViewStatus.setText(status);
        mTextViewStatus.setBackgroundColor(color);
    }

    public void setInterest(String money) {
        mTextViewInterest.setText("月均消费金额：" + money + "元");
    }


}
