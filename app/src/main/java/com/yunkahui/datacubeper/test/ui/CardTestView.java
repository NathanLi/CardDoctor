package com.yunkahui.datacubeper.test.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.CardTestItem;
import com.yunkahui.datacubeper.common.utils.DataUtils;

/**
 * Created by Administrator on 2018/4/28.
 */

public class CardTestView extends LinearLayout {

    private ImageView mImageViewIcon;
    private TextView mTextViewName;
    private TextView mTextViewBankName;
    private TextView mTextViewType;
    private TextView mTextViewBankCardNumber;
    private TextView mTextViewBankNameType;
    private TextView mTextViewKind1;
    private TextView mTextViewKind2;
    private TextView mTextViewKind3;
    private TextView mTextViewKind4;


    public CardTestView(Context context) {
        this(context, null);
    }

    public CardTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_test_bank_card_view, this);

        mImageViewIcon = findViewById(R.id.image_view_icon);
        mTextViewName = findViewById(R.id.text_view_name);
        mTextViewBankName = findViewById(R.id.text_view_bank_name);
        mTextViewType = findViewById(R.id.text_view_card_type);
        mTextViewBankCardNumber = findViewById(R.id.text_view_bank_card_number);
        mTextViewBankNameType = findViewById(R.id.text_view_bank_name_type);
        mTextViewKind1 = findViewById(R.id.text_view_kind_1);
        mTextViewKind2 = findViewById(R.id.text_view_kind_2);
        mTextViewKind3 = findViewById(R.id.text_view_kind_3);
        mTextViewKind4 = findViewById(R.id.text_view_kind_4);

    }

    public void setData(CardTestItem.Card card) {
        mImageViewIcon.setImageResource(DataUtils.getBankIconMap().containsKey(card.getBank_name()) ? DataUtils.getBankIconMap().get(card.getBank_name()) : R.mipmap.bank_other);
        mTextViewName.setText(card.getCardholder());
        mTextViewBankName.setText(card.getBank_name());
        mTextViewType.setText(card.getCard_level());
        mTextViewBankCardNumber.setText(card.getBankcard_num());
        mTextViewBankNameType.setText(card.getCard_name());
        mTextViewKind4.setText(card.getCard_brand());

        String card_crowd_sign = card.getCard_crowd_sign();
        switch (card_crowd_sign) {
            case "1": {
                mTextViewKind1.setText("低消费人群");
            }
            break;
            case "2": {
                mTextViewKind1.setText("中消费人群");
            }
            break;
            case "3": {
                mTextViewKind1.setText("高消费人群");
            }
            break;
            default: {
                mTextViewKind1.setVisibility(View.GONE);
            }
        }

        String card_active = card.getCard_active();
        switch (card_active) {
            case "1": {
                mTextViewKind2.setText("低活跃度");
            }
            break;
            case "2": {
                mTextViewKind2.setText("中低活跃度");
            }
            break;
            case "3": {
                mTextViewKind2.setText("中高活跃度");
            }
            break;
            case "4": {
                mTextViewKind2.setText("高活跃度");
            }
            break;
            default: {
                mTextViewKind2.setVisibility(View.GONE);
            }
        }

        String card_high_end = card.getCard_high_end();
        switch (card_high_end) {
            case "1": {
                mTextViewKind3.setText("高端客户");
            }
            break;
            default: {
                mTextViewKind3.setVisibility(View.GONE);
            }
        }


    }

}
