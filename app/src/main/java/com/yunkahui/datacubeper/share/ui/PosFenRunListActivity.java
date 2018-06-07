package com.yunkahui.datacubeper.share.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.adapter.MainTabAdapter;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.view.CustomViewPager;
import com.yunkahui.datacubeper.home.ui.ProfitIncomeFragment;
import com.yunkahui.datacubeper.home.ui.ProfitWithdrawFragment;
import com.yunkahui.datacubeper.share.logic.ShareProfitLogic;

import java.util.ArrayList;
import java.util.List;

public class PosFenRunListActivity extends AppCompatActivity implements IActivityStatusBar {

    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pos_fen_run_list);
        super.onCreate(savedInstanceState);
        setTitle("POS分润");
    }

    @Override
    public void initData() {

        String[] tabTitles = {"分润收入", "分润提现"};
        List<Fragment> fragments = new ArrayList<>();

        ProfitIncomeFragment profitIncomeFragment = new ProfitIncomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", ProfitIncomeFragment.TYPE_POS_FEN_RUN);
        profitIncomeFragment.setArguments(bundle);

        RecordListFragment recordListFragment = new RecordListFragment();
        Bundle recordBundle = new Bundle();
        recordBundle.putString("type", RecordListFragment.TYPE_POS_FEN_RUN_WITHDRAW);
        recordListFragment.setArguments(recordBundle);

        fragments.add(profitIncomeFragment);
        fragments.add(recordListFragment);
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
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
