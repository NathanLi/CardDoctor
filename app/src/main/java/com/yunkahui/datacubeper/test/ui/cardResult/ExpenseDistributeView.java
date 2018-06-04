package com.yunkahui.datacubeper.test.ui.cardResult;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * 周末时间消费分布
 */

public class ExpenseDistributeView extends LinearLayout {

    private LinearLayout mLinearLayoutRoot;
    private TextView mTextViewTitle;
    private TextView mTextViewTips;

    public ExpenseDistributeView(Context context) {
        this(context, null);
    }

    public ExpenseDistributeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpenseDistributeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_expense_distribute_view, this);

        mLinearLayoutRoot = findViewById(R.id.linear_layout_root);
        mTextViewTitle = findViewById(R.id.text_view_title);
        mTextViewTips = findViewById(R.id.text_view_tips);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ExpenseDistributeView);
        if (ta.hasValue(R.styleable.ExpenseDistributeView_ed_title)) {
            mTextViewTitle.setText(ta.getString(R.styleable.ExpenseDistributeView_ed_title));
        }
        if (ta.hasValue(R.styleable.ExpenseDistributeView_ed_tips)) {
            mTextViewTips.setText(ta.getString(R.styleable.ExpenseDistributeView_ed_tips));
        }
        ta.recycle();
    }

    public void setData() {
        String[] months = new String[]{"5月", "4月", "3月", "2月", "1月", "12月"};
        String[] count = new String[]{"20笔", "50笔", "50笔", "20笔", "50笔", "50笔"};
        String[] moneys = new String[]{"1500.00", "3000.00", "3000.00", "1500.00", "3000.00", "3000.00"};

        for (int i = 0; i < 6; i++) {
            ExpenseDistributeItemView itemView = new ExpenseDistributeItemView(getContext());
            itemView.setMonth(months[i], R.drawable.bg_button_login_normal);
            itemView.setCount(count[i]);
            itemView.setMoney(moneys[i]);
            mLinearLayoutRoot.addView(itemView, mLinearLayoutRoot.getChildCount() - 1);
        }

    }


}
