package com.yunkahui.datacubeper.bill.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.adapter.ActivatePlanListAdapter;
import com.yunkahui.datacubeper.bill.logic.FailCardListLogic;
import com.yunkahui.datacubeper.common.bean.ActivatePlan;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import java.util.ArrayList;
import java.util.List;

//激活规划页面
public class ActivatePlanActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private TextView mTextViewTips;
    private TextView mTextViewAllMoney;
    private TextView mTextViewCycle;
    private RecyclerView mRecyclerView;


    private int mCardId;
    private List<ActivatePlan.Plan> mPlanList;
    private ActivatePlanListAdapter mAdapter;
    private FailCardListLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_activate_plan);
        super.onCreate(savedInstanceState);
        setTitle("激活规划");

    }

    @Override
    public void initData() {
        mLogic = new FailCardListLogic();
        mPlanList = new ArrayList<>();
        mCardId = getIntent().getIntExtra("id", 0);
        mTextViewTips.setText(Html.fromHtml("<font color='#ff0000'>重要：</font>提交激活规划时，请保证有足够的保留金，注意还款期限是否逾期，如果逾期请进行重新规划操作"));
        mAdapter = new ActivatePlanListAdapter(R.layout.layout_list_item_generate_data, mPlanList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        loadData(mCardId);
    }

    @Override
    public void initView() {
        mTextViewTips = findViewById(R.id.text_view_tips);
        mTextViewAllMoney = findViewById(R.id.text_view_all_money);
        mTextViewCycle = findViewById(R.id.text_view_cycle);
        mRecyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.button_submit).setOnClickListener(this);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    public void loadData(int id) {
        LoadingViewDialog.getInstance().show(this);
        mLogic.loadActivatePlanning(this, id, new SimpleCallBack<BaseBean<ActivatePlan>>() {
            @Override
            public void onSuccess(BaseBean<ActivatePlan> baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("获取激活规划->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mTextViewAllMoney.setText(baseBean.getRespData().getRepay_total_money());
                    mTextViewCycle.setText(baseBean.getRespData().getExecute_period_begin() + " 至 " + baseBean.getRespData().getExecute_period_end());
                    mPlanList.clear();
                    mPlanList.addAll(baseBean.getRespData().getNew_plannings());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }

    public void activatePlan() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.activatePlanning(this, mCardId, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("激活规划->" + baseBean.getJsonObject().toString());
                ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    setResult(RESULT_OK);
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                activatePlan();
                break;
        }
    }
}
