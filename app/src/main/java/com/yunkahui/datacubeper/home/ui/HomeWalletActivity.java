package com.yunkahui.datacubeper.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.CardSelectorBean;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.home.logic.HomeLogic;
import com.yunkahui.datacubeper.home.logic.HomeWalletLogic;
import com.yunkahui.datacubeper.home.logic.RechargeLogic;
import com.yunkahui.datacubeper.share.adapter.WalletAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/10
 */
public class HomeWalletActivity extends AppCompatActivity implements IActivityStatusBar {

    private final int RESYLT_CODE_UPDATE = 1001;

    private RecyclerView mRecyclerView;
    private TextView mTvUserBalance;

    private HomeWalletLogic mLogic;
    private ArrayList<CardSelectorBean> mList;
    private String mMoney;

    @Override
    public void initData() {
        mLogic = new HomeWalletLogic();
        mList = new ArrayList<>();
        final boolean isQualified = "1".equals(DataUtils.getInfo().getIdentify_status()) && "1".equals(DataUtils.getInfo().getVIP_status());
        int[] icons = {R.mipmap.ic_recharge, R.mipmap.ic_withdrawals};
        String[] titles = {"充值", "提现"};
        List<HomeItem> walletItems = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            HomeItem homeItem = new HomeItem(icons[i], titles[i]);
            walletItems.add(homeItem);
        }
        WalletAdapter walletAdapter = new WalletAdapter(R.layout.layout_list_item_wallet, walletItems);
        View header = LayoutInflater.from(this).inflate(R.layout.layout_list_header_wallet, null);
        mTvUserBalance = header.findViewById(R.id.tv_user_balance);
        mMoney = getIntent().getStringExtra("money");
        if (mMoney != null) {
            mTvUserBalance.setText(mMoney);
        }
        walletAdapter.addHeaderView(header);
        walletAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (isQualified) {
                    if (position == 0) {
                        startActivityForResult(new Intent(HomeWalletActivity.this, RechargeForCardActivity.class)
                                .putExtra("money", mMoney), RESYLT_CODE_UPDATE);
                    } else if (position == 1) {
                        queryCashCardList();
                    }
                } else {
                    Toast.makeText(HomeWalletActivity.this, "还未实名认证或非VIP会员", Toast.LENGTH_SHORT).show();
                }
            }
        });
        walletAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(walletAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESYLT_CODE_UPDATE) {
            initUserFinance();
        }
    }


    //******** 获取余额、分润 ********
    private void initUserFinance() {
        LoadingViewDialog.getInstance().show(this);
        new HomeLogic().loadUserFinance(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("余额分润->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    JSONObject object = baseBean.getJsonObject();
                    JSONObject respData = object.optJSONObject("respData");
                    String userBalance = respData.optString("user_balance");
                    mTvUserBalance.setText(userBalance);
                } else {
                    Toast.makeText(getApplicationContext(), baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Toast.makeText(getApplicationContext(), "获取余额分润失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //******** 获取储蓄卡 ********
    private void queryCashCardList() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.checkCashCard(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("储蓄卡->" + baseBean.getJsonObject().toString());
                JSONObject object = baseBean.getJsonObject();
                CardSelectorBean bean;
                if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                    JSONObject json = object.optJSONObject("respData");
                    bean = new CardSelectorBean();
                    bean.setCardId(json.optInt("Id"));
                    bean.setBankCardName(json.optString("bankcard_name"));
                    bean.setBankCardNum(json.optString("bankcard_num"));
                    bean.setBankCardTel(json.optString("bankcard_tel"));
                    bean.setCardHolder(json.optString("cardholder"));
                    bean.setChecked(false);
                    mList.clear();
                    mList.add(bean);
                    mList.get(0).setChecked(true);
                    startActivity(new Intent(HomeWalletActivity.this, WithdrawForCardActivity.class)
                            .putExtra("title", "余额提现")
                            .putExtra("withdrawType", "02")
                            .putExtra("list", mList));
                } else {
                    Toast.makeText(HomeWalletActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "获取储蓄卡失败 " + throwable.toString());
            }
        });
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "明细").setIcon(R.mipmap.ic_detail_text).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                startActivity(new Intent(this, TradeRecordActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_wallet);
        super.onCreate(savedInstanceState);
        setTitle("我的钱包");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
