package com.yindian.carddoctor.home.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yindian.carddoctor.R;
import com.yindian.carddoctor.home.adapter.HomeFeatureAdapter;
import com.yindian.carddoctor.base.BaseFragment;
import com.yindian.carddoctor.common.bean.HomeFeature;
import com.yindian.carddoctor.common.view.SimpleToolbar;
import com.yindian.carddoctor.home.other.NotScrollGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    private RecyclerView mRecyclerView;

    public static Fragment getInstance(String data){
        HomeFragment f = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data",data);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void initData() {
        String[] titles = new String[] { "今日操作", "实名认证", "升级加盟", "申请POS",
                "个人征信", "借贷黑名单", "失信黑名单", "违章查询",
                "一键办卡", "贷款专区", "保险服务", "更多"};
        Integer[] imgs = { R.drawable.ic_today_operation, R.drawable.ic_name_authentication,
                R.drawable.ic_upgrade_and_join, R.drawable.ic_apply_pos,
                R.drawable.ic_personal_credit, R.drawable.ic_blacklist_lending,
                R.drawable.ic_discredit_carried_out, R.drawable.ic_query_car_illegal,
                R.drawable.ic_one_key_card, R.drawable.ic_launcher,
                R.drawable.ic_insurance_service, R.drawable.ic_launcher, };
        HomeFeature feature = null;
        List<HomeFeature> featureList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            feature = new HomeFeature(imgs[i], titles[i]);
            featureList.add(feature);
        }
        mRecyclerView.setLayoutManager(new NotScrollGridLayoutManager(getActivity(), 4));
        mRecyclerView.setAdapter(new HomeFeatureAdapter(getActivity(), featureList));
    }

    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.tab_item_home));
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

}
