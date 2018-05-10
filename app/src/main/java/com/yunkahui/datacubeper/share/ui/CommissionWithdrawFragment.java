package com.yunkahui.datacubeper.share.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.GlideApp;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.home.ui.SingleRecordActivity;
import com.yunkahui.datacubeper.share.logic.ShareWalletLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * 分佣提现明细
 */
public class CommissionWithdrawFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private ShareWalletLogic mLogic;


    private int mSize = 10;
    private int mPage = 1;
    private List<TradeRecordDetail> mDetailList;
    private CommissionWithdrawListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commission_withdraw, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLogic = new ShareWalletLogic();
        mDetailList = new ArrayList<>();
        mAdapter = new CommissionWithdrawListAdapter(R.layout.layout_list_item_trade_record, mDetailList);
        initRecyclerView();
        loadData();
        return view;
    }

    private void initRecyclerView() {
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setEmptyView(R.layout.layout_no_data);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TradeRecordDetail detail=mDetailList.get(position);
                String action = "分佣提现";
                String time = TimeUtils.format("yyyy-MM-dd hh:mm:ss", detail.getTimeStamp());
                String status = "";
                if ("0".equals(detail.getOrderStatus())) {
                    status = "提现处理中";
                } else if ("1".equals(detail.getOrderStatus())) {
                    status = "提现成功";
                } else if ("2".equals(detail.getOrderStatus())) {
                    status = "提现失败";
                }
                startActivity(new Intent(getActivity(), SingleRecordActivity.class)
                        .putExtra("time", time)
                        .putExtra("money", detail.getMoney())
                        .putExtra("status", status)
                        .putExtra("action", action)
                        .putExtra("remarks", detail.getRemark()));
            }
        });
    }


    public void loadData() {
        mLogic.loadCommissionRecord(getActivity(), mSize, mPage, "comm_withdraw_order", new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("分佣提现明细->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    List<TradeRecordDetail> details = mLogic.parsingCommossionRecord(baseBean.getJsonObject().optJSONObject("respData").optJSONArray("list").toString());
                    mDetailList.addAll(details);
                    mPage++;
                    mAdapter.notifyDataSetChanged();
                    int sumPage = baseBean.getJsonObject().optJSONObject("respData").optInt("pages");
                    if (sumPage > mPage) {
                        mAdapter.loadMoreComplete();
                    } else {
                        mAdapter.loadMoreEnd();
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                ToastUtils.show(getActivity(), "请求失败 " + throwable.toString());
                mAdapter.loadMoreFail();
            }
        });
    }


    class CommissionWithdrawListAdapter extends BaseQuickAdapter<TradeRecordDetail, BaseViewHolder> {

        public CommissionWithdrawListAdapter(int layoutResId, @Nullable List<TradeRecordDetail> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TradeRecordDetail item) {

            helper.setText(R.id.tv_title, item.getTitle());
            helper.setText(R.id.tv_time, item.getTime());
            helper.setText(R.id.tv_money, item.getMoney());

            switch (item.getOrderStatus()) {
                case "0":
                    helper.setText(R.id.tv_status, "提现处理中");
                    helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.colorPrimary));
                    break;
                case "1":
                    helper.setText(R.id.tv_status, "提现成功");
                    helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.text_color_gray_949494));
                    break;
                case "2":
                    helper.setText(R.id.tv_status, "提现失败");
                    helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.red));
                    break;
            }

        }
    }

}
