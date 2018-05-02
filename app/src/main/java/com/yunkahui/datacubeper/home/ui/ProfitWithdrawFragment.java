package com.yunkahui.datacubeper.home.ui;

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
import com.yunkahui.datacubeper.home.adapter.ProfitWithdrawAdapter;
import com.yunkahui.datacubeper.home.logic.ProfitWithdrawLogic;

import java.util.ArrayList;
import java.util.List;

public class ProfitWithdrawFragment extends BaseFragment {

    private RecyclerView mRecyclerView;

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
    }

    //******** 查询分润提现 ********
    private void getProfitWithdrawData() {
        mLogic.getProfitWithdraw(mActivity, "withdraw_fenruns", 20, ++mCurrentPage, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("分润提现->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.toString())) {
                    mAllPage = baseBean.getJsonObject().optJSONObject("respData").optInt("pages");
                    mList.addAll(mLogic.parsingJSONForProfitWithdraw(baseBean));
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mActivity, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(mActivity, "获取分润提现失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profit_withdraw;
    }
}