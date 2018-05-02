package com.yunkahui.datacubeper.mine.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private LinearLayout mLinearLayoutRadio1;
    private TextView mTextViewRadioText1;
    private TextView mTextViewAngle1;
    private LinearLayout mLinearLayoutRadio2;
    private TextView mTextViewRadioText2;
    private TextView mTextViewAngle2;

    private ViewPager mViewPager;

    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message);
        super.onCreate(savedInstanceState);
        setTitle("消息");
    }

    @Override
    public void initData() {
        mFragmentList = new ArrayList<>();
        MessageFragment fragmentNotice = new MessageFragment();
        Bundle bundleNotice = new Bundle();
        bundleNotice.putInt("type", MessageFragment.TYPE_NOTICE);
        fragmentNotice.setArguments(bundleNotice);

        MessageFragment fragmentSystem = new MessageFragment();
        Bundle bundleSystem = new Bundle();
        bundleSystem.putInt("type", MessageFragment.TYPE_SYSTEM);
        fragmentSystem.setArguments(bundleSystem);

        mFragmentList.add(fragmentNotice);
        mFragmentList.add(fragmentSystem);

        InnerViewPagerAdapter adapter = new InnerViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void initView() {
        mLinearLayoutRadio1 = findViewById(R.id.linear_layout_radio_1);
        mLinearLayoutRadio2 = findViewById(R.id.linear_layout_radio_2);
        mTextViewRadioText1 = findViewById(R.id.text_view_radio_text_1);
        mTextViewRadioText2 = findViewById(R.id.text_view_radio_text_2);
        mTextViewAngle1 = findViewById(R.id.text_view_angle_1);
        mTextViewAngle2 = findViewById(R.id.text_view_angle_2);
        mViewPager = findViewById(R.id.view_pager);

        mLinearLayoutRadio1.setOnClickListener(this);
        mLinearLayoutRadio2.setOnClickListener(this);
        mLinearLayoutRadio1.setBackgroundResource(R.drawable.bg_left_button_shape_select);
        mTextViewRadioText1.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_layout_radio_1:
                mLinearLayoutRadio1.setBackgroundResource(R.drawable.bg_left_button_shape_select);
                mTextViewRadioText1.setTextColor(getResources().getColor(R.color.white));
                mLinearLayoutRadio2.setBackgroundResource(R.drawable.bg_right_button_shape_normal);
                mTextViewRadioText2.setTextColor(getResources().getColor(R.color.colorPrimary));
                mViewPager.setCurrentItem(0);
                break;
            case R.id.linear_layout_radio_2:
                mLinearLayoutRadio2.setBackgroundResource(R.drawable.bg_right_button_shape_select);
                mTextViewRadioText2.setTextColor(getResources().getColor(R.color.white));
                mLinearLayoutRadio1.setBackgroundResource(R.drawable.bg_left_button_shape_normal);
                mTextViewRadioText1.setTextColor(getResources().getColor(R.color.colorPrimary));
                mViewPager.setCurrentItem(1);
                break;
        }
    }

    public void setNoticeNuReadNumber(int number) {
        if (number > 0) {
            mTextViewAngle1.setText(number + "");
            mTextViewAngle1.setVisibility(View.VISIBLE);
        } else {
            mTextViewAngle1.setVisibility(View.GONE);
        }
    }

    public void setSystemNuReadNumber(int number) {
        if (number > 0) {
            mTextViewAngle2.setText(number + "");
            mTextViewAngle2.setVisibility(View.VISIBLE);
        } else {
            mTextViewAngle2.setVisibility(View.GONE);
        }
    }

    private class InnerViewPagerAdapter extends FragmentStatePagerAdapter {


        public InnerViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

}
