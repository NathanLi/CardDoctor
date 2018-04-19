package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.CardSelectorBean;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.home.adapter.RechargeAdapter;
import com.yunkahui.datacubeper.home.logic.RechargeLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/10
 */
public class RechargeActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private static final String TAG = "RechargeActivity";
    private RechargeLogic mLogic;
    private TextView mTvUserBalance;
    private List<CardSelectorBean> mList;
    private LinearLayout mLlShowDialog;
    private RechargeAdapter mAdapter;

    @Override
    public void initData() {
        if (getIntent().getStringExtra("money") != null) {
            mTvUserBalance.setText(getIntent().getStringExtra("money"));
        }

        mLogic = new RechargeLogic();
        mList = new ArrayList<>();

        LoadingViewDialog.getInstance().show(this);
        mLogic.queryCreditCardList(this, new SimpleCallBack<BaseBean<BillCreditCard>>() {
            @Override
            public void onSuccess(BaseBean<BillCreditCard> baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                mList.clear();
                CardSelectorBean bean;
                for (BillCreditCard.CreditCard item : baseBean.getRespData().getCardDetail()) {
                    bean = new CardSelectorBean();
                    bean.setBankCardName(item.getBankCardName());
                    bean.setBankCardNum(String.format(getResources().getString(R.string.bank_card_tail_num), item.getBankCardNum().substring(item.getBankCardNum().length() - 4, item.getBankCardNum().length())));
                    bean.setChecked(false);
                    mList.add(bean);
                }
                mList.get(0).setChecked(true);
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Log.e(TAG, "onFailure: "+throwable.getMessage());
                Toast.makeText(RechargeActivity.this, "连接超时", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initView() {
        mTvUserBalance = findViewById(R.id.tv_user_balance);
        mLlShowDialog = findViewById(R.id.ll_show_dialog);
        mLlShowDialog.setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_recharge);
        super.onCreate(savedInstanceState);
        setTitle("余额充值");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_show_dialog:
                showSelectCardDialog();
                break;
        }
    }

    private void showSelectCardDialog() {
        if (mList != null && mList.size() == 0)
            Toast.makeText(this, "获取银行卡失败", Toast.LENGTH_SHORT).show();
        RecyclerView recyclerView = new RecyclerView(this);
        mAdapter = new RechargeAdapter(this, R.layout.layout_list_item_card_select, mList);
        mAdapter.bindToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("选择银行卡片")
                .setView(recyclerView)
                .show();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                allUnChecked();
                mList.get(position).setChecked(true);
                dialog.dismiss();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void allUnChecked() {
        for (CardSelectorBean bean : mList) {
            bean.setChecked(false);
        }
    }
}
