package com.yunkahui.datacubeper.bill.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.TimeItem;

import java.util.ArrayList;

public class AutoPlanActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private TextView mTvRepayDate;
    private EditText mEtInputAmount;
    private EditText mEtInputTimes;
    private ArrayList<TimeItem> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_auto_plan);
        super.onCreate(savedInstanceState);
        setTitle("自动规划");
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
    }

    @Override
    public void initView() {
        mTvRepayDate = findViewById(R.id.tv_repay_date);
        mEtInputAmount = findViewById(R.id.et_input_amount);
        mEtInputTimes = findViewById(R.id.et_input_times);
        findViewById(R.id.rl_select_date).setOnClickListener(this);
        findViewById(R.id.tv_go_plan).setOnClickListener(this);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_select_date:
                startActivityForResult(new Intent(this, TimePickerActivity.class)
                        .putExtra("time", getIntent().getStringExtra("time"))
                        .putParcelableArrayListExtra("selected_time", mList), 0);
                break;
            case R.id.tv_go_plan:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            ArrayList<TimeItem> list = data.getParcelableArrayListExtra("selected_time");
            for (TimeItem i : list) {
                Log.e("result", "onActivityResult: " + i.getDay());
            }
            if (list.size() > 0) {
                mList.clear();
                mList.addAll(list);
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (i == list.size() - 1) {
                        builder.append(mList.get(i).getDay());
                    } else {
                        builder.append(mList.get(i).getDay() + ",");
                    }
                }
                mTvRepayDate.setText(builder.toString());
                mTvRepayDate.setTextColor(Color.BLACK);
            } else {
                mTvRepayDate.setText(getString(R.string.repay_date));
                mTvRepayDate.setTextColor(getResources().getColor(R.color.text_color_gray_9d9d9d));
            }
        }
    }

}
