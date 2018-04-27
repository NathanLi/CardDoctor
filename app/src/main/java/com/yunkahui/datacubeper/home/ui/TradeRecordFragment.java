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

    private RecyclerView mRecyclerView;

    private TradeRecordLogic mLogic;
    private BaseQuickAdapter mAdapter;
    private List<WithdrawRecord.WithdrawDetail> mWithdrawDetails;
    private List<RechargeRecord.RechargeDetail> mRechargeDetails;
    private int mCurrentPage;
    private int mAllPages;

    public static Fragment newInstance(int kind) {
        TradeRecordFragment fragment = new TradeRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("kind", kind);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        mLogic = new TradeRecordLogic();
        mRechargeDetails = new ArrayList<>();
        mWithdrawDetails = new ArrayList<>();
        int kind = getArguments().getInt("kind");
        switch (kind) {
            case 0:
                getTradeData("recharge", 20, 1);
                mAdapter = new RechargeRecordAdapter(R.layout.layout_list_item_trade_record, mRechargeDetails);
                break;
            case 1:
                getTradeData("withdraw", 20, 1);
                mAdapter = new WithdrawRecordAdapter(R.layout.layout_list_item_trade_record, mWithdrawDetails);
                break;
        }
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String action, time, money, status;
                if (0 == getArguments().getInt("kind")) {
                    RechargeRecord.RechargeDetail detail = mRechargeDetails.get(position);
                    action = "账户充值";
                    time = TimeUtils.format("yyyy-MM-dd hh:mm:ss", detail.getCreate_time());
                    money = String.valueOf(detail.getAmount());
                    status = getTradeStatus(detail.getOrder_state(), "充值");
                } else {
                    WithdrawRecord.WithdrawDetail detail = mWithdrawDetails.get(position);
                    action = "账户提现";
                    time = TimeUtils.format("yyyy-MM-dd hh:mm:ss", detail.getCreate_time());
                    money = String.valueOf(detail.getWithdraw_amount());
                    status = getTradeStatus(detail.getOrder_state(), "提现");
                }
                startActivity(new Intent(mActivity, SingleRecordActivity.class)
                        .putExtra("time", time)
                        .putExtra("money", money)
                        .putExtra("status", status)
                        .putExtra("action", action));
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mCurrentPage >= mAllPages) {
                    mAdapter.loadMoreEnd();
                } else {
                    if (0 == getArguments().getInt("kind")) {
                        getTradeData("recharge", 20, ++mCurrentPage);
                    } else {
                        getTradeData("withdraw", 20, ++mCurrentPage);
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

    //******** 获取对应页面数据 ********
    public void getTradeData(final String pdType, int pageSize, int pageNum) {
        if ("recharge".equals(pdType)) {
            mLogic.getRechargeRecord(mActivity, pdType, pageSize, pageNum, new SimpleCallBack<BaseBean<RechargeRecord>>() {
                @Override
                public void onSuccess(BaseBean<RechargeRecord> baseBean) {
                    LogUtils.e("充值页面->" + baseBean.toString());
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
                    Toast.makeText(mActivity, "获取充值数据失败", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mLogic.getWithdrawRecord(mActivity, pdType, pageSize, pageNum, new SimpleCallBack<BaseBean<WithdrawRecord>>() {
                @Override
                public void onSuccess(BaseBean<WithdrawRecord> baseBean) {
                    LogUtils.e("提现页面->" + baseBean.toString());
                    if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                        mCurrentPage = baseBean.getRespData().getPageNum();
                        mAllPages = baseBean.getRespData().getPages();
                        mWithdrawDetails.addAll(baseBean.getRespData().getList());
                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(mActivity, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Toast.makeText(mActivity, "获取提现数据失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public String getTradeStatus(String state, String action) {
        String status;
        switch (state) {
            case "0":
                status = action + "初始化";
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
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_trade_record;
    }
}
