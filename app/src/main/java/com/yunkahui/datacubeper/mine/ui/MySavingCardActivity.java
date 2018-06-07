package com.yunkahui.datacubeper.mine.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.MyCardBean;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.mine.adapter.MyCardAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的储蓄卡（新）
 */

public class MySavingCardActivity extends AppCompatActivity implements IActivityStatusBar {

    private RecyclerView mRecyclerView;
    private List<MyCardBean> mList;
    private SimpleToolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_saving);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        mToolbar.setLeftIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setRightIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mList = new ArrayList<>();
        mList.add(new MyCardBean(R.mipmap.bank_other, "中行", "7913", "张三", "5月20日", "5月21日"));
        mList.add(new MyCardBean(R.mipmap.bank_other, "央行", "7913", "张四", "5月21日", "5月22日"));
        mList.add(new MyCardBean(R.mipmap.bank_other, "国行", "7913", "张五", "5月23日", "5月24日"));
        MyCardAdapter adapter = new MyCardAdapter(R.layout.layout_list_item_my_card, mList);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        adapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.tool_bar);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
