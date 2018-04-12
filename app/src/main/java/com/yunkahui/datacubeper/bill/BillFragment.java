package com.yunkahui.datacubeper.bill;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.bill.adapter.BillCardListAdapter;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.BillCardItem;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;

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
        list.add(new BillCardItem("交通银行", "1111", "100", "200", "1", "1-1"));
        list.add(new BillCardItem("中国银行", "2222", "200", "300", "2", "2-1"));
        list.add(new BillCardItem("工商银行", "3333", "300", "400", "3", "3-1"));
        list.add(new BillCardItem("招商银行", "4444", "400", "500", "4", "4-1"));
        list.add(new BillCardItem("广发银行", "5555", "500", "600", "5", "5-1"));
        BillCardListAdapter adapter = new BillCardListAdapter(R.layout.layout_list_item_bill_card, list);
        adapter.bindToRecyclerView(mRecyclerView);
        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.layout_list_item_bill_head, null);
        adapter.addHeaderView(headerView);
        View footerView = LayoutInflater.from(mActivity).inflate(R.layout.layout_list_item_bill_footer, null);
        adapter.addFooterView(footerView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_bill_sync:
                        Toast.makeText(mActivity, "btn_bill_sync", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_smart_plan:
                        Toast.makeText(mActivity, "tv_smart_plan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(adapter);
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