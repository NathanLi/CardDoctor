package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.adapter.MainTabAdapter;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class TradeRecordActivity extends AppCompatActivity implements IActivityStatusBar {

    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_trade_record);
        super.onCreate(savedInstanceState);
        setTitle("交易记录");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void initData() {
        mViewPager.setScanScroll(true);
        String[] tabTitles = {"充值", "提现", "全部"};
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TradeRecordFragment.newInstance(0, TradeRecordFragment.TYPE_RECHARGE_ORDER));
        fragments.add(TradeRecordFragment.newInstance(1, TradeRecordFragment.TYPE_BALANCE_WITHDRAW));
        fragments.add(new TradeDetailsFragment());
        for (String title : tabTitles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(title));
        }
        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager(), fragments, tabTitles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
    }
}
