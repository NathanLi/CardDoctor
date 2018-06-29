package com.yunkahui.datacubeper.applyreceipt.ui;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

/**
 * Created by Administrator on 2018/6/25 0025
 */

public class ReceiptActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_receipt);
        super.onCreate(savedInstanceState);
        setTitle("收付款");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
