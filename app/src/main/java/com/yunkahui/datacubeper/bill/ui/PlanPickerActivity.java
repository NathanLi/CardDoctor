package com.yunkahui.datacubeper.bill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.adapter.SelectPlanAdapter;
import com.yunkahui.datacubeper.common.bean.HomeItem;

import java.util.ArrayList;
import java.util.List;

public class PlanPickerActivity extends AppCompatActivity implements IActivityStatusBar {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_plan);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void initData() {
        List<HomeItem> list = new ArrayList<>();
        list.add(new HomeItem(R.mipmap.ic_plan_pos, "POS规划（手动刷卡）"));
        list.add(new HomeItem(R.mipmap.ic_plan_auto, "自动规划（自动刷卡）"));
        SelectPlanAdapter adapter = new SelectPlanAdapter(R.layout.layout_list_item_select_plan, list);
        adapter.bindToRecyclerView(mRecyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Class clazz = null;
                if (position == 0) {
                    clazz = PosPlanActivity.class;
                } else if (position == 1) {
                    clazz = AutoPlanActivity.class;
                }
                startActivity(new Intent(PlanPickerActivity.this, clazz)
                        .putExtra("time", getIntent().getStringExtra("time"))
                        .putExtra("user_credit_card_id", getIntent().getIntExtra("user_credit_card_id", 0))
                        .putExtra("bank_card_name", getIntent().getStringExtra("bank_card_name"))
                        .putExtra("bank_card_num", getIntent().getStringExtra("bank_card_num")));
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
    }
}
