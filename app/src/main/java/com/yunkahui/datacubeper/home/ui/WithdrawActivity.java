package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

/**
 * Created by YD1 on 2018/4/10
 */
public class WithdrawActivity extends AppCompatActivity implements IActivityStatusBar {

    private TextView mTvUserBalance;

    @Override
    public void initData() {
        if (getIntent().getStringExtra("money") != null) {
            mTvUserBalance.setText(getIntent().getStringExtra("money"));
        }
    }

    @Override
    public void initView() {
        mTvUserBalance = findViewById(R.id.tv_user_balance);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_withdraw);
        super.onCreate(savedInstanceState);
        setTitle("余额提现");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
