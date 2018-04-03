package com.yunkahui.datacubeper.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

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

}
