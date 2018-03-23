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

public class HomeTabAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> fragments;
    private final int[] tabImgs;
    private final String[] tabTitles;

    public HomeTabAdapter(FragmentManager fm, Context context, List<Fragment> fragments, int[] tabImgs, String[] tabTitles) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.tabImgs = tabImgs;
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_main_tab_item, null);
        ImageView tabIcon = view.findViewById(R.id.iv_tab_icon);
        TextView tabName =  view.findViewById(R.id.tv_tab_name);
        tabIcon.setImageResource(tabImgs[position]);
        tabName.setText(tabTitles[position]);
        if (0 == position) {
            tabIcon.setImageResource(R.drawable.ic_home_selected);
            tabName.setTextColor(ContextCompat.getColor(context, R.color.tab_text_select_blue));
        }
        return view;
    }
}
