package com.yunkahui.datacubeper.base;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.adapter.MainTabAdapter;
import com.yunkahui.datacubeper.common.utils.TintUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.home.ui.HomeFragment;
import com.yunkahui.datacubeper.bill.ui.BillFragment;
import com.yunkahui.datacubeper.test.ui.CardTestFragment;
import com.yunkahui.datacubeper.share.ui.ShareFragment;
import com.yunkahui.datacubeper.mine.ui.MineFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页框架
 */
public class MainActivity extends AppCompatActivity implements IActivityStatusBar {

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton mRbHome;
    private RadioButton mRbBill;
    private RadioButton mRbCardTest;
    private RadioButton mRbShare;
    private RadioButton mRbMine;

    private ColorStateList mSelectColor;
    private ColorStateList mUnSelectedColor;
    private int lastPosition;
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
        mRadioGroup = findViewById(R.id.radio_group);
        mRbHome = findViewById(R.id.rb_home);
        mRbBill = findViewById(R.id.rb_bill);
        mRbCardTest = findViewById(R.id.rb_card_test);
        mRbShare = findViewById(R.id.rb_share);
        mRbMine = findViewById(R.id.rb_mine);
    }

    private void changeTabColor(int index, boolean isSelected) {
        RadioButton radioButton = mRbHome;
        int res = R.mipmap.ic_home_selected;
        switch (index) {
            case 0:
                radioButton = mRbHome;
                res = R.mipmap.ic_home_selected;
                break;
            case 1:
                radioButton = mRbBill;
                res = R.mipmap.ic_bill_normal;
                break;
            case 2:
                radioButton = mRbCardTest;
                res = R.mipmap.ic_card_test_normal;
                break;
            case 3:
                radioButton = mRbShare;
                res = R.mipmap.ic_share_normal;
                break;
            case 4:
                radioButton = mRbMine;
                res = R.mipmap.ic_mine_normal;
                break;
        }
        radioButton.setCompoundDrawablesWithIntrinsicBounds(null, TintUtils.tintDrawable(getResources().getDrawable(res),
                isSelected ? mSelectColor : mUnSelectedColor), null, null);
    }

    @Override
    public void initData() {
        mSelectColor = ColorStateList.valueOf(getResources().getColor(R.color.text_color_blue_0085ff));
        mUnSelectedColor = ColorStateList.valueOf(getResources().getColor(R.color.bg_color_gray_88888888));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new BillFragment());
        fragments.add(new CardTestFragment());
        fragments.add(new ShareFragment());
        fragments.add(new MineFragment());
        String[] tabTitles = {"首页", "账单", "卡·测评", "分享", "我的"};
        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager(), fragments, tabTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(5);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int index = 0;
                switch (checkedId) {
                    case R.id.rb_home:
                        index = 0;
                        break;
                    case R.id.rb_bill:
                        index = 1;
                        break;
                    case R.id.rb_card_test:
                        index = 2;
                        break;
                    case R.id.rb_share:
                        index = 3;
                        break;
                    case R.id.rb_mine:
                        index = 4;
                        break;
                }
                changeTabColor(index, true);
                changeTabColor(lastPosition, false);
                mViewPager.setCurrentItem(index);
                lastPosition = index;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mTime > 1500) {
            ToastUtils.show(this, "再按一次退出程序");
            mTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}