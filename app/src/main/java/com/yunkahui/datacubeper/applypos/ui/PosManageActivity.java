package com.yunkahui.datacubeper.applypos.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
//POS管理
public class PosManageActivity extends AppCompatActivity implements IActivityStatusBar{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pos_manager);
        super.onCreate(savedInstanceState);
        setTitle("POS管理");
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
