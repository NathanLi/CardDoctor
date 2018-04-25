package com.yunkahui.datacubeper.bill.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.adapter.MainTabAdapter;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.view.CustomViewPager;
import com.yunkahui.datacubeper.common.view.ExportTipView;
import com.yunkahui.datacubeper.home.ui.DesignSubFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WYF on 2018/4/23/023.
 */
public class BillSyncActivity extends AppCompatActivity implements IActivityStatusBar {

    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bill_sync);
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("bank_card_num"));
    }

    @Override
    public void initData() {
        String[] tabTitles = {"用户名", "卡号"};
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(BillSyncFragment.newInstance(0));
        fragments.add(BillSyncFragment.newInstance(1));
        for (String title : tabTitles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(title));
        }
        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager(), fragments, tabTitles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
