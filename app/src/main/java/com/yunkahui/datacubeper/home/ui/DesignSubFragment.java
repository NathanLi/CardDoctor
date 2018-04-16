package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.HomeDesignSub;
import com.yunkahui.datacubeper.home.adapter.DesignSubAdapter;
import com.yunkahui.datacubeper.home.logic.DesignSubLogic;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/11
 */
public class DesignSubFragment extends BaseFragment {

    private static final String TAG = "DesignSubFragment";
    private List<HomeDesignSub.DesignSub> list = new ArrayList<>();
    private ImageView mIvNoData;
    private RecyclerView mRecyclerView;
    private DesignSubLogic mLogic;
    private DesignSubAdapter mDesignSubAdapter;

    public static BaseFragment newInstance(int kind) {
        DesignSubFragment fragment = new DesignSubFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("kind", kind);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        mLogic = new DesignSubLogic();
        initRecyclerView();
        int kind = getArguments().getInt("kind");
        switch (kind) {
            case 0:
                getTodayOperation("10", "10", "1");
                break;
            case 1:
                getTodayOperation("11", "10", "1");
                break;
            case 2:
                getTodayOperation("other", "10", "1");
                break;
            case 3:
                getSmartPlan("10", "10", "1");
                break;
            case 4:
                getSmartPlan("11", "10", "1");
                break;
            case 5:
                //getSmartPlan("10", "10", "1");
                break;
        }
    }

    private void initRecyclerView() {
        mDesignSubAdapter = new DesignSubAdapter(R.layout.layout_list_item_design_sub, list);
        mDesignSubAdapter.bindToRecyclerView(mRecyclerView);
        mDesignSubAdapter.setEmptyView(View.inflate(mActivity, R.layout.layout_no_data, null));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mDesignSubAdapter);
    }

    /*//******** 查询智能规划数据 ********
    private void getSmartPlan(final String isPos, String num, String page) {
        mLogic.requestSP(mActivity, isPos, num, page, new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject baseBean) {
                Log.e(TAG, "requestSP: "+isPos+", "+baseBean.toString());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "getSmartPlan onFailure: " + throwable.toString());
            }
        });
    }

    //******** 查询今日操作数据 ********
    private void getTodayOperation(final String isPos, String num, String page) {
        mLogic.requestTO(mActivity, isPos, num, page, new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject baseBean) {
                Log.e(TAG, "requestTO: "+isPos+", "+baseBean.toString());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "getTodayOperation onFailure: " + throwable.toString());
            }
        });
    }*/

    //******** 查询智能规划数据 ********
    private void getSmartPlan(final String isPos, String num, String page) {
        mLogic.requestSmartPlan(mActivity, isPos, num, page, new SimpleCallBack<BaseBean<HomeDesignSub>>() {
            @Override
            public void onSuccess(BaseBean<HomeDesignSub> baseBean) {
                Log.e(TAG, "requestSmartPlan onSuccess: "+isPos+", "+baseBean.getRespData().getList().toString());
                list.clear();
                list.addAll(baseBean.getRespData().getList());
                mDesignSubAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "getSmartPlan onFailure: "+isPos+", "  + throwable.toString());
            }
        });
    }

    //******** 查询今日操作数据 ********
    private void getTodayOperation(final String isPos, String num, String page) {
        mLogic.requestTodayOperation(mActivity, isPos, num, page, new SimpleCallBack<BaseBean<HomeDesignSub>>() {
            @Override
            public void onSuccess(BaseBean<HomeDesignSub> baseBean) {
                Log.e(TAG, "requestTodayOperation onSuccess: "+isPos+", "+baseBean.getRespData().getList().toString());
                list.clear();
                list.addAll(baseBean.getRespData().getList());
                mDesignSubAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "getTodayOperation onFailure: "+isPos+", " + throwable.toString());
            }
        });
    }

    @Override
    public void initView(View view) {
        mIvNoData = view.findViewById(R.id.iv_no_data);
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_auto_plan;
    }
}
