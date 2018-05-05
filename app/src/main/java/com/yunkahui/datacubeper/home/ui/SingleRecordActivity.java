package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

public class SingleRecordActivity extends AppCompatActivity implements IActivityStatusBar {

    public static final String TYPE_RECHARGE="recharge";
    public static final String TYPE_WITHDRAW="withdraw";
    public static final String TYPE_WITHDRAW_FENRUNS="withdraw_fenruns";


    private TextView mTvType;
    private TextView mTvMoney;
    private TextView mTvTime;
    private TextView mTvStatus;
    private TextView mTvRemark;

    @Override
    public void initData() {
        mTvType.setText(getIntent().getStringExtra("action"));
        mTvMoney.setText(getIntent().getStringExtra("money"));
        mTvTime.setText(String.format(getResources().getString(R.string.trade_time_format), getIntent().getStringExtra("time")));
        mTvStatus.setText(String.format(getResources().getString(R.string.trade_status_format), getIntent().getStringExtra("status")));
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
        mTvRemark = findViewById(R.id.tv_remark);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
