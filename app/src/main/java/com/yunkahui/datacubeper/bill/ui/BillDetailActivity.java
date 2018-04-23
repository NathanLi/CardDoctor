package com.yunkahui.datacubeper.bill.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

/**
 * @author WYF on 2018/4/23/023.
 */
public class BillDetailActivity extends AppCompatActivity implements IActivityStatusBar {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bill_card);
        super.onCreate(savedInstanceState);
        setTitle("交易管理");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }
}
