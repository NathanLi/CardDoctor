package com.yunkahui.datacubeper.bill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.bill.adapter.BillCardListAdapter;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.bill.logic.BillLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.home.ui.TodayOperationActivity;
import com.yunkahui.datacubeper.mine.logic.MineLogic;
import com.yunkahui.datacubeper.mine.ui.RealNameAuthActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class BillFragment extends BaseFragment implements View.OnClickListener {

    private final int RESULT_CODE_ADD = 1001;

    private RecyclerView mRecyclerView;
    private View mLlPromptAddCard;
    private TextView mTvCardCount;
    private TextView mTvRepayCount;
    private TextView mTvUnRepayCount;
    private TextView mTextViewPlan;

    private BillLogic mLogic;
    private BillCardListAdapter mAdapter;
    private List<BillCreditCard.CreditCard> mList;

    public static Fragment getInstance(String data) {
        BillFragment f = new BillFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void initData() {
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
        mLogic = new BillLogic();
        mList = new ArrayList<>();
        mAdapter = new BillCardListAdapter(mActivity, R.layout.layout_list_item_bill_card, mList);
        mAdapter.bindToRecyclerView(mRecyclerView);
        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.layout_list_header_bill, null);
        mTvCardCount = headerView.findViewById(R.id.tv_card_count);
        mTvRepayCount = headerView.findViewById(R.id.tv_repay_count);
        mTvUnRepayCount = headerView.findViewById(R.id.tv_unrepay_count);
        mTextViewPlan = headerView.findViewById(R.id.text_view_plan);
        headerView.findViewById(R.id.btn_today_operation).setOnClickListener(this);
        mAdapter.addHeaderView(headerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (mList.get(position) == null) {
                    return;
                }
                switch (view.getId()) {
                    case R.id.btn_bill_sync:
                        List<String> tabs = new ArrayList<>();
                        tabs.add("用户名");
                        tabs.add("卡号");
                        Intent intent = new Intent(getActivity(), BillSynchronousActivity.class);
                        intent.putExtra("title", mList.get(position).getBankCardName());
                        intent.putStringArrayListExtra("tabs", (ArrayList<String>) tabs);
                        startActivity(intent);
                        break;
                }
            }
        });
        getCreditCardList();
    }


    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setRightIcon(R.mipmap.icon_add)
                .setRightIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkRealNameAuthStatus();
                    }
                });
        toolbar.setTitleName(getString(R.string.bill));
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLlPromptAddCard = view.findViewById(R.id.ll_prompt_add_card);
        view.findViewById(R.id.tv_bind_card).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bill;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK && requestCode == RESULT_CODE_ADD) {
            getCreditCardList();
        }
    }

    //******** 查询信用卡列表 ********
    private void getCreditCardList() {
        mLogic.queryCreditCardList(mActivity, new SimpleCallBack<BaseBean<BillCreditCard>>() {
            @Override
            public void onSuccess(BaseBean<BillCreditCard> baseBean) {
                LogUtils.e("账单->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    DataUtils.setRealName(baseBean.getRespData().getTrueName());
                    List<BillCreditCard.CreditCard> details = baseBean.getRespData().getCardDetail();
                    mTextViewPlan.setText(baseBean.getRespData().getPlanCount() + "");
                    mTvCardCount.setText(baseBean.getRespData().getCardCount() + "");
                    mTvRepayCount.setText(baseBean.getRespData().getPayOffCount() + "");
                    mTvUnRepayCount.setText(baseBean.getRespData().getCardCount() - baseBean.getRespData().getPayOffCount() + "");
                    if (details != null) {
                        mList.clear();
                        if (details.size() > 0) {
                            mList.addAll(details);
                        } else {
                            mList.add(null);
                            mLlPromptAddCard.setVisibility(View.VISIBLE);
                        }
                    }else{
                        mList.add(null);
                        mLlPromptAddCard.setVisibility(View.VISIBLE);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    mList.add(null);
                    mAdapter.notifyDataSetChanged();
                    mLlPromptAddCard.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(mActivity, "获取卡列表失败", Toast.LENGTH_SHORT).show();
                mLlPromptAddCard.setVisibility(View.VISIBLE);
            }
        });
    }

    //查询实名认证状态
    private void checkRealNameAuthStatus() {
        LoadingViewDialog.getInstance().show(getActivity());
        new MineLogic().checkRealNameAuthStatus(getActivity(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("查询实名认证状态->" + baseBean.getJsonObject().toString());
                try {
                    JSONObject object = baseBean.getJsonObject();
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        switch (object.optJSONObject("respData").optString("status")) {
                            case "1":
                                DataUtils.setRealName(object.optJSONObject("respData").optString("true_name"));
                                startActivityForResult(new Intent(mActivity, AddCardActivity.class), RESULT_CODE_ADD);
                                break;
                            default:
                                ToastUtils.show(getActivity(), "请先实名认证");
                                break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("查询实名认证状态失败->" + throwable.toString());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bind_card:
                checkRealNameAuthStatus();
                break;
            case R.id.btn_today_operation:
                startActivity(new Intent(mActivity, TodayOperationActivity.class));
                break;
        }
    }
}