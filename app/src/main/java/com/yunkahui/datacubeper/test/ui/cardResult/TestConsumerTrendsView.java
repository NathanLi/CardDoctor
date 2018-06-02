package com.yunkahui.datacubeper.test.ui.cardResult;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

import java.util.List;

/**
 * 测评报告-- 消费趋势
 */

public class TestConsumerTrendsView extends LinearLayout {

    private TextView mTextViewTitle;
    private TextView mTextViewMonthMoney;
    private TextView mTextViewCount;
    private TextView mTextViewAverageMoney;
    private LinearLayout mLinearLayoutConsumer;
    private TextView mTextViewTips;

    public TestConsumerTrendsView(Context context) {
        this(context, null);
    }

    public TestConsumerTrendsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestConsumerTrendsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_test_consumer_trends_view, this);
        mTextViewTitle = findViewById(R.id.text_view_title);
        mTextViewMonthMoney = findViewById(R.id.text_view_month_money);
        mTextViewCount = findViewById(R.id.text_view_month_count);
        mTextViewAverageMoney = findViewById(R.id.text_view_average_money);
        mLinearLayoutConsumer = findViewById(R.id.linear_layout_consumer);
        mTextViewTips = findViewById(R.id.text_view_tips);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TestConsumerTrendsView);
        if (ta.hasValue(R.styleable.TestConsumerTrendsView_consumer_title)) {
            mTextViewTitle.setText(ta.getString(R.styleable.TestConsumerTrendsView_consumer_title));
        }
        if (ta.hasValue(R.styleable.TestConsumerTrendsView_consumer_tips)) {
            mTextViewTips.setText(ta.getString(R.styleable.TestConsumerTrendsView_consumer_tips));
        }
        ta.recycle();

    }

    public void setTextViewMonthMoney(String money) {
        mTextViewMonthMoney.setText("月均消费金额：" + money + "元");
    }

    public void setCount(int count) {
        mTextViewCount.setText("月均消费笔数：" + count + "笔");
    }

    public void setAverageMoney(String money) {
        mTextViewAverageMoney.setText(money + "元\n笔均交易金额");
    }

    public void setConsumerData(List<TestConsumerItemView.Consumer> consumerList){
        LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        for (int i=0;i<consumerList.size();i++){
            TestConsumerItemView itemView=new TestConsumerItemView(getContext());
            itemView.setLayoutParams(params);
            itemView.setConsumer(consumerList.get(i));
            mLinearLayoutConsumer.addView(itemView);
        }

    }


}
