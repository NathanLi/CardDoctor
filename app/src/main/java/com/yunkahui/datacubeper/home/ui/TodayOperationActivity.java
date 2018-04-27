package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.adapter.MainTabAdapter;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.view.CustomViewPager;
import com.yunkahui.datacubeper.common.view.SegmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/11
 */
public class TodayOperationActivity extends AppCompatActivity implements IActivityStatusBar {

    private SegmentView mSegmentView;
    private CustomViewPager mViewPager;

    @Override
    public void initData() {
        mSegmentView.setOnBackSegmentClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSegmentView.setOnSelectChangeListener(new SegmentView.OnSelectChangeLitener() {
            @Override
            public void onSelectChange(int index, TextView focusView) {
                mViewPager.setCurrentItem(index);
            }
        });

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TodayOperationFragment());
        fragments.add(new PlanListFragment());
        String[] titles = {"", ""};
        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void initView() {
        mSegmentView = findViewById(R.id.segment_view);
        mViewPager = findViewById(R.id.view_pager);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_today_operation);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
