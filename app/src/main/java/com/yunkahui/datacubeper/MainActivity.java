package com.yunkahui.datacubeper;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunkahui.datacubeper.adapter.MainTabAdapter;
import com.yunkahui.datacubeper.base.BaseActivity;
import com.yunkahui.datacubeper.home.ui.HomeFragment;
import com.yunkahui.datacubeper.bill.BillFragment;
import com.yunkahui.datacubeper.test.CardTestFragment;
import com.yunkahui.datacubeper.share.ShareFragment;
import com.yunkahui.datacubeper.mine.MineFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页框架
 */
public class MainActivity extends BaseActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Fragment> mFragments;

    @Override
    public void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.getInstance("HomeFragment"));
        mFragments.add(BillFragment.getInstance("BillFragment"));
        mFragments.add(CardTestFragment.getInstance("CardTestFragment"));
        mFragments.add(ShareFragment.getInstance("ShareFragment"));
        mFragments.add(MineFragment.getInstance("MineFragment"));

        int[] tabImgs = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher};
        String[] tabTitles = {"首页", "账单", "卡·测评", "分享", "我的"};

        //******** 设置适配器、关联ViewPager ********
        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager(), this, mFragments, tabImgs, tabTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setCustomView(mAdapter.getTabView(i));
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTabSelectedState(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabUnSelectedState(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void initView() {
        setImmersiveStatusBar(getResources().getColor(R.color.colorPrimary));
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
    }

    //******** 设置选中tab状态 ********
    private void setTabSelectedState(TabLayout.Tab tab) {
        View customView = tab.getCustomView();
        ImageView tabIcon = customView.findViewById(R.id.iv_tab_icon);
        TextView tabText = customView.findViewById(R.id.tv_tab_name);
        tabText.setTextColor(ContextCompat.getColor(this, R.color.tab_text_select_blue));
        String s = tabText.getText().toString();
        if (getString(R.string.tab_item_home).equals(s)) {
            tabIcon.setImageResource(R.drawable.ic_home_selected);
        } else if (getString(R.string.tab_item_bill).equals(s)) {
            tabIcon.setImageResource(R.drawable.ic_bill_normal);
        } else if (getString(R.string.tab_item_card_test).equals(s)) {
            tabIcon.setImageResource(R.drawable.ic_card_test_normal);
        } else if (getString(R.string.tab_item_share).equals(s)) {
            tabIcon.setImageResource(R.drawable.ic_share_normal);
        } else if (getString(R.string.tab_item_me).equals(s)) {
            tabIcon.setImageResource(R.drawable.ic_me_normal);
        }
    }

    //******** 设置未选中tab状态 ********
    private void setTabUnSelectedState(TabLayout.Tab tab) {
        View customView = tab.getCustomView();
        ImageView tabIcon = customView.findViewById(R.id.iv_tab_icon);
        TextView tabText = customView.findViewById(R.id.tv_tab_name);
        tabText.setTextColor(ContextCompat.getColor(this, R.color.tab_text_normal_gray));
        String s = tabText.getText().toString();
        if (getString(R.string.tab_item_home).equals(s)) {
            tabIcon.setImageResource(R.drawable.ic_launcher);
        } else if (getString(R.string.tab_item_bill).equals(s)) {
            tabIcon.setImageResource(R.drawable.ic_launcher);
        } else if (getString(R.string.tab_item_card_test).equals(s)) {
            tabIcon.setImageResource(R.drawable.ic_launcher);
        } else if (getString(R.string.tab_item_share).equals(s)) {
            tabIcon.setImageResource(R.drawable.ic_launcher);
        } else if (getString(R.string.tab_item_me).equals(s)) {
            tabIcon.setImageResource(R.drawable.ic_launcher);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}