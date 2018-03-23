package com.yindian.carddoctor.home.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yindian.carddoctor.R;
import com.yindian.carddoctor.adapter.HomeFeatureAdapter;
import com.yindian.carddoctor.common.bean.HomeFeature;
import com.yindian.carddoctor.common.view.SimpleToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private String[] from = { "image", "title" };
    private int[] to = { R.id.image, R.id.title };

    public static Fragment getInstance(String data){
        HomeFragment f = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data",data);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.tab_item_home));

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
            Log.e("test1", "onCreateView: "+titles[i]);
            feature = new HomeFeature(imgs[i], titles[i]);
            featureList.add(feature);
        }
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.setAdapter(new HomeFeatureAdapter(getActivity(), featureList));
        return view;
    }

}
