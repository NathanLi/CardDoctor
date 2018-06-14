package com.yunkahui.datacubeper.share.ui;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.bean.WithdrawRecord;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.home.ui.SingleRecordActivity;
import com.yunkahui.datacubeper.share.adapter.RecordMultListAdapter;
import com.yunkahui.datacubeper.share.logic.RecordListLogic;
import com.yunkahui.datacubeper.share.logic.RecordType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 明细列表(新)
 */
public class RecordListNew2Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private InnerListAdapter mAdapter;
    private List<WithdrawRecord.WithdrawDetail> mItemEntities;

    private RecordListLogic mLogic;
    private RecordType mRecordType;
    private long mStartTime;
    private long mEndTime;
    private int mCurrentPage = 1;
    private int mPageSize = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_list_new2, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLogic = new RecordListLogic();
        initRecycler();
        update();
        loadData();
        return view;
    }

    private void initRecycler() {
        mItemEntities = new ArrayList<>();
        mAdapter = new InnerListAdapter(R.layout.layout_list_item_trade_record, mItemEntities);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, mRecyclerView);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setEmptyView(R.layout.layout_no_data);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WithdrawRecord.WithdrawDetail detail = mItemEntities.get(position);
                String action = "";
                switch (mRecordType) {
                    case MyWallet_withdraw:
                        action = "分佣提现";
                        break;
                    case online_withdraw:
                        action = "线上分润提现";
                        break;
                    case balance_withdraw:
                        action = "账户提现";
                        break;
                    case pos_withdraw:
                        action = "POS分润提现";
                        break;
                    case balance_come:
                        action = "账户充值";
                        break;
                }
                String status = getTradeStatus(detail.getOrder_state(), action);
                startActivity(new Intent(getActivity(), SingleRecordActivity.class)
                        .putExtra("time", TimeUtils.format("yyyy-MM-dd hh:mm:ss", detail.getCreate_time()))
                        .putExtra("money", TextUtils.isEmpty(detail.getWithdraw_amount()) ? detail.getAmount() : detail.getWithdraw_amount())
                        .putExtra("status", status)
                        .putExtra("action", action)
                        .putExtra("remarks", TextUtils.isEmpty(detail.getThird_party_msg()) ? detail.getCallback_msg() : detail.getThird_party_msg())
                        .putExtra("fee", String.valueOf(detail.getFee())));
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


    public void update() {
        mCurrentPage = 1;
        mLogic.update();
        mItemEntities.clear();
        mRecordType = ((RecordListActivity) getActivity()).getRecordType();
        mStartTime = ((RecordListActivity) getActivity()).getStartTime();
        mEndTime = ((RecordListActivity) getActivity()).getEndTime();
        loadData();
    }

    private void loadData() {
        switch (mRecordType) {
            case MyWallet_withdraw:
            case online_withdraw:
            case balance_withdraw:
                mLogic.loadWithdrawRecord(getActivity(), mRecordType.getType(), mPageSize, mCurrentPage, mStartTime, mEndTime, new InnerCallBack());
                break;
            case pos_withdraw:
                mLogic.loadPosFenRunData(getActivity(), mPageSize, mCurrentPage, mRecordType.getType(), mStartTime, mEndTime, new InnerPosWithdrawCallBack());
                break;
            case balance_come:
                mLogic.loadRechargeRecord(getActivity(), mRecordType.getType(), mPageSize, mCurrentPage, mStartTime, mEndTime, new InnerCallBack());
                break;
        }

    }


    class InnerCallBack extends SimpleCallBack<BaseBean<WithdrawRecord>> {

        @Override
        public void onSuccess(BaseBean<WithdrawRecord> withdrawRecordBaseBean) {
            if (RequestUtils.SUCCESS.equals(withdrawRecordBaseBean.getRespCode())) {
                mItemEntities.addAll(withdrawRecordBaseBean.getRespData().getList());
                mAdapter.notifyDataSetChanged();
                if (mCurrentPage >= withdrawRecordBaseBean.getRespData().getPages()) {
                    mAdapter.loadMoreEnd();
                } else {
                    mAdapter.loadMoreComplete();
                }
                mCurrentPage++;
            }
        }

        @Override
        public void onFailure(Throwable throwable) {
            mAdapter.loadMoreFail();
            ToastUtils.show(getActivity(), "请求失败 " + throwable.toString());
        }
    }

    //POS分润提现解析
    class InnerPosWithdrawCallBack extends SimpleCallBack<BaseBean> {

        @Override
        public void onSuccess(BaseBean baseBean) {
            if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                List<WithdrawRecord.WithdrawDetail> detailList = new ArrayList<>();
                try {
                    JSONArray array = new JSONObject(baseBean.getJsonObject().toString()).optJSONObject("respData").optJSONArray("list");
                    for (int i = 0; i < array.length(); i++) {
                        WithdrawRecord.WithdrawDetail detail = new WithdrawRecord.WithdrawDetail();
                        JSONObject object = array.optJSONObject(i);
                        detail.setDescr(object.optString("trade_type_desc"));
                        detail.setCreate_time(object.optLong("create_time"));
                        detail.setWithdraw_amount(object.optString("change_amount"));
                        detailList.add(detail);
                    }
                    int pages = new JSONObject(baseBean.getJsonObject().toString()).optJSONObject("respData").optInt("pages");
                    mItemEntities.addAll(detailList);
                    mAdapter.notifyDataSetChanged();
                    if (mCurrentPage >= pages) {
                        mAdapter.loadMoreEnd();
                    } else {
                        mAdapter.loadMoreComplete();
                    }
                    mCurrentPage++;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Throwable throwable) {
            mAdapter.loadMoreFail();
            ToastUtils.show(getActivity(), "请求失败 " + throwable.toString());
        }
    }


    class InnerListAdapter extends BaseQuickAdapter<WithdrawRecord.WithdrawDetail, BaseViewHolder> {

        public InnerListAdapter(int layoutResId, @Nullable List<WithdrawRecord.WithdrawDetail> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, WithdrawRecord.WithdrawDetail item) {
            helper.setText(R.id.tv_title, item.getDescr());
            helper.setText(R.id.tv_time, TimeUtils.format("yyyy-MM-dd HH:mm:ss", item.getCreate_time()));
            helper.setText(R.id.tv_money, TextUtils.isEmpty(item.getWithdraw_amount()) ? item.getAmount() : item.getWithdraw_amount());
            String status;
            int color;
            switch (item.getOrder_state()) {
                case "0":
                    status = TextUtils.isEmpty(item.getAmount()) ? "提现处理中" : "充值处理中";
                    color = Color.parseColor("#0085FF");
                    break;
                case "1":
                    status = TextUtils.isEmpty(item.getAmount()) ? "提现成功" : "充值成功";
                    color = Color.parseColor("#8E8E93");
                    break;
                case "2":
                    status = TextUtils.isEmpty(item.getAmount()) ? "提现失败" : "充值失败";
                    color = Color.parseColor("#FF3B30");
                    break;
                default:
                    status = TextUtils.isEmpty(item.getAmount()) ? "提现处理中" : "充值处理中";
                    color = Color.parseColor("#8E8E93");
                    break;
            }
            helper.setTextColor(R.id.tv_status, color);
            helper.setText(R.id.tv_status, status);
        }

        private GradientDrawable createColorShape(int color, float topLeft, float topRight, float bottomRight, float bottomLeft) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
            drawable.setColor(color);
            return drawable;
        }
    }

}
