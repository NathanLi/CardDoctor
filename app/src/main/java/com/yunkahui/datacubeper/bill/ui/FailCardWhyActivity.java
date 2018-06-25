package com.yunkahui.datacubeper.bill.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.adapter.FailCardPlanListAdapter;
import com.yunkahui.datacubeper.bill.logic.FailCardListLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.FailBankCard;
import com.yunkahui.datacubeper.common.bean.FailBankCardDetail;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import java.util.ArrayList;
import java.util.List;

public class FailCardWhyActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private final int RESULT_CODE_UPDATE = 1001;

    private TextView mTextViewWhy;
    private TextView mTextViewSolve;
    private RecyclerView mRecyclerView;

    private FailBankCard mFailBankCard;
    private List<FailBankCardDetail.Plan> mPlanList;
    private FailCardPlanListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fail_card_why);
        super.onCreate(savedInstanceState);
        setTitle("交易关闭原因");

    }

    @Override
    public void initData() {
        mFailBankCard = getIntent().getParcelableExtra("bean");
        mPlanList = new ArrayList<>();
        if (mFailBankCard != null) {
            initCard();
            loadData();
        }
        mAdapter = new FailCardPlanListAdapter(R.layout.layout_list_item_generate_data, mPlanList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initView() {
        mTextViewWhy = findViewById(R.id.text_view_why);
        mTextViewSolve = findViewById(R.id.text_view_solve);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_CODE_UPDATE) {
            mFailBankCard.setIs_restart(1);
            mFailBankCard.setIs_replanning(1);
            initCard();
            setResult(RESULT_OK);
        }
    }

    private void initCard() {
        ImageView imageViewIcon = findViewById(R.id.image_view_icon);
        TextView textViewTitle = findViewById(R.id.text_view_title);
        TextView textViewRightDesc = findViewById(R.id.text_view_right_desc);
        Button buttonSubmit1 = findViewById(R.id.button_submit_1);
        Button buttonSubmit2 = findViewById(R.id.button_submit_2);

        buttonSubmit1.setOnClickListener(this);
        buttonSubmit2.setOnClickListener(this);
        textViewRightDesc.setOnClickListener(this);

        imageViewIcon.setImageResource(DataUtils.getBankIconMap().get(mFailBankCard.getBankcard_name()));
        textViewTitle.setText(mFailBankCard.getBankcard_name() + "[" + mFailBankCard.getBankcard_num() + "]");
        textViewRightDesc.setText("详情 >");
        textViewRightDesc.setTextColor(getResources().getColor(R.color.text_color_gray_949494));
        if (mFailBankCard.getIs_restart() == 0) {
            buttonSubmit1.setAlpha(0.5f);
            buttonSubmit1.setEnabled(false);
        }
        if (mFailBankCard.getIs_replanning() == 0) {
            buttonSubmit2.setAlpha(0.5f);
            buttonSubmit2.setEnabled(false);
        }

    }

    private void loadData() {
        LoadingViewDialog.getInstance().show(this);
        new FailCardListLogic().loadFailCardDetail(this, mFailBankCard.getBankcard_id(), new SimpleCallBack<BaseBean<FailBankCardDetail>>() {
            @Override
            public void onSuccess(BaseBean<FailBankCardDetail> baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("规划卡片失败详情->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mPlanList.clear();
                    mPlanList.addAll(baseBean.getRespData().getAutoplanning());
                    mTextViewWhy.setText(baseBean.getRespData().getQuestion());
                    mTextViewSolve.setText(baseBean.getRespData().getDeal());
                    mAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }

    //根据ID查询卡片信息
    public void loadBankCardDataForId(final int id, final String cardName, final String cardNum, final int type) {
        LoadingViewDialog.getInstance().show(this);
        new FailCardListLogic().loadBankCardDataForId(this, id, new SimpleCallBack<BaseBean<BillCreditCard>>() {
            @Override
            public void onSuccess(BaseBean<BillCreditCard> baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("卡片信息->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    BillCreditCard.CreditCard creditCard = baseBean.getRespData().getCardDetail().get(0);
                    switch (type) {
                        case 1:
                            startActivityForResult(new Intent(FailCardWhyActivity.this, AutoPlanActivity.class)
                                    .putExtra("time", creditCard.getRepayDayDate())
                                    .putExtra("user_credit_card_id", id)
                                    .putExtra("bank_card_num", cardNum)
                                    .putExtra("bank_card_name", cardName),RESULT_CODE_UPDATE);
                            break;
                        case 2:
                            startActivity(new Intent(FailCardWhyActivity.this, BillDetailActivity.class)
                                    .putExtra("user_credit_card_id", creditCard.getUserCreditCardId())
                                    .putExtra("card_holder", creditCard.getCardHolder())
                                    .putExtra("bank_card_num", creditCard.getBankCardNum())
                                    .putExtra("bank_card_name",creditCard.getBankCardName())
                                    .putExtra("reday_date", TimeUtils.format("MM-dd", creditCard.getRepayDayDate()))
                                    .putExtra("bill_date", creditCard.getBillDayDate()));
                            break;
                    }

                } else {
                    ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit_1:
                startActivityForResult(new Intent(FailCardWhyActivity.this,
                        ActivatePlanActivity.class).putExtra("id", mFailBankCard.getBankcard_id()), RESULT_CODE_UPDATE);
                break;
            case R.id.button_submit_2:
                loadBankCardDataForId(mFailBankCard.getBankcard_id(), mFailBankCard.getBankcard_num(), mFailBankCard.getBankcard_name(), 1);
                break;
            case R.id.text_view_right_desc:
                loadBankCardDataForId(mFailBankCard.getBankcard_id(), mFailBankCard.getBankcard_num(), mFailBankCard.getBankcard_name(), 2);
                break;
        }
    }

}
