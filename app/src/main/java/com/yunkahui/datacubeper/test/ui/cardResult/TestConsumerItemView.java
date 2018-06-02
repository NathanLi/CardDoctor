package com.yunkahui.datacubeper.test.ui.cardResult;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.view.chart.ChartSingleView;

/**
 * 测评报告--消费趋势--item
 */

public class TestConsumerItemView extends LinearLayout {

    private TextView mTextViewMonth;
    private ChartSingleView mChartSingleView;
    private TextView mTextViewCount;
    private TextView mTextViewMoney;

    public TestConsumerItemView(Context context) {
        this(context, null);
    }

    public TestConsumerItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestConsumerItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_consumer_item_view, this);

        mTextViewMonth = findViewById(R.id.text_view_month);
        mChartSingleView = findViewById(R.id.chart_single_view);
        mTextViewCount = findViewById(R.id.text_view_count);
        mTextViewMoney = findViewById(R.id.text_view_money);

        mChartSingleView.setLightRatio(0);

    }

    public void setLightRatio(int ratio) {
        mChartSingleView.setLightRatio(ratio);
    }

    public void setMonth(String month) {
        mTextViewMonth.setText(month);
    }

    public void setLinearColor(int lightColor, int darkColor) {
        mChartSingleView.setLightColor(lightColor);
        mChartSingleView.setDarkColor(darkColor);
    }

    public void setCount(int count) {
        mTextViewCount.setText(count + "笔");
    }

    public void setMoney(String money) {
        mTextViewMoney.setText(money);
    }

    public void setConsumer(Consumer consumer) {
        mTextViewMonth.setText(consumer.getMonth());
        mChartSingleView.setLightColor(consumer.getLightColor());
        mChartSingleView.setDarkColor(consumer.getDarkColor());
        mChartSingleView.setLightRatio(consumer.getLightRatio());
        mTextViewCount.setText(consumer.getCount() + "笔");
        mTextViewMoney.setText(consumer.getMoney());
    }

    public static Consumer newInstance(){
        return new Consumer();
    }

    public static class Consumer {
        String month;
        int lightColor;
        int darkColor;
        float lightRatio;
        int count;
        String money;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public int getLightColor() {
            return lightColor;
        }

        public void setLightColor(int lightColor) {
            this.lightColor = lightColor;
        }

        public int getDarkColor() {
            return darkColor;
        }

        public void setDarkColor(int darkColor) {
            this.darkColor = darkColor;
        }

        public float getLightRatio() {
            return lightRatio;
        }

        public void setLightRatio(float lightRatio) {
            this.lightRatio = lightRatio;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }

}
