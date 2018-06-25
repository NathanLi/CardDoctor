package com.yunkahui.datacubeper.applyreceipt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.view.SimpleMenuItemView;

/**
 * Created by Administrator on 2018/6/25 0025
 */

public class ReceiptActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    public final int RESULT_CODE_UPDATE = 1001;

    private ProgressApplyReceiptView mReceiptView;
    private SimpleMenuItemView mBaseInfoView;
    private SimpleMenuItemView mSettleInfoView;
    private SimpleMenuItemView mIdCardView;
    private SimpleMenuItemView mHandIdCardView;
    private SimpleMenuItemView mBankCardView;
    private SimpleMenuItemView mHandBankCardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_receipt);
        super.onCreate(savedInstanceState);
        setTitle("申请开通收付款");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mReceiptView = findViewById(R.id.progress_bar_apply_receipt);
        mBaseInfoView = findViewById(R.id.simple_menu_base_info);
        mSettleInfoView = findViewById(R.id.simple_menu_settle_info);
        mIdCardView = findViewById(R.id.simple_menu_id_card_photo);
        mHandIdCardView = findViewById(R.id.simple_menu_hand_id_card_photo);
        mBankCardView = findViewById(R.id.simple_menu_bank_card_photo);
        mHandBankCardView = findViewById(R.id.simple_menu_hand_bank_card_photo);

        mReceiptView.setOnClickListener(this);
        mBaseInfoView.setOnClickListener(this);
        mSettleInfoView.setOnClickListener(this);
        mIdCardView.setOnClickListener(this);
        mHandIdCardView.setOnClickListener(this);
        mBankCardView.setOnClickListener(this);
        mHandBankCardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simple_menu_base_info:
                startActivity(new Intent(this, BaseReceiptInfoActivity.class));
                break;
            case R.id.simple_menu_settle_info:
                startActivity(new Intent(this, SettleReceiptInfoActivity.class));
                break;
            case R.id.simple_menu_id_card_photo:
                startActivityForResult(new Intent(this, UploadPhotoActivity.class).putExtra("type", UploadPhotoActivity.TYPE_ID_CARD), RESULT_CODE_UPDATE);
                break;
            case R.id.simple_menu_hand_id_card_photo:
                startActivityForResult(new Intent(this, UploadPhotoActivity.class).putExtra("type", UploadPhotoActivity.TYPE_HAND_ID_CARD), RESULT_CODE_UPDATE);
                break;
            case R.id.simple_menu_bank_card_photo:
                startActivityForResult(new Intent(this, UploadPhotoActivity.class).putExtra("type", UploadPhotoActivity.TYPE_BANK_CARD), RESULT_CODE_UPDATE);
                break;
            case R.id.simple_menu_hand_bank_card_photo:
                startActivityForResult(new Intent(this, UploadPhotoActivity.class).putExtra("type", UploadPhotoActivity.TYPE_HAND_BANK_CARD), RESULT_CODE_UPDATE);
                break;
        }
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
