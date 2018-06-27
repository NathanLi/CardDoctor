package com.yunkahui.datacubeper.applyreceipt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.view.SimpleItemView;

/**
 * Created by Administrator on 2018/6/25 0025
 */

public class QuickPayActivity extends AppCompatActivity implements IActivityStatusBar {

    private SimpleItemView mStvOrderNum;
    private SimpleItemView mStvOrderTitle;
    private SimpleItemView mStvOrderDeadline;
    private SimpleItemView mStvOrderDesc;
    private TextView mTvAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_quick_pay);
        super.onCreate(savedInstanceState);
        setTitle("快捷支付");
    }

    @Override
    public void initData() {
        mTvAmount.setText("¥ 999");
        mStvOrderNum.setTitle("订单号");
        mStvOrderTitle.setTitle("订单标题");
        mStvOrderDeadline.setTitle("订单有效时间");
        mStvOrderDesc.setTitle("订单描述");

        mStvOrderNum.setValue("5444643165");
        mStvOrderTitle.setValue("交易");
        mStvOrderDeadline.setValue("5446843");
        mStvOrderDesc.setValue("-");
    }

    @Override
    public void initView() {
        mTvAmount = findViewById(R.id.tv_amount);
        mStvOrderNum = findViewById(R.id.stv_order_num);
        mStvOrderTitle = findViewById(R.id.stv_order_title);
        mStvOrderDeadline = findViewById(R.id.stv_order_deadline);
        mStvOrderDesc = findViewById(R.id.stv_order_desc);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
