package com.yunkahui.datacubeper.home.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.view.DoubleBlockView;
import com.yunkahui.datacubeper.home.adapter.HomeItemAdapter;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.home.other.NotScrollGridLayoutManager;
import com.yunkahui.datacubeper.share.ui.ProfitActivity;
import com.yunkahui.datacubeper.share.ui.WalletActivity;
import com.yunkahui.datacubeper.share.ui.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private DoubleBlockView mDoubleBlockView;

    @Override
    public void initData() {
        initListener();
        String[] titles = new String[] { "今日操作", "实名认证", "升级加盟", "申请POS",
                "个人征信", "借贷黑名单", "失信黑名单", "违章查询",
                "一键办卡", "贷款专区", "保险服务", "更多"};
        Integer[] imgs = { R.mipmap.ic_today_operation, R.mipmap.ic_name_verify,
                R.mipmap.ic_upgrade_and_join, R.mipmap.ic_apply_pos,
                R.mipmap.ic_personal_credit, R.mipmap.ic_blacklist_lending,
                R.mipmap.ic_discredit_carried_out, R.mipmap.ic_query_car_illegal,
                R.mipmap.ic_one_key_card, R.mipmap.ic_launcher,
                R.mipmap.ic_insurance_service, R.mipmap.ic_launcher, };
        List<HomeItem> homeItems = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            HomeItem item = new HomeItem(imgs[i], titles[i]);
            homeItems.add(item);
        }
        HomeItemAdapter homeItemAdapter = new HomeItemAdapter(R.layout.layout_list_item_home, homeItems);
        homeItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0) {
                    startActivity(new Intent(mActivity, TodayOperationActivity.class));
                } else if (position == 4) {
                    startActivity(new Intent(mActivity, WebViewActivity.class).putExtra("url", "https://ipcrs.pbccrc.org.cn/"));
                } else if (position == 5) {
                    startActivity(new Intent(mActivity, WebViewActivity.class).putExtra("url", "http://www.025hmd.com"));
                } else if (position == 6) {
                    startActivity(new Intent(mActivity, WebViewActivity.class).putExtra("url", "http://shixin.court.gov.cn/"));
                } else if (position == 7) {
                    startActivity(new Intent(mActivity, WebViewActivity.class).putExtra("url", "http://m.46644.com/illegal/?tpltype=weixin"));
                } else if (position == 8) {
                    startActivity(new Intent(mActivity, WebViewActivity.class).putExtra("url", "http://kadai.yunkahui.cn/touch/index.php?uid=64"));
                } else if (position == 9) {
                    startActivity(new Intent(mActivity, WebViewActivity.class).putExtra("url", "http://kadai.yunkahui.cn/touch/index.php?p=products_list&lanmu=18&from=timeline&isappinstalled=0"));
                } else if (position == 10) {
                    startActivity(new Intent(mActivity, WebViewActivity.class).putExtra("url", "http://www.epicc.com.cn/wap/views/proposal/giveactivity/JBD_S/?productcode=JBD_S&plantype=B?cmpid=2017pcluodiye"));
                }
            }
        });
        homeItemAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new NotScrollGridLayoutManager(getActivity(), 4));
        mRecyclerView.setAdapter(homeItemAdapter);
    }

    private void initListener() {
        mDoubleBlockView.setOnLeftBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity, WalletActivity.class)
                .putExtra("from", "home"));
            }
        }).setOnRightBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity, ProfitActivity.class));
            }
        });
    }

    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.tab_item_home));
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mDoubleBlockView = view.findViewById(R.id.double_block_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

}
