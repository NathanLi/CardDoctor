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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import com.yunkahui.datacubeper.home.ui.TradeRecordActivity;
import com.yunkahui.datacubeper.share.adapter.WalletAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/10
 */
public class WalletActivity extends AppCompatActivity implements IActivityStatusBar{

    private String mFrom;
    private RecyclerView mRecyclerView;

    @Override
    public void initData() {
        mFrom = getIntent().getStringExtra("from");
        int[] imgs = {R.mipmap.ic_recharge, R.mipmap.ic_withdrawals};
        String[] strs = {"充值", "提现"};
        List<HomeItem> walletItems = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            HomeItem homeItem = new HomeItem(imgs[i], strs[i]);
            walletItems.add(homeItem);
        }
        WalletAdapter walletAdapter = new WalletAdapter(R.layout.layout_list_item_wallet, walletItems);
        walletAdapter.bindToRecyclerView(mRecyclerView);
        View header = LayoutInflater.from(this).inflate(R.layout.layout_list_header_wallet, null);
        TextView tvAccountBalance = header.findViewById(R.id.tv_account_balance);
        walletAdapter.addHeaderView(header);
        walletAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(WalletActivity.this, RechargeWithdrawalsActivity.class));
            }
        });
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
                if ("home".equals(mFrom)) {
                    startActivity(new Intent(this, TradeRecordActivity.class));
                } else if ("share".equals(mFrom)) {
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
