package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

public class SingleRecordActivity extends AppCompatActivity implements IActivityStatusBar {

    private TextView mTvType;
    private TextView mTvMoney;
    private TextView mTvTime;
    private TextView mTvPoundage;
    private TextView mTvRemark;

    @Override
    public void initData() {

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
        mTvPoundage = findViewById(R.id.tv_poundage);
        mTvRemark = findViewById(R.id.tv_remark);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
