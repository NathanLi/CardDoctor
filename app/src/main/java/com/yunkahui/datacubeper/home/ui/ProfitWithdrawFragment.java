package com.yunkahui.datacubeper.home.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.home.adapter.ProfitWithdrawAdapter;
import com.yunkahui.datacubeper.home.logic.ProfitWithdrawLogic;

import java.util.ArrayList;
import java.util.List;

public class ProfitWithdrawFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private View mLayoutLoading;

    private ProfitWithdrawLogic mLogic;
    private ProfitWithdrawAdapter mAdapter;
    private List<TradeRecordDetail> mList;
    private int mAllPage;
    private int mCurrentPage;

    @Override
    public void initData() {
        mLogic = new ProfitWithdrawLogic();
        mList = new ArrayList<>();
        getProfitWithdrawData();
        mAdapter = new ProfitWithdrawAdapter(R.layout.layout_list_item_trade_record, mList);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mCurrentPage >= mAllPage) {
                    mAdapter.loadMoreEnd();
                } else {
                    getProfitWithdrawData();
                }
            }
        }, mRecyclerView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.disableLoadMoreIfNotFullPage();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter.setEmptyView(R.layout.layout_no_data);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.e("data="+mList.get(position).toString());
                TradeRecordDetail detail=mList.get(position);
                String action = "分润提现";
                String time = TimeUtils.format("yyyy-MM-dd hh:mm:ss", detail.getTimeStamp());
                String status = "";
                if ("0".equals(detail.getTradeType())) {
                    status = "提现处理中";
                } else if ("1".equals(detail.getTradeType())) {
                    status = "提现成功";
                } else if ("2".equals(detail.getTradeType())) {
                    status = "提现失败";
                } else if ("3".equals(detail.getTradeType())) {
                    status = "提现处理中";
                }
                startActivity(new Intent(mActivity, SingleRecordActivity.class)
                        .putExtra("time", time)
                        .putExtra("money", detail.getMoney())
                        .putExtra("status", status)
                        .putExtra("action", action)
                        .putExtra("remarks", detail.getRemark()));

            }
        });

    }

    //******** 查询分润提现 ********
    private void getProfitWithdrawData() {
        mLogic.getProfitWithdraw(mActivity, "withdraw_fenruns", 20, ++mCurrentPage, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                mLayoutLoading.setVisibility(View.GONE);
                LogUtils.e("分润提现->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mAllPage = baseBean.getJsonObject().optJSONObject("respData").optInt("pages");
                    mList.addAll(mLogic.parsingJSONForProfitWithdraw(baseBean));
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                mLayoutLoading.setVisibility(View.GONE);
                Toast.makeText(mActivity, "获取分润提现失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initView(View view) {
        mLayoutLoading = view.findViewById(R.id.rl_loading_view);
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profit_withdraw;
    }
}