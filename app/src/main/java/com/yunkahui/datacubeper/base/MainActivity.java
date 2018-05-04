package com.yunkahui.datacubeper.base;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.adapter.MainTabAdapter;
import com.yunkahui.datacubeper.common.utils.LogUtils;
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

    private int[] icons = {R.mipmap.ic_home_selected, R.mipmap.ic_bill_normal, R.mipmap.ic_card_test_normal,
            R.mipmap.ic_share_normal, R.mipmap.ic_mine_normal};
    private SparseArray<RadioButton> mRadioButtons;
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
    public void initView() {
        mViewPager = findViewById(R.id.view_pager);
    }

    private void changeTabColor(int index, boolean isSelected) {
        Drawable tint = TintUtils.tintDrawable(getResources().getDrawable(icons[index]), isSelected ? mSelectColor : mUnSelectedColor);
        mRadioButtons.get(index).setCompoundDrawablesWithIntrinsicBounds(null, tint, null, null);
    }

    @Override
    public void initData() {
        mRadioButtons = new SparseArray<>();
        mRadioButtons.put(0, (RadioButton) findViewById(R.id.rb_home));
        mRadioButtons.put(1, (RadioButton) findViewById(R.id.rb_bill));
        mRadioButtons.put(2, (RadioButton) findViewById(R.id.rb_card_test));
        mRadioButtons.put(3, (RadioButton) findViewById(R.id.rb_share));
        mRadioButtons.put(4, (RadioButton) findViewById(R.id.rb_mine));

        ((RadioGroup) findViewById(R.id.radio_group)).setOnCheckedChangeListener(new InnerCheckChangeListener());
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
        mViewPager.setOffscreenPageLimit(5);

        for (int i = 0; i < tabTitles.length; i++) {
            if (i == 0) {
                changeTabColor(i, true);
            } else {
                changeTabColor(i, false);
            }
        }
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

    private class InnerCheckChangeListener implements RadioGroup.OnCheckedChangeListener {

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
            changeTabColor(lastPosition, false);
            changeTabColor(index, true);
            mViewPager.setCurrentItem(index);
            lastPosition = index;
        }
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}