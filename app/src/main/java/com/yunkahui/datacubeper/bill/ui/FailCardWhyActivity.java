package com.yunkahui.datacubeper.bill.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

public class FailCardWhyActivity extends AppCompatActivity implements IActivityStatusBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fail_card_why);
        super.onCreate(savedInstanceState);
        setTitle("交易关闭原因");

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
