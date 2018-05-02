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
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.home.adapter.ExpandableProfitIncomeAdapter;
import com.yunkahui.datacubeper.home.logic.ProfitIncomeLogic;

import java.util.ArrayList;
import java.util.List;

public class ProfitIncomeFragment extends BaseFragment {

    private RecyclerView mRecyclerView;

    private ProfitIncomeLogic mLogic;
    private ExpandableProfitIncomeAdapter mAdapter;
    private List<MultiItemEntity> mList;
    private int mAllPage;
    private int mCurrentPage;
    private ConstraintLayout mSuspensionBar;
    private int mSuspensionHeight;
    private int mCurrentPosition;
    private TextView mTvTime;
    private TextView mTvMessage;

    @Override
    public void initData() {
        mLogic = new ProfitIncomeLogic();
        mList = new ArrayList<>();
        getProfitIncomeData();
        mAdapter = new ExpandableProfitIncomeAdapter(mActivity, mList);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mCurrentPage >= mAllPage) {
                    mAdapter.loadMoreEnd();
                } else {
                    getProfitIncomeData();
                }
            }
        }, mRecyclerView);
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
        mAdapter.expandAll();
        mRecyclerView.setAdapter(mAdapter);
    }

    //******** 查询分润收入 ********
    private void getProfitIncomeData() {
        mLogic.getProfitIncome(mActivity, 20, ++mCurrentPage, "fenruns", new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("分润收入->" + baseBean.getJsonObject().toString());
                mAllPage = baseBean.getJsonObject().optJSONObject("respData").optInt("pages");
                mList.addAll(mLogic.parsingJSONForProfitIncome(baseBean));
                initSuspensionBar();
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(mActivity, "获取分润收入失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profit_income;
    }
}
