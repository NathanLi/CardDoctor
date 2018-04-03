package com.yunkahui.datacubeper.mine.ui;

import android.app.Activity;
import android.media.Image;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.MineItem;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.mine.adapter.MineItemAdapter;

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

    @Override
    public void initData() {
        List<MineItem> list = new ArrayList<>();
        MineItemAdapter mineItemAdapter = new MineItemAdapter(R.layout.layout_list_item_mine, list);
        mineItemAdapter.bindToRecyclerView(mRecyclerView);
        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.layout_mine_info, null);
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
                // TODO: 2018/4/3 set next page
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mineItemAdapter);
        Integer[][] imgs = {{R.mipmap.ic_upgrade_join, R.mipmap.ic_verify_name, R.mipmap.ic_bind_phone, R.mipmap.ic_personal_data, R.mipmap.ic_change_psd},
                {R.mipmap.ic_conn_service, R.mipmap.ic_feedback},
                {R.mipmap.ic_my_card, R.mipmap.ic_my_alipay,R.mipmap.ic_pos_manager},
                {R.mipmap.ic_about_us, R.mipmap.ic_version, R.mipmap.ic_safe_exit}};
        String[][] titles = {
                {"升级加盟", "实名认证", "绑定手机", "个人资料", "修改密码"},
                {"联系客服", "意见反馈"},
                {"我的储蓄卡", "我的支付宝", "POS管理"},
                {"关于我们", "版本号", "安全退出"}
        };
        for (int i = 0; i < titles.length; i++) {
            for (int j = 0; j < titles[i].length; j++) {
                list.add(new MineItem(imgs[i][j], titles[i][j], j == 0));
            }
        }
        mineItemAdapter.notifyDataSetChanged();
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


}