package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

/**
 * 调整规划
 */
public class AdjustPlanActivity extends AppCompatActivity implements IActivityStatusBar {

    private String mAmount;
    private String mId;
    private String mBusinessType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_adjust);
        super.onCreate(savedInstanceState);
        setTitle("调整规划");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void initData() {
        String type = getIntent().getStringExtra("type");
        mAmount = getIntent().getStringExtra("amount");
        mId = getIntent().getStringExtra("id");
        mBusinessType = getIntent().getStringExtra("business_type");
        Fragment fragment = null;
        if ("还款".equals(type)) {
            fragment = new RepayAdjustFragment();
        } else if ("消费".equals(type)) {
//            fragment = new ExpenseAdjustFragment();
        }
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        if(fragment!=null){
            transaction.replace(R.id.frame_layout,fragment).commitNowAllowingStateLoss();
        }
    }

    public String getId() {
        return mId;
    }

    public String getAmount() {
        return mAmount;
    }

    public String getBusinessType() {
        return mBusinessType;
    }

    @Override
    public void initView() {
    }
}
