package com.yunkahui.datacubeper.mine.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

public class PersonalInfoActivity extends AppCompatActivity implements IActivityStatusBar{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_personal_info);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
