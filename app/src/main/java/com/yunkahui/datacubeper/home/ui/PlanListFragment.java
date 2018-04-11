package com.yunkahui.datacubeper.home.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.adapter.MainTabAdapter;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/11
 */
public class PlanListFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;

    @Override
    public void initData() {
        String[] tabTitles = {"自动规", "POS规划", "其他消费"};
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new AutoPlanFragment());
        fragments.add(new PosPlanFragment());
        fragments.add(new OtherConsumerFragment());
        for (String title : tabTitles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(title));
        }
        MainTabAdapter mAdapter = new MainTabAdapter(((AppCompatActivity) mActivity).getSupportFragmentManager(), fragments, tabTitles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initView(View view) {
        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.view_pager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_plan_list;
    }
}
