package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

public class ScanResultActivity extends AppCompatActivity implements IActivityStatusBar {

    private TextView mTvTitle;
    private TextView mTvTip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_scan_result);
        super.onCreate(savedInstanceState);
        setTitle("提示");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void initData() {
        mTvTitle.setText("已扫描到以下内容");
        mTvTip.setText(getIntent().getStringExtra("result"));
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.text_view_result);
        mTvTip = findViewById(R.id.text_view_money);
    }
}
