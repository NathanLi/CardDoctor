package com.yunkahui.datacubeper.home.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.bean.TradeRecordSummary;
import com.yunkahui.datacubeper.home.adapter.ProfitWithdrawAdapter;
import com.yunkahui.datacubeper.home.logic.HomeProfitLogic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfitWithdrawFragment extends BaseFragment {


    private static final String TAG = "ProfitWithdrawFragment";
    private HomeProfitLogic mLogic;
    private RecyclerView mRecyclerView;
    private List<TradeRecordDetail> mList;
    private int mAllPage;
    private int mCurrentPage;
    private ProfitWithdrawAdapter mAdapter;

    @Override
    public void initData() {
        mLogic = new HomeProfitLogic();
        mList = new ArrayList<>();
        getProfitIncomeData();
        mAdapter = new ProfitWithdrawAdapter(R.layout.layout_list_item_trade_record, mList);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.disableLoadMoreIfNotFullPage();
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getProfitIncomeData() {
        mLogic.getProfitWithdraw(mActivity, "withdraw_fenruns", 20, ++mCurrentPage, new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                try {
                    JSONObject object = new JSONObject(jsonObject.toString());
                    Log.e(TAG, "ProfitWithdrawFragment: " + object.toString());
                    JSONObject respData = object.getJSONObject("respData");
                    mAllPage = respData.optInt("pages");
                    JSONArray jsonArray = respData.getJSONArray("list");
                    TradeRecordDetail item;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = new JSONObject(jsonArray.get(i).toString());
                        item = new TradeRecordDetail();
                        item.setTimeStamp(j.optLong("create_time"));
                        item.setTime(com.yunkahui.datacubeper.common.utils.TimeUtils.format("yyyy-MM-dd hh:mm:ss", j.optLong("create_time")));
                        item.setTradeType(j.optString("order_state"));
                        item.setMoney(j.optString("amountString"));
                        item.setTitle(j.optString("withdraw_type"));
                        mList.add(item);
                    }
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