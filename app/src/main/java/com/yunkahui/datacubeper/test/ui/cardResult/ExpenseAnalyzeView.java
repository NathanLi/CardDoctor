package com.yunkahui.datacubeper.test.ui.cardResult;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.utils.SizeUtils;
import com.yunkahui.datacubeper.common.view.chart.ChartAxis;
import com.yunkahui.datacubeper.common.view.chart.ChartLinearView;

import java.util.Arrays;

/**
 * 节假日消费分析
 */

public class ExpenseAnalyzeView extends LinearLayout {

    private TextView mTextViewTitle;
    private ChartLinearView mChartLinearView;
    private TextView mTextViewTips;

    public ExpenseAnalyzeView(Context context) {
        this(context, null);
    }

    public ExpenseAnalyzeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpenseAnalyzeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_expense_analyze_view, this);

        mTextViewTitle = findViewById(R.id.text_view_title);
        mChartLinearView = findViewById(R.id.chart_liner_view);
        mTextViewTips = findViewById(R.id.text_view_tips);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ExpenseAnalyzeView);
        if (ta.hasValue(R.styleable.ExpenseAnalyzeView_ea_title)) {
            mTextViewTitle.setText(ta.getString(R.styleable.ExpenseAnalyzeView_ea_title));
        }
        if (ta.hasValue(R.styleable.ExpenseAnalyzeView_ea_tips)) {
            mTextViewTips.setText(ta.getString(R.styleable.ExpenseAnalyzeView_ea_tips));
        }
        ta.recycle();

        initChartLinearView();
    }

    public void initChartLinearView() {

        String[] times = {"元旦", "春节", "清明", "劳动节", "端午", "中秋","国庆"};
        float[] moneys = new float[7];
        moneys[0] = 10;
        moneys[1] = 18;
        moneys[2] = 8;
        moneys[3] = 10;
        moneys[4] = 20;
        moneys[5] = 8;
        moneys[6] = 12;
        int base = 5;
        float space = 4;  //Y轴值间隔量

        float[] moneys2 = new float[7];
        moneys2[0] = 8;
        moneys2[1] = 6;
        moneys2[2] = 18;
        moneys2[3] = 19;
        moneys2[4] = 10;
        moneys2[5] = 5;
        moneys2[6] = 7;

        mChartLinearView.addAxis(new ChartAxis.Builder(0, base, space).startOffset(SizeUtils.transFromDip(4))
                .unit("k").auxiliary(true).create(), true);
        mChartLinearView.addAxis(new ChartAxis.Builder(0, base, space).startOffset(SizeUtils.transFromDip(4))
                .unit("笔").auxiliary(true).create(), false);

        mChartLinearView.addAxis(new ChartAxis.Builder(Arrays.asList(times))
                .startPadding(-1).endPadding(-1)
                .startOffset(SizeUtils.transFromDip(5)).endOffset(SizeUtils.transFromDip(5))
                .create(), true);
        mChartLinearView.addAxis(new ChartAxis.Builder(Arrays.asList(times))
                .startPadding(-1).endPadding(-1)
                .startOffset(SizeUtils.transFromDip(5)).endOffset(SizeUtils.transFromDip(5))
                .create(), false);

        mChartLinearView.addPillars(moneys, Color.parseColor("#5ac7fa"), true);
        mChartLinearView.addPillars(moneys2, Color.parseColor("#ff5900"), false);

        mChartLinearView.showAni();

    }

}
