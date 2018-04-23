package com.yunkahui.datacubeper.bill.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.adapter.GenerateDataAdapter;
import com.yunkahui.datacubeper.bill.logic.AutoPlanLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.GeneratePlan;
import com.yunkahui.datacubeper.common.bean.GeneratePlanItem;
import com.yunkahui.datacubeper.common.bean.TimeItem;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.home.ui.AdjustPlanActivity;

import java.util.ArrayList;
import java.util.List;

public class AutoPlanActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private static final String TAG = "AutoPlanActivity";
    private int mPosition;
    private List<GeneratePlanItem> mList;
    private TextView mTvRepayDate;
    private EditText mEtInputAmount;
    private EditText mEtInputTimes;
    private RecyclerView mRecyclerView;
    private ArrayList<TimeItem> mCurrentTimeList;
    private ArrayList<TimeItem> mResultList;
    private TextView mTvGoPlan;
    private AutoPlanLogic mLogic;
    private GenerateDataAdapter mAdapter;
    private BaseBean<GeneratePlan> mBaseBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_pos_plan);
        super.onCreate(savedInstanceState);
        setTitle("自动规划");
    }

    @Override
    public void initData() {
        mCurrentTimeList = new ArrayList<>();
        mLogic = new AutoPlanLogic();
        mList = new ArrayList<>();
        initListener();
        mAdapter = new GenerateDataAdapter(R.layout.layout_list_item_generate_data, mList, getIntent().getStringExtra("bank_card_name"), getIntent().getStringExtra("bank_card_num"));
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String type;
                if (mList.get(position).getType() == 0) {
                    type = "消费";
                } else {
                    type = "还款";
                }
                startActivityForResult(new Intent(AutoPlanActivity.this, AdjustPlanActivity.class)
                        .putExtra("type", type)
                        .putExtra("amount", String.valueOf(mList.get(position).getMoney()))
                        .putExtra("business_type", mList.get(position).getMccType())
                        .putExtra("is_commit_to_server", false), 1);
                mPosition = position;
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "确认").setIcon(R.mipmap.ic_sure_white).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                showBottomSheetDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showBottomSheetDialog() {
        TimeItem startTime = mCurrentTimeList.get(0);
        TimeItem endTime = mCurrentTimeList.get(mCurrentTimeList.size() - 1);
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_sure_dialog, null);
        GeneratePlan.PlanningAdditionalBean additional = mBaseBean.getRespData().getPlanningAdditional();
        ((TextView) contentView.findViewById(R.id.tv_repay_amount)).setText(
                String.format(getString(R.string.money_format), additional.getRepayTotalMoney()));
        ((TextView) contentView.findViewById(R.id.tv_repay_times)).setText(
                String.format(getString(R.string.num_format), additional.getRepayTotalCount()));
        ((TextView) contentView.findViewById(R.id.tv_expense_times)).setText(
                String.format(getString(R.string.num_format), additional.getConsumeTotalCount()));
        ((TextView) contentView.findViewById(R.id.tv_repay_max)).setText(
                String.format(getString(R.string.money_format), additional.getMaxInRepaymentList()));
        ((TextView) contentView.findViewById(R.id.tv_time)).setText(
                String.format(getString(R.string.time_format), startTime.getYear(), startTime.getMonth(), startTime.getDay(),
                        endTime.getYear(), endTime.getMonth(), endTime.getDay()));
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(contentView);
        bottomSheetDialog.show();
        contentView.findViewById(R.id.show_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        contentView.findViewById(R.id.show_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                LoadingViewDialog.getInstance().show(AutoPlanActivity.this);
                for (GeneratePlan.PlanningListBean s1 : mBaseBean.getRespData().getPlanningList()) {
                    for (GeneratePlan.PlanningListBean.DetailsBean s2 : s1.getDetails()) {
                        for (GeneratePlan.PlanningListBean.DetailsBean.ConsumptionBean s3 : s2.getConsumption()) {
                            Log.e(TAG, "onClick: "+s3.getMoney());
                        }
                    }
                }
                mLogic.confirmSmartPlan(AutoPlanActivity.this, getIntent().getIntExtra("user_credit_card_id", 0), new Gson().toJson(mBaseBean), new SimpleCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        LoadingViewDialog.getInstance().dismiss();
                        Log.e(TAG, "confirmPosPlan onSuccess: "+baseBean.getJsonObject().toString());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        LoadingViewDialog.getInstance().dismiss();
                        Log.e(TAG, "confirmPosPlan onFailure: "+throwable.getMessage());
                    }
                });

            }
        });
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
                        .putParcelableArrayListExtra("selected_time", mCurrentTimeList), 1);
                break;
            case R.id.tv_go_plan:
                generateData();
                break;
        }
    }

    private void generateData() {
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
            date.append(item.getYear() + "-" + item.getMonth() + "-" + item.getDay() + ",");
        }
        date.deleteCharAt(date.toString().length() - 1);
        mLogic.generateSmartPlan(this, getIntent().getIntExtra("user_credit_card_id", 0), mEtInputAmount.getText().toString(),
                date.toString(), mEtInputTimes.getText().toString(), new SimpleCallBack<BaseBean<GeneratePlan>>() {
                    @Override
                    public void onSuccess(BaseBean<GeneratePlan> baseBean) {
                        Log.e(TAG, "onSuccess: "+baseBean.getJsonObject().toString());
                        try {
                            if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                                mBaseBean = baseBean;
                                GeneratePlanItem item = null;
                                for (int x = 0; x < baseBean.getRespData().getPlanningList().size(); x++) {
                                    List<GeneratePlan.PlanningListBean.DetailsBean> details = baseBean.getRespData().getPlanningList().get(x).getDetails();
                                    for (int y = 0; y < details.size(); y++) {
                                        GeneratePlan.PlanningListBean.DetailsBean detailsBean = details.get(y);
                                        item = new GeneratePlanItem();
                                        item.setType(1);
                                        item.setGroup(y);
                                        item.setSection(-1);
                                        item.setMoney(detailsBean.getRepayment().getMoney());
                                        item.setTimeStamp(detailsBean.getRepayment().getTime());
                                        mList.add(item);
                                        for (int z = 0; z < detailsBean.getConsumption().size(); z++) {
                                            item = new GeneratePlanItem();
                                            item.setType(0);
                                            item.setGroup(y);
                                            item.setSection(z);
                                            GeneratePlan.PlanningListBean.DetailsBean.ConsumptionBean consumptionBean = detailsBean.getConsumption().get(0);
                                            item.setMoney(consumptionBean.getMoney());
                                            item.setTimeStamp(consumptionBean.getTime());
                                            item.setMccType(consumptionBean.getMccType());
                                            mList.add(item);
                                        }
                                    }
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(TAG, "onFailure: " + throwable.getMessage());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_FIRST_USER) {
            mResultList = data.getParcelableArrayListExtra("selected_time");
            if (mResultList.size() > 0) {
                if (mEtInputAmount.getText().toString().length() > 0 && mEtInputTimes.getText().toString().length() > 0) {
                    mTvGoPlan.setSelected(true);
                }
                mCurrentTimeList.clear();
                mCurrentTimeList.addAll(mResultList);
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < mResultList.size(); i++) {
                    if (i == mResultList.size() - 1) {
                        builder.append(mCurrentTimeList.get(i).getDay());
                    } else {
                        builder.append(mCurrentTimeList.get(i).getDay() + ",");
                    }
                }
                mTvRepayDate.setText(builder.toString());
                mTvRepayDate.setTextColor(Color.BLACK);
            } else {
                mTvRepayDate.setText(getString(R.string.repay_date));
                mTvRepayDate.setTextColor(getResources().getColor(R.color.text_color_gray_9d9d9d));
            }
        } else if (resultCode == Activity.RESULT_OK) {
            int amount = Integer.parseInt(data.getStringExtra("amount"));
            GeneratePlanItem item = mList.get(mPosition);
            item.setMoney(amount);
            GeneratePlan.PlanningListBean.DetailsBean detailsBean = mBaseBean.getRespData().getPlanningList().get(0).getDetails().get(item.getSection());
            if ("repay".equals(data.getStringExtra("type"))) {
                detailsBean.getRepayment().setMoney(amount);
            } else if ("expense".equals(data.getStringExtra("type"))) {
                GeneratePlan.PlanningListBean.DetailsBean.ConsumptionBean consumptionBean = detailsBean.getConsumption().get(mList.get(mPosition).getSection());
                consumptionBean.setMoney(amount);
                consumptionBean.setMccType(data.getStringExtra("business_type"));
                item.setMccType(data.getStringExtra("business_type"));
            }
            mAdapter.notifyItemChanged(mPosition);
            Toast.makeText(AutoPlanActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
        }
    }
}
