package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.adapter.MainTabAdapter;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/11
 */
public class TodayOperationActivity extends AppCompatActivity implements IActivityStatusBar {

    private TextView mTvTodayOperation;
    private TextView mTvPlanList;
    private CustomViewPager mViewPager;

    @Override
    public void initData() {
        mTvTodayOperation.setSelected(true);
        mTvPlanList.setSelected(false);
        mTvTodayOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTvTodayOperation.isSelected()) {
                    mTvTodayOperation.setSelected(true);
                    mTvPlanList.setSelected(false);
                    mViewPager.setCurrentItem(0);
                }
            }
        });
        mTvPlanList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTvPlanList.isSelected()) {
                    mTvPlanList.setSelected(true);
                    mTvTodayOperation.setSelected(false);
                    mViewPager.setCurrentItem(1);
                }
            }
        });

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new TodayOperationFragment());
        fragments.add(new PlanListFragment());
        String[] s = {"", ""};
        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager(), fragments, s);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void initView() {
        mTvTodayOperation = findViewById(R.id.tv_today_operation);
        mTvPlanList = findViewById(R.id.tv_plan_list);
        mViewPager = findViewById(R.id.view_pager_content);
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
