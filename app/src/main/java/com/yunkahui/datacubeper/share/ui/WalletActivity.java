package com.yunkahui.datacubeper.share.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseActivity;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import com.yunkahui.datacubeper.share.adapter.WalletAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/10
 */
public class WalletActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    @Override
    public void initData() {
        int[] imgs = {R.drawable.ic_recharge, R.drawable.ic_withdrawals};
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"明细").setIcon(R.drawable.ic_detail_text).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                startActivity(new Intent(this, TransactionDetailActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet;
    }
}
