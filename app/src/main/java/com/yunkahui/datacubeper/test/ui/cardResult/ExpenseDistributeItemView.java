package com.yunkahui.datacubeper.test.ui.cardResult;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * 周末时间消费分布--item
 */

public class ExpenseDistributeItemView extends RelativeLayout {

    private TextView mTextViewMonth;
    private TextView mTextViewCount;
    private TextView mTextViewMoney;

    public ExpenseDistributeItemView(Context context) {
        this(context, null);
    }

    public ExpenseDistributeItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpenseDistributeItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_expense_distribute_item_view, this);

        mTextViewMonth = findViewById(R.id.text_view_month);
        mTextViewCount = findViewById(R.id.text_view_count);
        mTextViewMoney = findViewById(R.id.text_view_money);

    }

    public void setMonth(String month, int bgRes) {
        mTextViewMonth.setText(month);
        mTextViewMonth.setBackgroundResource(bgRes);
    }

    public void setCount(String count) {
        mTextViewCount.setText(count);
    }

    public void setMoney(String monty) {
        mTextViewMoney.setText(monty);
    }

}
