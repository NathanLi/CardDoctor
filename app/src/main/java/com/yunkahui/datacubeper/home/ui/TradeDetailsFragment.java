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
import com.wang.avi.AVLoadingIndicatorView;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.TradeRecordSummary;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.home.adapter.ExpandableTradeRecordAdapter;
import com.yunkahui.datacubeper.home.logic.TradeRecordLogic;

import java.util.ArrayList;
import java.util.List;
//余额明细-全部
public class TradeDetailsFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private ConstraintLayout mSuspensionBar;
    private AVLoadingIndicatorView mLayoutLoading;
    private TextView mTvTime;
    private TextView mTvMessage;

    private TradeRecordLogic mLogic;
    private ExpandableTradeRecordAdapter mAdapter;
    private List<MultiItemEntity> mList;
    private int mAllPage;
    private int mCurrentPage;
    private int mSuspensionHeight;
    private int mCurrentPosition;

    @Override
    public void initData() {
        mLogic = new TradeRecordLogic();
        mList = new ArrayList<>();
        getTradeDetailsData();
        mAdapter = new ExpandableTradeRecordAdapter(mActivity, mList);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mCurrentPage >= mAllPage) {
                    mAdapter.loadMoreEnd();
                } else {
                    getTradeDetailsData();
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
                if (mAdapter.getItemViewType(mCurrentPosition + 1) == ExpandableTradeRecordAdapter.TYPE_LEVEL_0) {
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
        mAdapter.expandAll();
    }

    //******** 获取交易详情 ********
    private void getTradeDetailsData() {
        mLogic.getTradeDetail(mActivity, 20, ++mCurrentPage, "balance", new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                mLayoutLoading.setVisibility(View.GONE);
                LogUtils.e("全部交易记录->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mAllPage = baseBean.getJsonObject().optJSONObject("respData").optInt("pages");
                    mList.addAll(mLogic.parsingJSONForTradeDetail(baseBean));
                    initSuspensionBar();
                    mAdapter.notifyDataSetChanged();
                    mSuspensionBar.setVisibility(mList.size() > 0 ? View.VISIBLE : View.GONE);
                } else {
                    mSuspensionBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                mLayoutLoading.setVisibility(View.GONE);
                Toast.makeText(mActivity, "获取全部交易记录失败", Toast.LENGTH_SHORT).show();
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
        mLayoutLoading = view.findViewById(R.id.av_loading_view);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSuspensionBar = view.findViewById(R.id.suspension_bar);
        mTvTime = view.findViewById(R.id.tv_time);
        mTvMessage = view.findViewById(R.id.tv_mess);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_all_trade_record;
    }
}
