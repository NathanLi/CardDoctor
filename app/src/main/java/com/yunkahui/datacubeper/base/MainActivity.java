package com.yunkahui.datacubeper.base;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
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
    private RadioGroup mRadioGroup;
    private long mTime;
    private int lastPosition;
    private RadioButton mRbHome;
    private RadioButton mRbBill;
    private RadioButton mRbCardTest;
    private RadioButton mRbShare;
    private RadioButton mRbMine;

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

    private Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    private void changeTabDrawable(int index, boolean isSelected) {
        Log.e(TAG, "changeTabDrawable: " + index);
        switch (index) {
            case 0:
                changeTabColor(mRbHome, getResources().getDrawable(R.mipmap.ic_home_selected), isSelected);
                break;
            case 1:
                changeTabColor(mRbBill, getResources().getDrawable(R.mipmap.ic_bill_normal), isSelected);
                break;
            case 2:
                changeTabColor(mRbCardTest, getResources().getDrawable(R.mipmap.ic_card_test_normal), isSelected);
                break;
            case 3:
                changeTabColor(mRbShare, getResources().getDrawable(R.mipmap.ic_share_normal), isSelected);
                break;
            case 4:
                changeTabColor(mRbMine, getResources().getDrawable(R.mipmap.ic_mine_normal), isSelected);
                break;
        }
    }

    private void changeTabColor(RadioButton rb, Drawable lastDrawable, boolean isSelected) {
        Log.e(TAG, "changeTabDrawable: " + isSelected);
        ColorStateList selectColor = ColorStateList.valueOf(getResources().getColor(R.color.tab_text_select_blue_0085ff));
        ColorStateList unSelectColor = ColorStateList.valueOf(getResources().getColor(R.color.bill_bind_card_explain_gray_88888888));
        if (isSelected) {
            rb.setCompoundDrawablesWithIntrinsicBounds(null, tintDrawable(lastDrawable, selectColor), null, null);
        } else {
            rb.setCompoundDrawablesWithIntrinsicBounds(null, tintDrawable(lastDrawable, unSelectColor), null, null);
        }
    }

    private String TAG = "MainActivity";
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
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        if (lastPosition != 0) {
                            changeTabDrawable(0, true);
                            changeTabDrawable(lastPosition, false);
                            lastPosition = 0;
                        }
                        break;
                    case R.id.rb_bill:
                        if (lastPosition != 1) {
                            changeTabDrawable(1, true);
                            changeTabDrawable(lastPosition, false);
                            lastPosition = 1;
                        }
                        break;
                    case R.id.rb_card_test:
                        if (lastPosition != 2) {
                            changeTabDrawable(2, true);
                            changeTabDrawable(lastPosition, false);
                            lastPosition = 2;
                        }
                        break;
                    case R.id.rb_share:
                        if (lastPosition != 3) {
                            changeTabDrawable(3, true);
                            changeTabDrawable(lastPosition, false);
                            lastPosition = 3;
                        }
                        break;
                    case R.id.rb_mine:
                        if (lastPosition != 4) {
                            changeTabDrawable(4, true);
                            changeTabDrawable(lastPosition, false);
                            lastPosition = 4;
                        }
                        break;
                }
                mViewPager.setCurrentItem(lastPosition);
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