package com.yunkahui.datacubeper.test.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.CardTestItem;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.CardTestView;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.test.adapter.CardTestAdapter;
import com.yunkahui.datacubeper.test.logic.CardTestLogic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class CardTestFragment extends BaseFragment implements View.OnClickListener {

    private final int RESULT_CODE_UPDATE=1001;

    private RecyclerView mRecyclerView;

    private CardTestLogic mLogic;

    @Override
    public void initData() {
        mLogic=new CardTestLogic();
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
        View header = LayoutInflater.from(mActivity).inflate(R.layout.layout_list_header_card_test, null);
        final CardTestView cardTestView = header.findViewById(R.id.card_test_view);

        header.findViewById(R.id.btn_run_test).setOnClickListener(this);
        header.findViewById(R.id.text_view_example).setOnClickListener(this);
        header.findViewById(R.id.text_view_history_test).setOnClickListener(this);

        cardTestAdapter.addHeaderView(header);
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


    public void loadTestMoney(){
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.loadTestMoney(getActivity(), new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                LoadingViewDialog.getInstance().dismiss();
                try {
                    LogUtils.e("卡测评->"+jsonObject.toString());
                    JSONObject object=new JSONObject(jsonObject.toString());
                    if(RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                        Intent intent=new Intent(getActivity(),CardTestActivity.class);
                        intent.putExtra("money",object.getDouble("respData"));
                        startActivityForResult(intent,RESULT_CODE_UPDATE);
                    }else{
                        ToastUtils.show(getActivity(),object.optString("respDesc"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity(),"请求失败 "+throwable.toString());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_run_test:
                loadTestMoney();
                break;
            case R.id.text_view_example:
                TestResultActivity.actionStart(getActivity(), null, System.currentTimeMillis());
                break;
            case R.id.text_view_history_test:
                break;
        }
    }
}