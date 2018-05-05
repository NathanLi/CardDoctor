package com.yunkahui.datacubeper.home.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

public class SingleRecordActivity extends AppCompatActivity implements IActivityStatusBar {

    private TextView mTvType;
    private TextView mTvMoney;
    private TextView mTvTime;
    private TextView mTvStatus;
    private TextView mTvRemarks;

    @Override
    public void initData() {
        mTvType.setText(getIntent().getStringExtra("action"));
        mTvMoney.setText(getIntent().getStringExtra("money"));
        mTvTime.setText(String.format(getResources().getString(R.string.trade_time_format), getIntent().getStringExtra("time")));
        String status = String.format(getResources().getString(R.string.trade_status_format), getIntent().getStringExtra("status"));
        String remarks = getIntent().getStringExtra("remarks");
        String remarksSub = remarks.substring(remarks.lastIndexOf("：") + 1);
        mTvRemarks.setText("备        注：" + remarksSub);
        if (remarks.contains("失败"))
            mTvRemarks.setTextColor(Color.RED);
        mTvStatus.setText(status);
        if (status.contains("失败"))
            mTvStatus.setTextColor(Color.RED);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_single_record);
        super.onCreate(savedInstanceState);
        setTitle("交易详情");
    }

    @Override
    public void initView() {
        mTvType = findViewById(R.id.tv_type);
        mTvMoney = findViewById(R.id.tv_money);
        mTvTime = findViewById(R.id.tv_time);
        mTvStatus = findViewById(R.id.tv_status);
        mTvRemarks = findViewById(R.id.tv_remarks);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
