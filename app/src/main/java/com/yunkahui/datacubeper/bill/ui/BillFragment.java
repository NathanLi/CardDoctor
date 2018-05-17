package com.yunkahui.datacubeper.bill.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.yunkahui.datacubeper.bill.logic.BillSynchronousLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.home.ui.TodayOperationActivity;
import com.yunkahui.datacubeper.mine.logic.MineLogic;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class BillFragment extends BaseFragment implements View.OnClickListener {

    private final int RESULT_CODE_ADD = 1001;
    private final int RESULT_CODE_FAIL_CARD = 1002;

    private RecyclerView mRecyclerView;
    private View mLlPromptAddCard;
    private TextView mTvCardCount;
    private TextView mTvRepayCount;
    private TextView mTvUnRepayCount;
    private TextView mTvShowNum;
    private TextView mTextViewWarning;

    private BillLogic mLogic;
    private BillCardListAdapter mAdapter;
    private List<BillCreditCard.CreditCard> mList;
    private int mFailCardNum;

    public static Fragment getInstance(String data) {
        BillFragment f = new BillFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void initData() {
        mLogic = new BillLogic();
        mList = new ArrayList<>();
        mAdapter = new BillCardListAdapter(R.layout.layout_list_item_bill_card, mList);
        mAdapter.bindToRecyclerView(mRecyclerView);
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_list_header_bill, null);
        mTvCardCount = headerView.findViewById(R.id.tv_card_count);
        mTvRepayCount = headerView.findViewById(R.id.tv_repay_count);
        mTvUnRepayCount = headerView.findViewById(R.id.tv_unrepay_count);
        mTvShowNum = headerView.findViewById(R.id.tv_show_num);
        headerView.findViewById(R.id.show_today).setOnClickListener(this);
        mAdapter.addHeaderView(headerView);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (mList.get(position) == null) {
                    return;
                }
                switch (view.getId()) {
                    case R.id.btn_bill_sync:
                        if(BillSynchronousLogic.judgeBank(mList.get(position).getBankCardName()).length==0){
                            ToastUtils.show(getActivity(),"暂不支持该银行");
                            return;
                        }
                        Intent intent = new Intent(getActivity(), BillSynchronousActivity.class);
                        intent.putExtra("bank_card_name", mList.get(position).getBankCardName());
                        intent.putExtra("bank_card_num", mList.get(position).getBankCardNum());
                        startActivity(intent);
                        break;
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mList.get(position) == null) {
                    return;
                }
                final String itemTime = TimeUtils.format("yyyy-MM-dd", mList.get(position).getRepayDayDate());
                startActivity(new Intent(getActivity(), BillDetailActivity.class)
                        .putExtra("title", mList.get(position).getBankCardName())
                        .putExtra("user_credit_card_id", mList.get(position).getUserCreditCardId())
                        .putExtra("card_holder", mList.get(position).getCardHolder())
                        .putExtra("bank_card_num", mList.get(position).getBankCardNum())
                        .putExtra("bank_card_name",mList.get(position).getBankCardName())
                        .putExtra("reday_date", itemTime.substring(5))
                        .putExtra("bill_date", mList.get(position).getBillDayDate()));
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.e("长安" + position);
                if (mList.get(position) == null) {
                    return true;
                }
                showDeleteDialog(mList.get(position).getUserCreditCardId());
                return true;
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCreditCardList();
        loadFailCardNum();
    }

    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setRightIcon(R.mipmap.icon_add)
                .setRightIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ("0".equals(DataUtils.getInfo().getVIP_status())) {
                            ToastUtils.show(getActivity(), "请先升级VIP");
                        } else {
                            checkRealNameAuthStatus();
                        }
                    }
                });
        toolbar.setTitleName(getString(R.string.bill));
        mTextViewWarning = view.findViewById(R.id.text_view_warning);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLlPromptAddCard = view.findViewById(R.id.ll_prompt_add_card);
        view.findViewById(R.id.tv_bind_card).setOnClickListener(this);
        mTextViewWarning.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bill;
    }

    //查询失败卡片数量
    public void loadFailCardNum() {
        mLogic.queryCardCountOflanFailed(mActivity, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("失败的卡片数量-->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mFailCardNum = baseBean.getJsonObject().optJSONObject("respData").optInt("failCardNum");
                    if (mFailCardNum > 0) {
                        mTextViewWarning.setVisibility(View.VISIBLE);
                        mTextViewWarning.setText("紧急：有" + mFailCardNum + "张卡片交易关闭，请前往处理");
                    } else {
                        mTextViewWarning.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK && requestCode == RESULT_CODE_ADD) {

            switch (requestCode) {
                case RESULT_CODE_ADD:
//                    getCreditCardList();
                    break;
                case RESULT_CODE_FAIL_CARD:
                    loadFailCardNum();
                    break;
            }
        }
    }

    //提示删除信用卡弹窗
    private void showDeleteDialog(final int cardId) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage("确定删除改卡片?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCreditCard(cardId);
                    }
                })
                .setNeutralButton("取消", null)
                .create();
        dialog.show();
    }

    //删除信用卡
    private void deleteCreditCard(int cardId) {
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.deleteCreditCard(getActivity(), cardId, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("删除信用卡->" + baseBean.getJsonObject().toString());
                ToastUtils.show(getActivity(), baseBean.getRespDesc());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    getCreditCardList();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity(), "请求失败 " + throwable.toString());
            }
        });
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
                    mTvShowNum.setText(baseBean.getRespData().getPlanCount() + "");
                    mTvCardCount.setText(baseBean.getRespData().getCardCount() + "\n卡片数");
                    mTvRepayCount.setText(baseBean.getRespData().getPayOffCount() + "\n已还清");
                    mTvUnRepayCount.setText(baseBean.getRespData().getCardCount() - baseBean.getRespData().getPayOffCount() + "\n未还款");
                    mList.clear();
                    if (details != null) {
                        if (details.size() > 0) {
                            mList.addAll(details);
                            mLlPromptAddCard.setVisibility(View.GONE);
                        } else {
                            mList.add(null);
                            mLlPromptAddCard.setVisibility(View.VISIBLE);
                        }
                    } else {
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
                if ("0".equals(DataUtils.getInfo().getVIP_status())) {
                    ToastUtils.show(getActivity(), "请先升级VIP");
                } else {
                    checkRealNameAuthStatus();
                }
                break;
            case R.id.show_today:
                startActivity(new Intent(mActivity, TodayOperationActivity.class));
                break;
            case R.id.text_view_warning:
                Intent intent = new Intent(getActivity(), FailCardListActivity.class);
                intent.putExtra("num", mFailCardNum);
                startActivityForResult(intent, RESULT_CODE_FAIL_CARD);
                break;
        }
    }
}