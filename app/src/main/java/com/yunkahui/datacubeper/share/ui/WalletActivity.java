package com.yunkahui.datacubeper.share.ui;

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
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.home.ui.RechargeActivity;
import com.yunkahui.datacubeper.home.ui.TradeRecordActivity;
import com.yunkahui.datacubeper.home.ui.WithdrawActivity;
import com.yunkahui.datacubeper.share.adapter.WalletAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/10
 */
public class WalletActivity extends AppCompatActivity implements IActivityStatusBar {

    private RecyclerView mRecyclerView;

    private String mFromPage;

    @Override
    public void initData() {
        mFromPage = getIntent().getStringExtra("from");
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
        TextView tvUserBalance = header.findViewById(R.id.tv_user_balance);
        final String money = getIntent().getStringExtra("money");
        if (money != null) {
            tvUserBalance.setText(money);
        }
        walletAdapter.addHeaderView(header);
        walletAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (isQualified) {
                    if (position == 0) {
                        startActivity(new Intent(WalletActivity.this, RechargeActivity.class)
                                .putExtra("money", money));
                    } else if (position == 1) {
                        startActivity(new Intent(WalletActivity.this, WithdrawActivity.class)
                                .putExtra("money", money)
                                .putExtra("from", mFromPage));
                    }
                } else {
                    Toast.makeText(WalletActivity.this, "还未实名认证或非VIP会员", Toast.LENGTH_SHORT).show();
                }
            }
        });
        walletAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(walletAdapter);
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
                if ("home".equals(mFromPage)) {
                    startActivity(new Intent(this, TradeRecordActivity.class));
                } else if ("share".equals(mFromPage)) {
                    startActivity(new Intent(this, TradeDetailActivity.class));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_wallet);
        super.onCreate(savedInstanceState);
        setTitle("我的钱包");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
