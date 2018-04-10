package com.yunkahui.datacubeper.mine.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.MineItem;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.mine.adapter.MineItemAdapter;
import com.yunkahui.datacubeper.mine.logic.MineLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class MineFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private ImageView mIvIcon;
    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvRecommandCode;
    private TextView mTvReferee;

    private MineLogic mLogic;
    private List<MineItem> mMenuItemList;

    @Override
    public void initData() {
        mLogic=new MineLogic();
        mMenuItemList = new ArrayList<>();
        MineItemAdapter mineItemAdapter = new MineItemAdapter(R.layout.layout_list_item_mine, mMenuItemList);
        mineItemAdapter.bindToRecyclerView(mRecyclerView);
        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.layout_list_header_mine, null);
        mIvIcon = headerView.findViewById(R.id.iv_user_icon);
        mTvName = headerView.findViewById(R.id.tv_user_name);
        mTvPhone =headerView.findViewById(R.id.tv_user_name);
        mTvRecommandCode = headerView.findViewById(R.id.tv_recommand_code);
        mTvReferee = headerView.findViewById(R.id.tv_referee);
        // TODO: 2018/4/3 set user info
        mineItemAdapter.addHeaderView(headerView);
        mineItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                itemClick(position);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mineItemAdapter);

        mMenuItemList.addAll(mLogic.getMineItemList(getActivity()));
        mineItemAdapter.notifyDataSetChanged();

        mLogic.loadPersonalInformation(getActivity());

    }

    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.tab_item_me));
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    public void itemClick(int position){

        switch (mMenuItemList.get(position).getId()){
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                break;
            case 20:
                break;
            case 21:
                break;
            case 30:
                break;
            case 31:
                break;
            case 32:
                break;
            case 40:
                break;
            case 41:
                break;
            case 42:
                break;
        }

    }


}