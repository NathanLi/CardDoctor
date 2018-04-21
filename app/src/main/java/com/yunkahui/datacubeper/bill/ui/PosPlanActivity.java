package com.yunkahui.datacubeper.bill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

public class PosPlanActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private TextView mTvRepayDate;
    private EditText mEtInputAmount;
    private EditText mEtInputTimes;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_pos_plan);
        super.onCreate(savedInstanceState);
        setTitle("POS规划");
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
        mRecyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.rl_select_date).setOnClickListener(this);
        findViewById(R.id.tv_to_plan).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_select_date:
                startActivity(new Intent(this, TimePickerActivity.class));
                break;
            case R.id.tv_to_plan:
                break;
        }
    }
}
