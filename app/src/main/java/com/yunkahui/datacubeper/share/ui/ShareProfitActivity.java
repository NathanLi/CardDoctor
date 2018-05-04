package com.yunkahui.datacubeper.share.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.adapter.MainTabAdapter;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.CardSelectorBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.CustomViewPager;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.home.logic.HomeProfitLogic;
import com.yunkahui.datacubeper.home.ui.HomeProfitActivity;
import com.yunkahui.datacubeper.home.ui.ProfitIncomeFragment;
import com.yunkahui.datacubeper.home.ui.ProfitWithdrawFragment;
import com.yunkahui.datacubeper.home.ui.WithdrawForCardActivity;
import com.yunkahui.datacubeper.mine.ui.BindZFBActivity;
import com.yunkahui.datacubeper.share.logic.ShareProfitLogic;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/10
 */
public class ShareProfitActivity extends AppCompatActivity implements IActivityStatusBar {

    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;

    private ShareProfitLogic mLogic;
    private ArrayList<CardSelectorBean> mList;

    @Override
    public void initData() {
        mLogic = new ShareProfitLogic();
        mList = new ArrayList<>();

        String[] tabTitles = {"分润收入", "分润提现"};
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ProfitIncomeFragment());
        fragments.add(new ProfitWithdrawFragment());
        for (String title : tabTitles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(title));
        }
        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager(), fragments, tabTitles);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setScanScroll(true);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
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
                        startActivity(new Intent(ShareProfitActivity.this, WithdrawForZFBActivity.class)
                                .putExtra("withdrawType", "01")
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
                Toast.makeText(ShareProfitActivity.this, "获取支付宝信息失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBindZFBDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("尚未绑定支付宝，请前往绑定")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ShareProfitActivity.this, BindZFBActivity.class));
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
        setContentView(R.layout.activity_home_profit);
        super.onCreate(savedInstanceState);
        setTitle("累计分润");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
