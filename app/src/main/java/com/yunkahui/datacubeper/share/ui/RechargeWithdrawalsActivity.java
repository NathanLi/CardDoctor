package com.yunkahui.datacubeper.share.ui;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseActivity;

/**
 * Created by YD1 on 2018/4/10
 */
public class RechargeWithdrawalsActivity extends BaseActivity {

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_withdrawals;
    }
}
