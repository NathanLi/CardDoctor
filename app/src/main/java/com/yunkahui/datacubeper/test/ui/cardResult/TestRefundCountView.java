package com.yunkahui.datacubeper.test.ui.cardResult;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.view.chart.ChartScoreView;
import com.yunkahui.datacubeper.common.view.chart.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/2.
 */

public class TestRefundCountView extends LinearLayout {

    private TextView mTextViewTitle;
    private TextView mTextViewTips;
    private TextView mTextViewStatus;
    private TextView mTextViewMessage;
    private ChartScoreView mChartScoreView;

    public TestRefundCountView(Context context) {
        this(context, null);
    }

    public TestRefundCountView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestRefundCountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_test_refund_count_view, this);
        mTextViewTitle = findViewById(R.id.text_view_title);
        mChartScoreView = findViewById(R.id.chart_score_view);
        mTextViewTips = findViewById(R.id.text_view_tips);
        mTextViewStatus = findViewById(R.id.text_view_status);
        mTextViewMessage = findViewById(R.id.text_view_message);
        initScoreView();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TestRefundCountView);
        if (ta.hasValue(R.styleable.TestRefundCountView_refund_title)) {
            mTextViewTitle.setText(ta.getString(R.styleable.TestRefundCountView_refund_title));
        }
        if (ta.hasValue(R.styleable.TestRefundCountView_refund_tips)) {
            mTextViewTips.setText(ta.getString(R.styleable.TestRefundCountView_refund_tips));
        }
        ta.recycle();
    }

    private void initScoreView() {
        List<Score> scores = new ArrayList<>();
        scores.add(new Score(Color.parseColor("#d7d7d7"), false, "0", "无"));
        scores.add(new Score(Color.parseColor("#7ed221"), false, "1", "优"));
        scores.add(new Score(Color.parseColor("#ffcc00"), false, "2～3", "中"));
        scores.add(new Score(Color.parseColor("#ff6116"), false, "4～5", "差"));
        scores.add(new Score(Color.parseColor("#ff3b30"), false, ">=6", "极差"));
        mChartScoreView.setmScores(scores);
    }

    public void setSelect(int i) {
        mChartScoreView.getScores().get(i).setSelect(true);
    }

    public void setStatus(String status){
        mTextViewStatus.setText(status);
    }

    public void setMessage(String message){
        mTextViewMessage.setText(message);
    }

}
