package com.yunkahui.datacubeper.mine.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements IActivityStatusBar{

    private RadioGroup mRadioGroup;
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
        mFragmentList=new ArrayList<>();
        MessageFragment fragmentNotice=new MessageFragment();
        Bundle bundleNotice=new Bundle();
        bundleNotice.putInt("type",MessageFragment.TYPE_NOTICE);
        fragmentNotice.setArguments(bundleNotice);

        MessageFragment fragmentSystem=new MessageFragment();
        Bundle bundleSystem=new Bundle();
        bundleSystem.putInt("type",MessageFragment.TYPE_SYSTEM);
        fragmentSystem.setArguments(bundleSystem);

        mFragmentList.add(fragmentNotice);
        mFragmentList.add(fragmentSystem);

        InnerViewPagerAdapter adapter=new InnerViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_button_notice:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.radio_button_system:
                        mViewPager.setCurrentItem(1);
                        break;
                }
            }
        });
    }

    @Override
    public void initView() {
        mRadioGroup=findViewById(R.id.radio_group_message);
        mViewPager=findViewById(R.id.view_pager);

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }



    private class InnerViewPagerAdapter extends FragmentStatePagerAdapter{


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
