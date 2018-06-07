package com.yunkahui.datacubeper.share.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.adapter.MainTabAdapter;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.view.CustomViewPager;
import com.yunkahui.datacubeper.home.ui.ProfitIncomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分明细记录
 */
public class IntegralRecordListActivity extends AppCompatActivity implements IActivityStatusBar {

    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_integral_record_list);
        super.onCreate(savedInstanceState);
        setTitle("积分明细");
    }

    @Override
    public void initData() {
        String[] tabTitles = {"收入", "支出"};
        List<Fragment> fragments = new ArrayList<>();

        RecordListFragment recordListFragment1 = new RecordListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", RecordListFragment.TYPE_INTEGRAL_GAIN);
        recordListFragment1.setArguments(bundle);

        RecordListFragment recordListFragment2 = new RecordListFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("type", RecordListFragment.TYPE_INTEGRAL_EXPEND);
        recordListFragment2.setArguments(bundle2);

        fragments.add(recordListFragment1);
        fragments.add(recordListFragment2);
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
