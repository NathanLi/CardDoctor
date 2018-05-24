package com.yunkahui.datacubeper.home.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.view.PageRecyclerView.PageRecyclerView;
import com.yunkahui.datacubeper.home.adapter.HomeNewAdapter;
import com.yunkahui.datacubeper.home.adapter.HomeNewItemAdapter;
import com.yunkahui.datacubeper.home.logic.HomeLogic;

import java.util.ArrayList;
import java.util.List;


/**
 * 新版首页
 */
public class HomeNewFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private TextView mTextViewBalance;
    private TextView mTextViewFenRun;
    private RecyclerView mRecyclerViewMenu;

    private HomeNewAdapter mAdapter;
    private List<BillCreditCard.CreditCard> mCreditCardList;
    private List<HomeItem> mMenuHomeItems;
    private HomeLogic mLogic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_new, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mLogic = new HomeLogic();
        mCreditCardList = new ArrayList<>();
        mMenuHomeItems = new ArrayList<>();
        mAdapter = new HomeNewAdapter(R.layout.layout_list_item_bill_card, mCreditCardList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        initHeaderView();
        initMenuRecyclerView();
        return view;
    }

    //首页页面以recyclerView 的头部view形式加入
    private void initHeaderView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_list_header_view_home, null);
        mTextViewBalance = view.findViewById(R.id.text_view_balance);
        mTextViewFenRun = view.findViewById(R.id.text_view_FenRun);
        mRecyclerViewMenu = view.findViewById(R.id.recycler_view_menu);
        mAdapter.addHeaderView(view);
        mTextViewBalance.setText(Html.fromHtml(String.format(getResources().getString(R.string.account_balance), "-")));
        mTextViewFenRun.setText(Html.fromHtml(String.format(getResources().getString(R.string.add_up_FenRun), "-")));
    }

    //初始化滑动菜单
    private void initMenuRecyclerView() {
        mMenuHomeItems.addAll(mLogic.parsingJSONForHomeItem(getActivity()));
        HomeNewItemAdapter adapter=new HomeNewItemAdapter(R.layout.layout_list_item_home_menu,mMenuHomeItems);
        mRecyclerViewMenu.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL));
        mRecyclerViewMenu.setAdapter(adapter);
    }

}
