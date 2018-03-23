package com.yindian.carddoctor;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yindian.carddoctor.adapter.HomeTabAdapter;
import com.yindian.carddoctor.base.BaseActivity;
import com.yindian.carddoctor.base.CompatStatusBarActivity;
import com.yindian.carddoctor.common.view.SimpleToolbar;
import com.yindian.carddoctor.home.ui.HomeFragment;
import com.yindian.carddoctor.home.ui.Tab2Fragment;
import com.yindian.carddoctor.home.ui.Tab3Fragment;
import com.yindian.carddoctor.home.ui.Tab4Fragment;
import com.yindian.carddoctor.home.ui.Tab5Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private List<Fragment> fragments;

    @Override
    public void initialize() {
        setImmersiveStatusBar(getResources().getColor(R.color.colorPrimary));
        fragments = new ArrayList<>();
        fragments.add(HomeFragment.getInstance("HomeFragment"));
        fragments.add(Tab2Fragment.getInstance("Tab2Fragment"));
        fragments.add(Tab3Fragment.getInstance("Tab3Fragment"));
        fragments.add(Tab4Fragment.getInstance("Tab4Fragment"));
        fragments.add(Tab5Fragment.getInstance("Tab5Fragment"));

        int[] tabImgs = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher};
        String[] tabTitles = {"首页", "账单", "卡·测评", "分享", "我的"};

        //******** 设置适配器、关联ViewPager ********
        HomeTabAdapter mAdapter = new HomeTabAdapter(getSupportFragmentManager(), this, fragments, tabImgs, tabTitles);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(mAdapter.getTabView(i));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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