package com.yunkahui.datacubeper.test.ui.cardResult;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * Created by Administrator on 2018/6/1.
 */

public class TestResultCardView extends LinearLayout {

    private ImageView mImageViewIcon;
    private TextView mTextViewCardName;
    private TextView mTextViewName;
    private TextView mTextViewCardNumber;
    private TextView mTextViewLines;
    private TextView mTextViewBill;


    public TestResultCardView(Context context) {
        this(context, null);
    }

    public TestResultCardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestResultCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_test_result_card_view, this);

        mImageViewIcon = findViewById(R.id.image_view_icon);
        mTextViewCardName = findViewById(R.id.text_view_bank_card_name);
        mTextViewName = findViewById(R.id.text_view_name);
        mTextViewCardNumber = findViewById(R.id.text_view_card_number);
        mTextViewLines = findViewById(R.id.text_view_lines);
        mTextViewBill = findViewById(R.id.text_view_bill);

    }


    public void setIcon(int res) {
        mImageViewIcon.setImageResource(res);
    }

    public void setBankCardName(String name) {
        mTextViewCardName.setText(name);
    }

    public void setName(String name) {
        mTextViewName.setText(name);
    }

    public void setCardNumber(String number) {
        mTextViewCardNumber.setText(number);
    }

    public void setTextViewLines(String money) {
        mTextViewLines.setText(Html.fromHtml("<font><big>" + money + "</big></font><br/>固定额度"));
    }

    public void setTextViewBill(String money) {
        mTextViewBill.setText(Html.fromHtml("<font><big>" + money + "</big></font><br/>本期账单"));
    }

}
