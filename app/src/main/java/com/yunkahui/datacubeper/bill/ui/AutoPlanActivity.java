package com.yunkahui.datacubeper.bill.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

public class AutoPlanActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private TextView mTvRepayDate;
    private EditText mEtInputAmount;
    private EditText mEtInputTimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_auto_plan);
        super.onCreate(savedInstanceState);
        setTitle("自动规划");
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
        mTvRepayDate = findViewById(R.id.tv_repay_date);
        mEtInputAmount = findViewById(R.id.et_input_amount);
        mEtInputTimes = findViewById(R.id.et_input_times);
        findViewById(R.id.rl_select_date).setOnClickListener(this);
        findViewById(R.id.tv_to_plan).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_select_date:
                break;
            case R.id.tv_to_plan:
                break;
        }
    }

}
