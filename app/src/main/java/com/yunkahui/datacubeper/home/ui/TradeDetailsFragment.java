package com.yunkahui.datacubeper.home.ui;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;

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
    private List<MultiItemEntity> list;
    private String mPages;
    private ExpandableTradeRecordAdapter mAdapter;

    @Override
    public void initData() {
        mLogic = new TradeRecordLogic();
        list = new ArrayList<>();
        mLogic.getTradeDetail(mActivity, 20, 1, "wallet", new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                try {
                    Log.e(TAG, "onSuccess: ");
                    JSONObject object = new JSONObject(jsonObject.toString());
                    JSONObject respData = object.getJSONObject("respData");
                    mPages = respData.getString("pages");
                    JSONArray jsonArray = respData.getJSONArray("list");
                    TradeRecordDetail item;
                    TradeRecordSummary summary = new TradeRecordSummary();
                    TradeRecordDetail lastItem = null;
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = new JSONObject(jsonArray.get(i).toString());
                        item = new TradeRecordDetail();
                        item.setTime(com.yunkahui.datacubeper.common.utils.TimeUtils.format("MM-dd hh:mm", j.getLong("create_time")));
                        item.setMoney(j.getString("amountString"));
                        item.setTitle(j.getString("trade_type_ios"));
                        if (lastItem != null) {
                            if (item.getTime().startsWith("0") && lastItem.getTime().startsWith("0") &&
                                    Integer.parseInt(lastItem.getTime().substring(1, 2)) < Integer.parseInt(item.getTime().substring(1, 2))) {
                                list.add(summary);
                                summary = new TradeRecordSummary();
                                summary.getList().add(item);
                            } else if (!item.getTime().startsWith("0") && lastItem.getTime().startsWith("0")) {
                                list.add(summary);
                                summary = new TradeRecordSummary();
                                summary.getList().add(item);
                            } else {
                                summary.getList().add(item);
                            }
                        } else {
                            summary.getList().add(item);
                        }
                        if (i == jsonArray.length() - 1) {
                            list.add(summary);
                        } else {
                            lastItem = item;
                        }
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
        mAdapter = new ExpandableTradeRecordAdapter(list);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setEnableLoadMore(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
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
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_all_trade_record;
    }
}
