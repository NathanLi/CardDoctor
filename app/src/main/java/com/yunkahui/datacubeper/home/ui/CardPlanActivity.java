package com.yunkahui.datacubeper.home.ui;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.view.PlanSpinner;

/**
 * 卡片规划（新）
 */
public class CardPlanActivity extends AppCompatActivity implements IActivityStatusBar, PlanSpinner.OnSpinnerClickListener {

    private RecyclerView mRecyclerView;
    private PlanSpinner mPlanSpinnerDataType;
    private PlanSpinner mPlanSpinnerListType;
    private PlanSpinner mPlanSpinnerCardType;

    private AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_card_plan);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mPlanSpinnerDataType.updatePopupWindow();
                mPlanSpinnerListType.updatePopupWindow();
                mPlanSpinnerCardType.updatePopupWindow();
            }
        });
    }

    @Override
    public void initView() {
        Toolbar toolbar = findViewById(R.id.card_doctor_tool_bar);
        toolbar.setBackgroundResource(R.drawable.bg_blue_gradient);
        mRecyclerView = findViewById(R.id.recycler_view);
        mPlanSpinnerDataType = findViewById(R.id.plan_spinner_data_type);
        mPlanSpinnerListType = findViewById(R.id.plan_spinner_list_type);
        mPlanSpinnerCardType = findViewById(R.id.plan_spinner_card_type);
        mAppBarLayout = findViewById(R.id.app_bar_layout);

        mPlanSpinnerDataType.setOnSpinnerClickListener(this);
        mPlanSpinnerListType.setOnSpinnerClickListener(this);
        mPlanSpinnerCardType.setOnSpinnerClickListener(this);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void onSpinnerClick() {
        mAppBarLayout.setExpanded(false);
    }
}
