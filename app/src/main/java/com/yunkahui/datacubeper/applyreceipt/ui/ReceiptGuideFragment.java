package com.yunkahui.datacubeper.applyreceipt.ui;

import android.view.View;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.view.SimpleMenuItemView;

/**
 * Created by Administrator on 2018/6/26 0026
 */

public class ReceiptGuideFragment extends BaseFragment implements View.OnClickListener {

    private SimpleMenuItemView mBaseInfoView;
    private SimpleMenuItemView mSettleInfoView;
    private SimpleMenuItemView mIdCardView;
    private SimpleMenuItemView mHandIdCardView;
    private SimpleMenuItemView mBankCardView;
    private SimpleMenuItemView mHandBankCardView;

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        mBaseInfoView = view.findViewById(R.id.simple_menu_base_info);
        mSettleInfoView = view.findViewById(R.id.simple_menu_settle_info);
        mIdCardView = view.findViewById(R.id.simple_menu_id_card_photo);
        mHandIdCardView = view.findViewById(R.id.simple_menu_hand_id_card_photo);
        mBankCardView = view.findViewById(R.id.simple_menu_bank_card_photo);
        mHandBankCardView = view.findViewById(R.id.simple_menu_hand_bank_card_photo);

        mBaseInfoView.setOnClickListener(this);
        mSettleInfoView.setOnClickListener(this);
        mIdCardView.setOnClickListener(this);
        mHandIdCardView.setOnClickListener(this);
        mBankCardView.setOnClickListener(this);
        mHandBankCardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_receipt_guide;
    }
}
