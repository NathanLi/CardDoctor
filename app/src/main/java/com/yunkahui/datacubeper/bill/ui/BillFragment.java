package com.yunkahui.datacubeper.bill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.bill.adapter.BillCardListAdapter;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.bill.logic.BillLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.view.BillCardView;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.home.ui.TodayOperationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class BillFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "BillFragment";
    private BillLogic mLogic;
    private RecyclerView mRecyclerView;
    private List<BillCreditCard.CreditCard> list = new ArrayList<>();
    private View mLlPromptAddCard;

    public static Fragment getInstance(String data){
        BillFragment f = new BillFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data",data);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void initData() {
        mLogic = new BillLogic();
        // TODO: 2018/4/16 0016 查询规划失败列表
        /*mLogic.queryCardCountOflanFailed(mActivity, new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                Log.e(TAG, "queryCardCountOfPlanFailed onSuccess: " + jsonObject.toString());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "queryCardCountOfPlanFailed onFailure: " + throwable.getMessage());
            }
        });*/
        final BillCardListAdapter adapter = new BillCardListAdapter(mActivity, R.layout.layout_list_item_bill_card, list);
        adapter.bindToRecyclerView(mRecyclerView);
        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.layout_list_header_bill, null);
        TextView tvCardCount = headerView.findViewById(R.id.tv_card_count);
        TextView tvRepayCount = headerView.findViewById(R.id.tv_repay_count);
        TextView tvUnRepayCount = headerView.findViewById(R.id.tv_unrepay_count);
        Button btnTodayOperation = headerView.findViewById(R.id.btn_today_operation);
        btnTodayOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity, TodayOperationActivity.class));
            }
        });
        adapter.addHeaderView(headerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(adapter);
        mLogic.queryCreditCardList(mActivity, new SimpleCallBack<BaseBean<BillCreditCard>>() {
            @Override
            public void onSuccess(BaseBean<BillCreditCard> baseBean) {
                LogUtils.e("账单->"+baseBean.getJsonObject().toString());
                if(RequestUtils.SUCCESS.equals(baseBean.getRespCode())){
                    List<BillCreditCard.CreditCard> details = baseBean.getRespData().getCardDetail();
                    list.clear();
                    if (details.size() > 0) {
                        list.addAll(details);
                    } else {
                        list.add(null);
                        mLlPromptAddCard.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: "+throwable.getMessage());
                mLlPromptAddCard.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setRightIcon(R.mipmap.icon_add)
                .setRightIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mActivity, AddCardActivity.class));
                    }
                });
        toolbar.setTitleName(getString(R.string.tab_item_bill));
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLlPromptAddCard = view.findViewById(R.id.ll_prompt_add_card);
        view.findViewById(R.id.tv_bind_card).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bill;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bind_card:
                startActivity(new Intent(mActivity, AddCardActivity.class));
                break;
        }
    }
}