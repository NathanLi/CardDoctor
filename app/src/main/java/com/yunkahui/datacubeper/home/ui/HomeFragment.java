package com.yunkahui.datacubeper.home.ui;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.view.DoubleBlockView;
import com.yunkahui.datacubeper.home.adapter.HomeItemAdapter;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.home.other.NotScrollGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    private RecyclerView mRecyclerView;

    @Override
    public void initData() {
        String[] titles = new String[] { "今日操作", "实名认证", "升级加盟", "申请POS",
                "个人征信", "借贷黑名单", "失信黑名单", "违章查询",
                "一键办卡", "贷款专区", "保险服务", "更多"};
        Integer[] imgs = { R.mipmap.ic_today_operation, R.mipmap.ic_name_verify,
                R.mipmap.ic_upgrade_and_join, R.mipmap.ic_apply_pos,
                R.mipmap.ic_personal_credit, R.mipmap.ic_blacklist_lending,
                R.mipmap.ic_discredit_carried_out, R.mipmap.ic_query_car_illegal,
                R.mipmap.ic_one_key_card, R.mipmap.ic_launcher,
                R.mipmap.ic_insurance_service, R.mipmap.ic_launcher, };
        HomeItem feature = null;
        List<HomeItem> featureList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            feature = new HomeItem(imgs[i], titles[i]);
            featureList.add(feature);
        }
        mRecyclerView.setLayoutManager(new NotScrollGridLayoutManager(getActivity(), 4));
        mRecyclerView.setAdapter(new HomeItemAdapter(getActivity(), featureList));
    }

    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.tab_item_home));
        mRecyclerView = view.findViewById(R.id.recycler_view);
        DoubleBlockView doubleBlockView = view.findViewById(R.id.double_block_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

}
