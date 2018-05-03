package com.yunkahui.datacubeper.bill.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.yunkahui.datacubeper.bill.logic.PosPlanLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.GeneratePlan;
import com.yunkahui.datacubeper.common.bean.GeneratePlanItem;
import com.yunkahui.datacubeper.common.bean.TimeItem;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.home.ui.AdjustPlanActivity;
import com.yunkahui.datacubeper.mine.ui.AddCashCardActivity;
import com.yunkahui.datacubeper.upgradeJoin.ui.UpgradeVipActivity;

import java.util.ArrayList;
import java.util.List;

public class PosPlanActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private TextView mTvRepayDate;
    private EditText mEtInputAmount;
    private EditText mEtInputTimes;
    private TextView mTvGoPlan;

    private PosPlanLogic mLogic;
    private List<GeneratePlanItem> mList;
    private ArrayList<TimeItem> mSelectedTimeList;
    private ArrayList<TimeItem> mResultList;
    private GenerateDataAdapter mAdapter;
    private BaseBean<GeneratePlan> mBaseBean;
    private int mLastClickPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_pos_plan);
        super.onCreate(savedInstanceState);
        setTitle("POS规划");
    }

    @Override
    public void initData() {
        mLogic = new PosPlanLogic();
        mList = new ArrayList<>();
        mSelectedTimeList = new ArrayList<>();
        mEtInputAmount.addTextChangedListener(new InnerTextChangeListener());
        mEtInputTimes.addTextChangedListener(new InnerTextChangeListener());
        String card = getIntent().getStringExtra("bank_card_num");
        String cardFormat = String.format(getResources().getString(R.string.bank_card_tail_num), card.substring(card.length() - 4, card.length()));
        mAdapter = new GenerateDataAdapter(R.layout.layout_list_item_generate_data, mList, getIntent().getStringExtra("bank_card_name"), cardFormat);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivityForResult(new Intent(PosPlanActivity.this, AdjustPlanActivity.class)
                        .putExtra("type", mList.get(position).getType() == 0 ? "消费" : "还款")
                        .putExtra("amount", String.valueOf(mList.get(position).getMoney()))
                        .putExtra("business_type", mList.get(position).getMccType())
                        .putExtra("is_commit_to_server", false), 1);
                mLastClickPosition = position;
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initView() {
        mTvRepayDate = findViewById(R.id.tv_repay_date);
        mEtInputAmount = findViewById(R.id.et_input_amount);
        mEtInputTimes = findViewById(R.id.et_input_times);
        mRecyclerView = findViewById(R.id.recycler_view);
        mTvGoPlan = findViewById(R.id.tv_go_plan);
        mTvGoPlan.setOnClickListener(this);
        findViewById(R.id.rl_select_date).setOnClickListener(this);
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

    //******** 展示确认弹窗 ********
    private void showBottomSheetDialog() {
        TimeItem startTime = mSelectedTimeList.get(0);
        TimeItem endTime = mSelectedTimeList.get(mSelectedTimeList.size() - 1);
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_sure_dialog, null);
        if (mBaseBean != null) {
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
                    LoadingViewDialog.getInstance().show(PosPlanActivity.this);
                    mLogic.confirmPosPlan(PosPlanActivity.this, getIntent().getIntExtra("user_credit_card_id", 0), new Gson().toJson(mBaseBean.getRespData()), new SimpleCallBack<BaseBean>() {
                        @Override
                        public void onSuccess(BaseBean baseBean) {
                            LoadingViewDialog.getInstance().dismiss();
                            LogUtils.e("提交规划--》" + baseBean.getJsonObject().toString());
                            if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                                finish();
                            } else if ("0265".equals(baseBean.getRespCode())) {
                                showUpgradeJoinDialog(baseBean.getRespDesc());
                            } else {
                                ToastUtils.show(PosPlanActivity.this, baseBean.getRespDesc());
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            LoadingViewDialog.getInstance().dismiss();
                            Toast.makeText(PosPlanActivity.this, "POS规划失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
    }

    //******** 跳往修改卡片 ********
    private void showUpgradeJoinDialog(String desc) {
        new AlertDialog.Builder(this)
                .setMessage(desc)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(PosPlanActivity.this, AddCardActivity.class);
                        intent.putExtra("type", AddCardActivity.TYPE_EDIT);
                        intent.putExtra("card_id", getIntent().getIntExtra("user_credit_card_id", 0));
                        intent.putExtra("card_number", getIntent().getStringExtra("bank_card_num"));
                        intent.putExtra("bank_card_name",getIntent().getStringExtra("bank_card_name"));
                        startActivity(intent);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
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
                        .putExtra("time", getIntent().getLongExtra("time", 0))
                        .putParcelableArrayListExtra("selected_time", mSelectedTimeList), 1);
                break;
            case R.id.tv_go_plan:
                generateData();
                break;
        }
    }

    //******** 进行规划 ********
    private void generateData() {
        if (check()) {
            StringBuilder date = new StringBuilder();
            for (TimeItem item : mResultList) {
                date.append(item.getYear() + "-" + item.getMonth() + "-" + item.getDay() + ",");
            }
            date.deleteCharAt(date.toString().length() - 1);
            LoadingViewDialog.getInstance().show(this);
            mLogic.generatePosPlan(this, getIntent().getIntExtra("user_credit_card_id", 0), mEtInputAmount.getText().toString(),
                    date.toString(), mEtInputTimes.getText().toString(), new SimpleCallBack<BaseBean<GeneratePlan>>() {
                        @Override
                        public void onSuccess(BaseBean<GeneratePlan> baseBean) {
                            LoadingViewDialog.getInstance().dismiss();
                            if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                                mBaseBean = baseBean;
                                mList.clear();
                                mList.addAll(mLogic.parsingJSONForPosPlan(baseBean));
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(PosPlanActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            LoadingViewDialog.getInstance().dismiss();
                            Toast.makeText(PosPlanActivity.this, "生成POS规划数据失败", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean check() {
        if (mResultList != null && mResultList.size() == 0 || TextUtils.isEmpty(mEtInputAmount.getText().toString()) || TextUtils.isEmpty(mEtInputTimes.getText().toString())) {
            Toast.makeText(this, "有信息没有填写", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Integer.parseInt(mEtInputAmount.getText().toString()) < 1000) {
            Toast.makeText(this, "还款金额不能少于1000.00元！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Integer.parseInt(mEtInputAmount.getText().toString()) % 100 != 0) {
            Toast.makeText(this, "还款金额必须是100倍数！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mResultList != null && Integer.parseInt(mEtInputTimes.getText().toString()) > mResultList.size()) {
            Toast.makeText(this, "为了完善您的信用体制，每天还款不能超过1笔！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mResultList != null && Integer.parseInt(mEtInputTimes.getText().toString()) < mResultList.size()) {
            Toast.makeText(this, "还款笔数必须大于或等于天数", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_FIRST_USER) {
            handleSelectedTime(data);
        } else if (resultCode == Activity.RESULT_OK) {
            handleInfo(data);
        }
    }

    //******** 处理修改信息 ********
    private void handleInfo(Intent data) {
        int amount = Integer.parseInt(data.getStringExtra("amount"));
        GeneratePlanItem item = mList.get(mLastClickPosition);
        item.setMoney(amount);
        GeneratePlan.PlanningListBean.DetailsBean detailsBean = mBaseBean.getRespData().getPlanningList().get(item.getGroup()).getDetails().get(0);
        if ("repay".equals(data.getStringExtra("type"))) {
            detailsBean.getRepayment().setMoney(amount);
        } else if ("expense".equals(data.getStringExtra("type"))) {
            GeneratePlan.PlanningListBean.DetailsBean.ConsumptionBean consumptionBean = detailsBean.getConsumption().get(mList.get(mLastClickPosition).getSection());
            consumptionBean.setMoney(amount);
            consumptionBean.setMccType(data.getStringExtra("business_type"));
            item.setMccType(data.getStringExtra("business_type"));
        }
        mAdapter.notifyItemChanged(mLastClickPosition);
        Toast.makeText(PosPlanActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
    }

    //******** 处理时间选择结果 ********
    private void handleSelectedTime(Intent data) {
        mResultList = data.getParcelableArrayListExtra("selected_time");
        if (mResultList.size() > 0) {
            if (mEtInputAmount.getText().toString().length() > 0 && mEtInputTimes.getText().toString().length() > 0) {
                mTvGoPlan.setSelected(true);
            }
            mSelectedTimeList.clear();
            mSelectedTimeList.addAll(mResultList);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < mResultList.size(); i++) {
                if (i == mResultList.size() - 1) {
                    builder.append(mSelectedTimeList.get(i).getDay());
                } else {
                    builder.append(mSelectedTimeList.get(i).getDay() + ",");
                }
            }
            mTvRepayDate.setText(builder.toString());
            mTvRepayDate.setTextColor(Color.BLACK);
        } else {
            mSelectedTimeList.clear();
            mTvRepayDate.setText(getString(R.string.repay_date));
            mTvRepayDate.setTextColor(getResources().getColor(R.color.text_color_gray_9d9d9d));
        }
    }

    private class InnerTextChangeListener extends CustomTextChangeListener {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mResultList != null && mResultList.size() > 0 && s.length() > 0 && mEtInputAmount.getText().toString().length() > 0
                    && mEtInputTimes.getText().toString().length() > 0) {
                mTvGoPlan.setSelected(true);
            } else {
                mTvGoPlan.setSelected(false);
            }
        }
    }
}
