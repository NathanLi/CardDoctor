package com.yunkahui.datacubeper.login.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseActivity;

public class ForgetPasswordActivity extends BaseActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        mToolbar=findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setImmersiveStatusBar(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }
}
