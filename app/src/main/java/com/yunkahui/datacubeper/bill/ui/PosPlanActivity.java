package com.yunkahui.datacubeper.bill.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.TimeItem;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PosPlanActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private static final String TAG = "PosPlanActivity";
    private TextView mTvRepayDate;
    private EditText mEtInputAmount;
    private EditText mEtInputTimes;
    private RecyclerView mRecyclerView;
    private ArrayList<TimeItem> mList;
    private ArrayList<TimeItem> mResultList;
    private TextView mTvGoPlan;
    private PlanPickerLogic mLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_pos_plan);
        super.onCreate(savedInstanceState);
        setTitle("POS规划");
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        mLogic = new PlanPickerLogic();
        initListener();
    }

    private void initListener() {
        mEtInputAmount.addTextChangedListener(new CustomTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mResultList != null && mResultList.size() > 0 && s.length() > 0 && mEtInputTimes.getText().toString().length() > 0) {
                    mTvGoPlan.setSelected(true);
                } else {
                    mTvGoPlan.setSelected(false);
                }
            }
        });
        mEtInputTimes.addTextChangedListener(new CustomTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mResultList != null && mResultList.size() > 0 && s.length() > 0 && mEtInputAmount.getText().toString().length() > 0) {
                    mTvGoPlan.setSelected(true);
                } else {
                    mTvGoPlan.setSelected(false);
                }
            }
        });
    }

    @Override
    public void initView() {
        mTvRepayDate = findViewById(R.id.tv_repay_date);
        mEtInputAmount = findViewById(R.id.et_input_amount);
        mEtInputTimes = findViewById(R.id.et_input_times);
        mRecyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.rl_select_date).setOnClickListener(this);
        mTvGoPlan = findViewById(R.id.tv_go_plan);
        mTvGoPlan.setOnClickListener(this);
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
                        .putParcelableArrayListExtra("selected_time", mList), 1);
                break;
            case R.id.tv_go_plan:
                if (mResultList != null && mResultList.size() == 0 || TextUtils.isEmpty(mEtInputAmount.getText().toString()) || TextUtils.isEmpty(mEtInputTimes.getText().toString())) {
                    Toast.makeText(this, "有信息没有填写", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Integer.parseInt(mEtInputAmount.getText().toString()) < 1000) {
                    Toast.makeText(this, "还款金额不能少于1000.00元！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Integer.parseInt(mEtInputAmount.getText().toString()) % 100 != 0) {
                    Toast.makeText(this, "还款金额必须是100倍数！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mResultList != null && Integer.parseInt(mEtInputTimes.getText().toString()) > mResultList.size()) {
                    Toast.makeText(this, "为了完善您的信用体制，每天还款不能超过1笔！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mResultList != null && Integer.parseInt(mEtInputTimes.getText().toString()) < mResultList.size()) {
                    Toast.makeText(this, "还款笔数必须大于或等于天数", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuilder date = new StringBuilder();
                for (TimeItem item : mResultList) {
                    date.append(item.getYear()+"-"+item.getMonth()+"-"+item.getDay()+",");
                }
                date.deleteCharAt(date.toString().length() - 1);
                mLogic.generatePlan(this, getIntent().getIntExtra("user_credit_card_id", 0), mEtInputAmount.getText().toString(),
                        date.toString(), mEtInputTimes.getText().toString(), new SimpleCallBack<JsonObject>() {
                            @Override
                            public void onSuccess(JsonObject jsonObject) {
                                try {
                                    JSONObject object = new JSONObject(jsonObject.toString());
                                    Log.e(TAG, "onSuccess: "+object.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                Log.e(TAG, "onFailure: "+throwable.getMessage());
                            }
                        });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mResultList = data.getParcelableArrayListExtra("selected_time");
            if (mResultList.size() > 0) {
                if (mEtInputAmount.getText().toString().length() > 0 && mEtInputTimes.getText().toString().length() > 0) {
                    mTvGoPlan.setSelected(true);
                }
                mList.clear();
                mList.addAll(mResultList);
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < mResultList.size(); i++) {
                    if (i == mResultList.size() - 1) {
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
