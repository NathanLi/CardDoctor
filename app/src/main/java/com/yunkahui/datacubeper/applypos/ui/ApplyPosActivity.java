package com.yunkahui.datacubeper.applypos.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
//申请POS
public class ApplyPosActivity extends AppCompatActivity implements IActivityStatusBar{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_apply_pos);
        super.onCreate(savedInstanceState);
        setTitle("申请POS");

    }

    @Override
    public void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new ApplyPosSecondFragment()).commit();
    }

    @Override
    public void initView() {

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
