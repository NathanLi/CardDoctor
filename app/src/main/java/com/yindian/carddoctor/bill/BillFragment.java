package com.yindian.carddoctor.bill;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yindian.carddoctor.R;
import com.yindian.carddoctor.bill.adapter.BillCardListAdapter;
import com.yindian.carddoctor.base.BaseFragment;
import com.yindian.carddoctor.common.bean.BillCardItem;
import com.yindian.carddoctor.common.view.SimpleToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class BillFragment extends BaseFragment {


    private RecyclerView mRecyclerView;

    public static Fragment getInstance(String data){
        BillFragment f = new BillFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data",data);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void initData() {
        List<BillCardItem> list = new ArrayList<>();
        list.add(new BillCardItem("交通银行", "1111", 100, 200, 1, "1-1"));
        list.add(new BillCardItem("中国银行", "2222", 200, 300, 2, "2-1"));
        list.add(new BillCardItem("工商银行", "3333", 300, 400, 3, "3-1"));
        list.add(new BillCardItem("招商银行", "4444", 400, 500, 4, "4-1"));
        list.add(new BillCardItem("广发银行", "5555", 500, 600, 5, "5-1"));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(new BillCardListAdapter(mActivity, list));
    }

    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.tab_item_bill));
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bill;
    }

}