package com.yunkahui.datacubeper.test;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.CardTestItem;
import com.yunkahui.datacubeper.common.view.CardTestView;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.test.adapter.CardTestAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class CardTestFragment extends BaseFragment {

    private RecyclerView mRecyclerView;

    @Override
    public void initData() {
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        Log.e("test", "initData: "+screenHeigh+", "+screenWidth);
        List<CardTestItem> cardTestItems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CardTestItem cardTestItem = new CardTestItem();
            cardTestItem.setIcon(R.mipmap.ic_launcher);
            cardTestItem.setUserName("dian" + i);
            cardTestItem.setBankName("中国" + i + "银行");
            cardTestItem.setBankId("" + i + i + i + i + i + i);
            cardTestItem.setNickName("vsa");
            cardTestItem.setCardType("借记卡");
            cardTestItems.add(cardTestItem);
        }
        CardTestAdapter cardTestAdapter = new CardTestAdapter(R.layout.layout_list_item_card_test, cardTestItems);
        cardTestAdapter.bindToRecyclerView(mRecyclerView);
        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.layout_list_header_card_test, null);
        cardTestAdapter.addHeaderView(headerView);
        cardTestAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(cardTestAdapter);
    }

    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.tab_item_card_test));
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_card_test;
    }

}