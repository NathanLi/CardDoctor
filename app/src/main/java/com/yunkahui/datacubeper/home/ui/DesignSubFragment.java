package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.home.logic.DesignSubLogic;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YD1 on 2018/4/11
 */
public class DesignSubFragment extends BaseFragment {

    private ImageView mIvNoData;
    private RecyclerView mRecyclerView;
    private DesignSubLogic mLogic;

    public static BaseFragment newInstance(int kind) {
        DesignSubFragment fragment = new DesignSubFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("kind", kind);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String TAG = "designSubFragment";

    @Override
    public void initData() {
        mLogic = new DesignSubLogic();
        int kind = getArguments().getInt("kind");
        switch (kind) {
            case 0:
                getTodayOperation("10", "10", "1");
                break;
            case 1:
                getTodayOperation("11", "10", "1");
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    private void getTodayOperation(final String isPos, String num, String page) {
        mLogic.requestTodayOperation(mActivity, isPos, num, page, new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                Log.e(TAG, "onSuccess: "+isPos+", "+jsonObject.toString());
                try {
                    JSONObject object = new JSONObject(jsonObject.toString());
                    String respCode = object.getString("respCode");
                    if ("0024".equals(respCode)) {
                        mIvNoData.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.toString());
                Toast.makeText(mActivity, "刷新失败", Toast.LENGTH_SHORT).show();
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
