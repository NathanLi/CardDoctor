package com.yunkahui.datacubeper.login.ui;

import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseActivity;
import com.yunkahui.datacubeper.base.IActivityBase;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

public class RegisterActivity extends AppCompatActivity implements IActivityStatusBar {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);

        LoadingViewDialog.getInstance().show(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new RegisterFirstFragment()).commit();
    }

    /**
     * 跳转到注册第二步
     */
    public void registerSecond(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new RegisterSecondFragment()).commitNowAllowingStateLoss();
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
