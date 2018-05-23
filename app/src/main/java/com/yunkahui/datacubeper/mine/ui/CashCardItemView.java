package com.yunkahui.datacubeper.mine.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BankCard;
import com.yunkahui.datacubeper.common.utils.DataUtils;

/**
 * Created by Administrator on 2018/4/17.
 */

public class CashCardItemView extends LinearLayout {

    private ImageView mImageViewIcon;
    private TextView mTextViewBankName;
    private TextView mTextViewName;
    private TextView mTextViewPhone;


    public CashCardItemView(Context context) {
        this(context, null);
    }

    public CashCardItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CashCardItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_cash_card_item_view, this);

        mImageViewIcon = findViewById(R.id.image_view_icon);
        mTextViewBankName = findViewById(R.id.text_view_bank_name);
        mTextViewName = findViewById(R.id.text_view_name);
        mTextViewPhone = findViewById(R.id.text_view_phone);

    }


    public void setData(BankCard bankCard) {
        int iconResId = DataUtils.getBankIconForName(bankCard.getBankcard_name());
        String cardNum = bankCard.getBankcard_num();
        mImageViewIcon.setImageResource(iconResId);
        mTextViewBankName.setText(bankCard.getBankcard_name() + "[" + cardNum.substring(cardNum.length() - 4, cardNum.length()) + "]");
        mTextViewName.setText(bankCard.getCardholder());
        mTextViewPhone.setText(bankCard.getBankcard_tel());
    }


}
