package com.yunkahui.datacubeper.applyreceipt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

/**
 * Created by Administrator on 2018/6/25 0025
 */

public class ReceiptGuideActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    public static final int START_EXAMINE = 998;
    public static final int RESULT_EXAMINE_SUCCESS = 999;
    public static final int RESULT_EXAMINE_FAIL = 1000;
    public final int RESULT_CODE_UPDATE = 1001;

    private ProgressApplyReceiptView mReceiptView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_receipt_guide);
        super.onCreate(savedInstanceState);
        setTitle("申请开通收付款");
    }

    @Override
    public void initData() {
        int type = getIntent().getIntExtra("type", START_EXAMINE);
        if (type == START_EXAMINE) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ReceiptGuideFragment()).commit();
        } else if (type == RESULT_EXAMINE_SUCCESS) {
            ExamineResultFragment examineResultFragment = new ExamineResultFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", ExamineResultFragment.EXAMINE_SUCCESS);
            examineResultFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, examineResultFragment).commit();
        } else {
            ExamineResultFragment examineResultFragment = new ExamineResultFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", ExamineResultFragment.EXAMINE_FAIL);
            examineResultFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, examineResultFragment).commit();
        }
    }

    @Override
    public void initView() {
        mReceiptView = findViewById(R.id.progress_bar_apply_receipt);
        mReceiptView.setOnClickListener(this);
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
