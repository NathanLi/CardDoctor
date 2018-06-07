package com.yunkahui.datacubeper.home.ui;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.TradeRecordSummary;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.home.adapter.ExpandableProfitIncomeAdapter;
import com.yunkahui.datacubeper.home.logic.ProfitIncomeLogic;

import java.util.ArrayList;
import java.util.List;

public class ProfitIncomeFragment extends BaseFragment {

    public static final String TYPE_FUN_RUN = "fenruns";      //分润收入
    public static final String TYPE_COMMISSION = "commission";    //分佣收入

    public static final String TYPE_POS_FEN_RUN = "gain";  //pos分润


    private ConstraintLayout mSuspensionBar;
    private RecyclerView mRecyclerView;
    private View mLayoutLoading;
    private TextView mTvTime;
    private TextView mTvMessage;

    private ProfitIncomeLogic mLogic;
    private ExpandableProfitIncomeAdapter mAdapter;
    private List<MultiItemEntity> mList;
    private int mAllPage;
    private int mCurrentPage;
    private int mSuspensionHeight;
    private int mCurrentPosition;

    private String mType;

    @Override
    public void initData() {
        mType = getArguments().getString("type", "");
        mLogic = new ProfitIncomeLogic();
        mList = new ArrayList<>();
        mAdapter = new ExpandableProfitIncomeAdapter(mActivity, mList);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                    loadData();
            }
        }, mRecyclerView);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setEnableLoadMore(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = mSuspensionBar.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mAdapter.getItemViewType(mCurrentPosition + 1) == ExpandableProfitIncomeAdapter.TYPE_LEVEL_0) {
                    View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                    if (view != null) {
                        if (view.getTop() <= mSuspensionHeight) {
                            mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
                        } else {
                            mSuspensionBar.setY(0);
                        }
                    }
                }

                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    mSuspensionBar.setY(0);
                    updateSuspensionBar();
                }
            }
        });
        mAdapter.setEnableLoadMore(true);
        mAdapter.disableLoadMoreIfNotFullPage();
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter.setEmptyView(R.layout.layout_no_data);
        mRecyclerView.setAdapter(mAdapter);
        loadData();
    }


    private void loadData() {
        switch (mType) {
            case TYPE_POS_FEN_RUN:
                getPosFenRunData();
                break;
            case TYPE_COMMISSION:
            case TYPE_FUN_RUN:
                getProfitIncomeData();
                break;
        }
    }

    //POS分润数据
    private void getPosFenRunData() {
        mLogic.getPosFenRunData(getActivity(), 20, ++mCurrentPage, mType, new InnerCallBack());
    }


    //******** 查询分润收入 分佣收入********
    private void getProfitIncomeData() {
        mLogic.getProfitIncome(mActivity, 20, ++mCurrentPage, mType, new InnerCallBack());
    }

    //请求回调
    class InnerCallBack extends SimpleCallBack<BaseBean>{
        @Override
        public void onSuccess(BaseBean baseBean) {
            mLayoutLoading.setVisibility(View.GONE);
            LogUtils.e(mType + "收入->" + baseBean.getJsonObject().toString());
            if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                List<MultiItemEntity> entityList = mLogic.parsingJSONForProfitIncome(baseBean);
                if (entityList.size() > 0) {
                    mAllPage = baseBean.getJsonObject().optJSONObject("respData").optInt("pages");
                    mList.addAll(entityList);
                    initSuspensionBar();
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                    if (mCurrentPage == 1) {
                        mAdapter.expandAll();
                    }
                    if(mCurrentPage==mAllPage){
                        mAdapter.loadMoreEnd();
                    }
                } else {
                    mSuspensionBar.setVisibility(View.GONE);
                }
            } else {
                mSuspensionBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onFailure(Throwable throwable) {
            mLayoutLoading.setVisibility(View.GONE);
            mSuspensionBar.setVisibility(View.GONE);
            Toast.makeText(mActivity, "请求失败 " + throwable.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //******** 初始化悬浮条信息 ********
    private void initSuspensionBar() {
        TradeRecordSummary summary = (TradeRecordSummary) mList.get(0);
        mTvTime.setText(summary.getTime());
        mTvMessage.setText(summary.getMessage());
    }

    //******** 更新悬浮条信息 ********
    private void updateSuspensionBar() {
        if (mList.get(mCurrentPosition) instanceof TradeRecordSummary) {
            TradeRecordSummary summary = (TradeRecordSummary) mList.get(mCurrentPosition);
            mTvTime.setText(summary.getTime());
            mTvMessage.setText(summary.getMessage());
        }
    }

    @Override
    public void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSuspensionBar = view.findViewById(R.id.suspension_bar);
        mTvTime = view.findViewById(R.id.tv_time);
        mTvMessage = view.findViewById(R.id.tv_mess);
        mLayoutLoading = view.findViewById(R.id.rl_loading_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profit_income;
    }
}
