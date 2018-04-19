package com.yunkahui.datacubeper.share.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;

public class ShareProfitFragment extends BaseFragment {

    private RecyclerView mRecyclerView;

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.frament_share_profit;
    }
}
