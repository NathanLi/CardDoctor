package com.yunkahui.datacubeper.home.ui;

import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.bean.TradeRecordSummary;
import com.yunkahui.datacubeper.home.adapter.ExpandableTradeRecordAdapter;
import com.yunkahui.datacubeper.home.logic.TradeRecordLogic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TradeDetailsFragment extends BaseFragment {

    private static final String TAG = "TradeDetailsFragment";
    private TradeRecordLogic mLogic;
    private RecyclerView mRecyclerView;
    private List<MultiItemEntity> mList;
    private String mPages;
    private ExpandableTradeRecordAdapter mAdapter;
    private ConstraintLayout mSuspensionBar;
    private int mSuspensionHeight;
    private int mCurrentPosition;
    private TextView mTvTime;
    private TextView mTvMessage;

    @Override
    public void initData() {
        mLogic = new TradeRecordLogic();
        mList = new ArrayList<>();
        mLogic.getTradeDetail(mActivity, 20, 1, "wallet", new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                try {
                    JSONObject object = new JSONObject(jsonObject.toString());
                    JSONObject respData = object.getJSONObject("respData");
                    mPages = respData.getString("pages");
                    JSONArray jsonArray = respData.getJSONArray("list");
                    //TradeRecordDetail item;
                    TradeRecordDetail item = new TradeRecordDetail(); //待删除
                    TradeRecordSummary summary = new TradeRecordSummary();
                    TradeRecordDetail lastItem = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = new JSONObject(jsonArray.get(i).toString());
                        item = new TradeRecordDetail();
                        item.setTime(com.yunkahui.datacubeper.common.utils.TimeUtils.format("MM-dd hh:mm", j.getLong("create_time")));
                        item.setMoney(j.getString("amountString"));
                        item.setTitle(j.getString("trade_type_ios"));
                        if (lastItem != null) {
                            if (item.getTime().startsWith("0") && lastItem.getTime().startsWith("0") &&
                                    Integer.parseInt(lastItem.getTime().substring(1, 2)) > Integer.parseInt(item.getTime().substring(1, 2))) {
                                summaryInfo(summary);
                                mList.add(summary);
                                summary = new TradeRecordSummary();
                                summary.addSubItem(item);
                            } else if (!item.getTime().startsWith("0") && lastItem.getTime().startsWith("0")) {
                                summaryInfo(summary);
                                mList.add(summary);
                                summary = new TradeRecordSummary();
                                summary.addSubItem(item);
                            } else {
                                summary.addSubItem(item);
                            }
                        } else {
                            summary.addSubItem(item);
                        }
                        if (i == jsonArray.length() - 1) {
                            summaryInfo(summary);
                            mList.add(summary);
                        } else {
                            lastItem = item;
                        }
                    }
                    Log.e(TAG, "onSuccess size: " + mList.size());
                    //******** for 循环待删除 ********
                    for (int i = 0; i < 5; i++) {
                        summary.addSubItem(item);
                    }
                    initSuspensionBar();
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });
        mAdapter = new ExpandableTradeRecordAdapter(mList);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setEnableLoadMore(true);
        mRecyclerView.setAdapter(mAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
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
        mAdapter.expandAll();
    }

    private int index;

    //******** 统计月份信息并设置给数据体 ********
    private void summaryInfo(TradeRecordSummary summary) {
        summary.setTime("标题" + index);
        summary.setMessage("测试标题" + (index++));
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

    private GradientDrawable createColorShape(int color, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
        drawable.setColor(color);
        return drawable;
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
        return R.layout.fragment_all_trade_record;
    }
}
