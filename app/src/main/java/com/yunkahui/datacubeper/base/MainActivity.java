package com.yunkahui.datacubeper.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.adapter.MainTabAdapter;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.home.ui.HomeFragment;
import com.yunkahui.datacubeper.bill.BillFragment;
import com.yunkahui.datacubeper.test.CardTestFragment;
import com.yunkahui.datacubeper.share.ui.ShareFragment;
import com.yunkahui.datacubeper.mine.ui.MineFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页框架
 */
public class MainActivity extends AppCompatActivity implements IActivityStatusBar {

    private ViewPager mViewPager;
    private List<BaseFragment> mFragments;
    private RadioGroup radioGroup;
    private long mTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void initView() {
        mViewPager = findViewById(R.id.view_pager);
        radioGroup = findViewById(R.id.radio_group);
    }

    @Override
    public void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new BillFragment());
        mFragments.add(new CardTestFragment());
        mFragments.add(new ShareFragment());
        mFragments.add(new MineFragment());

        String[] tabTitles = {"首页", "账单", "卡·测评", "分享", "我的"};
        //******** 设置适配器、关联ViewPager ********
        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager(), mFragments, tabTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(5);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_bill:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_card_test:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_share:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.rb_mine:
                        mViewPager.setCurrentItem(4);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-mTime>1500){
            ToastUtils.show(this,"再按一次退出程序");
            mTime=System.currentTimeMillis();
        }else{
            finish();
        }

    }
}