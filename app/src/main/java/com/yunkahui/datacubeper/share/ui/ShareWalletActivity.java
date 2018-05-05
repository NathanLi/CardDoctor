package com.yunkahui.datacubeper.share.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.mine.ui.BindZFBActivity;
import com.yunkahui.datacubeper.share.adapter.WalletAdapter;
import com.yunkahui.datacubeper.share.logic.ShareWalletLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/10
 */
public class ShareWalletActivity extends AppCompatActivity implements IActivityStatusBar {

    private RecyclerView mRecyclerView;

    private ShareWalletLogic mLogic;

    @Override
    public void initData() {
        mLogic = new ShareWalletLogic();
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
                /*if (isQualified) {
                    if (position == 0) {
                        startActivity(new Intent(ShareWalletActivity.this, RechargeForZFBActivity.class)
                                .putExtra("money", money));
                    } else if (position == 1) {
                        startActivity(new Intent(ShareWalletActivity.this, WithdrawForZFBActivity.class)
                                .putExtra("money", money)
                                .putExtra("withdrawType", "00"));
                    }
                } else {
                    Toast.makeText(ShareWalletActivity.this, "还未实名认证或非VIP会员", Toast.LENGTH_SHORT).show();
                }*/
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
        menu.add(1, 1, 1, "提现").setIcon(R.mipmap.ic_withdraw_text).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                checkUserZFB();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //******** 查询支付宝信息 ********
    private void checkUserZFB() {
        mLogic.checkUserZFB(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("支付宝信息->" + baseBean.getJsonObject().toString());
                try {
                    if (RequestUtils.SUCCESS.equals(baseBean.getJsonObject().optString("respCode"))) {
                        startActivity(new Intent(ShareWalletActivity.this, WithdrawForZFBActivity.class)
                                .putExtra("withdrawType", "00")
                                .putExtra("json", baseBean.getJsonObject().toString()));
                    } else {
                        showBindZFBDialog();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Toast.makeText(ShareWalletActivity.this, "获取支付宝信息失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBindZFBDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("尚未绑定支付宝，请前往绑定")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ShareWalletActivity.this, BindZFBActivity.class));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
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
