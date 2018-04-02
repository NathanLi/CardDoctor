package com.yindian.carddoctor.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yindian.carddoctor.R;

import java.util.List;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class MainTabAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<Fragment> mFragments;
    private final int[] mTabImgs;
    private final String[] mTabTitles;

    public MainTabAdapter(FragmentManager fm, Context context, List<Fragment> fragments, int[] tabImgs, String[] tabTitles) {
        super(fm);
        this.mContext = context;
        this.mFragments = fragments;
        this.mTabImgs = tabImgs;
        this.mTabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item_main_tab, null);
        ImageView tabIcon = view.findViewById(R.id.iv_tab_icon);
        TextView tabName =  view.findViewById(R.id.tv_tab_name);
        tabIcon.setImageResource(mTabImgs[position]);
        tabName.setText(mTabTitles[position]);
        if (0 == position) {
            tabIcon.setImageResource(R.drawable.ic_home_selected);
            tabName.setTextColor(ContextCompat.getColor(mContext, R.color.tab_text_select_blue));
        }
        return view;
    }
}
