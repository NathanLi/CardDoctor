package com.yunkahui.datacubeper.share.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

/**
 * Created by YD1 on 2018/4/10
 */
public class ProfitWithdrawalsActivity extends AppCompatActivity implements IActivityStatusBar{

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_profit_withdrawals);
        super.onCreate(savedInstanceState);
        setTitle("分润提现");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
