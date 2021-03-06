package com.yunkahui.datacubeper.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.wang.avi.AVLoadingIndicatorView;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.RechargeRecord;
import com.yunkahui.datacubeper.common.bean.WithdrawRecord;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.home.adapter.RechargeRecordAdapter;
import com.yunkahui.datacubeper.home.adapter.WithdrawRecordAdapter;
import com.yunkahui.datacubeper.home.logic.TradeRecordLogic;

import java.util.ArrayList;
import java.util.List;

public class TradeRecordFragment extends BaseFragment {

    public static final String TYPE_RECHARGE_ORDER = "00";    //余额充值

    public static final String TYPE_COMMISSIONI_WITHDRAW = "00";    //分佣提现
    public static final String TYPE_BALANCE_WITHDRAW = "02";    //余额提现
    public static final String TYPE_FENRUNS_WITHDRAW = "01";    //分润提现


    private RecyclerView mRecyclerView;
    private AVLoadingIndicatorView mLayoutLoading;

    private TradeRecordLogic mLogic;
    private BaseQuickAdapter mAdapter;
    private List<WithdrawRecord.WithdrawDetail> mWithdrawDetails;
    private List<RechargeRecord.RechargeDetail> mRechargeDetails;
    private int mCurrentPage;
    private int mAllPages;
    private String mType;

    public static Fragment newInstance(int kind, String type) {
        TradeRecordFragment fragment = new TradeRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("kind", kind);
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        mType = getArguments().getString("type");
        mLogic = new TradeRecordLogic();
        mRechargeDetails = new ArrayList<>();
        mWithdrawDetails = new ArrayList<>();
        int kind = getArguments().getInt("kind");
        switch (kind) {
            case 0:
                getRechargeData(20, 1);
                mAdapter = new RechargeRecordAdapter(R.layout.layout_list_item_trade_record, mRechargeDetails);
                break;
            case 1:
                getWithdrawRecord(mType, 20, 1);
                mAdapter = new WithdrawRecordAdapter(R.layout.layout_list_item_trade_record, mWithdrawDetails);
                break;
        }
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String action, time, money, status, remarks,fee;
                if (0 == getArguments().getInt("kind")) {
                    RechargeRecord.RechargeDetail detail = mRechargeDetails.get(position);
                    action = "账户充值";
                    fee=String.valueOf(detail.getFee());
                    time = TimeUtils.format("yyyy-MM-dd hh:mm:ss", detail.getCreate_time());
                    money = String.valueOf(detail.getAmount());
                    status = getTradeStatus(detail.getOrder_state(), "充值");
                    remarks = detail.getCallback_msg();
                } else {
                    WithdrawRecord.WithdrawDetail detail = mWithdrawDetails.get(position);
                    action = "账户提现";
                    fee=String.valueOf(detail.getFee());
                    time = TimeUtils.format("yyyy-MM-dd hh:mm:ss", detail.getCreate_time());
                    money = String.valueOf(detail.getWithdraw_amount());
                    status = getTradeStatus(detail.getOrder_state(), "提现");
                    remarks = detail.getThird_party_msg();
                }

                startActivity(new Intent(mActivity, SingleRecordActivity.class)
                        .putExtra("time", time)
                        .putExtra("money", money)
                        .putExtra("status", status)
                        .putExtra("action", action)
                        .putExtra("remarks", remarks)
                        .putExtra("fee",fee));
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mCurrentPage >= mAllPages) {
                    mAdapter.loadMoreEnd();
                } else {
                    if (0 == getArguments().getInt("kind")) {
                        getRechargeData(20, ++mCurrentPage);
                    } else {
                        getWithdrawRecord(mType, 20, ++mCurrentPage);
                    }
                }
            }
        }, mRecyclerView);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setEnableLoadMore(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter.setEmptyView(R.layout.layout_no_data);
        mRecyclerView.setAdapter(mAdapter);
    }

    //******** 获取提现记录 ********
    private void getWithdrawRecord(String type, int pageSize, int pageNum) {
        mLogic.getWithdrawRecord(mActivity, type, pageSize, pageNum, new SimpleCallBack<BaseBean<WithdrawRecord>>() {
            @Override
            public void onSuccess(BaseBean<WithdrawRecord> baseBean) {
                LogUtils.e("提现->" + baseBean.toString());
                mLayoutLoading.setVisibility(View.GONE);
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mCurrentPage = baseBean.getRespData().getPageNum();
                    mAllPages = baseBean.getRespData().getPages();
                    mWithdrawDetails.addAll(baseBean.getRespData().getList());
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                mLayoutLoading.setVisibility(View.GONE);
                Toast.makeText(mActivity, "获取提现数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //******** 获取充值记录 ********
    private void getRechargeData(int pageSize, int pageNum) {
        mLogic.getRechargeRecord(mActivity, mType, pageSize, pageNum, new SimpleCallBack<BaseBean<RechargeRecord>>() {
            @Override
            public void onSuccess(BaseBean<RechargeRecord> baseBean) {
                mLayoutLoading.setVisibility(View.GONE);
                LogUtils.e(mType+"充值->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mCurrentPage = baseBean.getRespData().getPageNum();
                    mAllPages = baseBean.getRespData().getPages();
                    mRechargeDetails.addAll(baseBean.getRespData().getList());
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(mActivity, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                mLayoutLoading.setVisibility(View.GONE);
                Toast.makeText(mActivity, "获取充值数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getTradeStatus(String state, String action) {
        String status;
        switch (state) {
            case "0":
                status = action + "处理中";
                break;
            case "1":
                status = action + "成功";
                break;
            case "2":
                status = action + "失败";
                break;
            default:
                status = action + "处理中";
                break;
        }
        return status;
    }

    @Override
    public void initView(View view) {
        mLayoutLoading = view.findViewById(R.id.av_loading_view);
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_trade_record;
    }
}
