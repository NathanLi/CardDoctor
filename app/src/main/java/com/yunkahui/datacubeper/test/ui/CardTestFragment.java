package com.yunkahui.datacubeper.test.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.GsonBuilder;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.wang.avi.AVLoadingIndicatorView;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.CardTestItem;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.CardTestView;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.test.adapter.CardTestAdapter;
import com.yunkahui.datacubeper.test.logic.CardTestLogic;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class CardTestFragment extends BaseFragment implements View.OnClickListener {

    private final int RESULT_CODE_UPDATE = 1001;

    private AVLoadingIndicatorView mLoadingIndicatorView;
    private RecyclerView mRecyclerView;

    private CardTestLogic mLogic;
    private List<CardTestItem> mCardTestItems;
    private CardTestAdapter mCardTestAdapter;

    @Override
    public void initData() {
        mLogic = new CardTestLogic();
        mCardTestItems = new ArrayList<>();
        mCardTestAdapter = new CardTestAdapter(R.layout.layout_list_item_card_test, mCardTestItems);
        mCardTestAdapter.bindToRecyclerView(mRecyclerView);
        View header = LayoutInflater.from(mActivity).inflate(R.layout.layout_list_header_card_test, null);
        final CardTestView cardTestView = header.findViewById(R.id.card_test_view);

        header.findViewById(R.id.btn_run_test).setOnClickListener(this);
        header.findViewById(R.id.text_view_example).setOnClickListener(this);
        header.findViewById(R.id.text_view_history_test).setOnClickListener(this);

        mCardTestAdapter.addHeaderView(header);
        mCardTestAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mCardTestAdapter);

        mCardTestAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_run_test:
                        loadTestMoney(mCardTestItems.get(position).getCard());
                        break;
                    case R.id.text_view_report:
                        Intent intent = new Intent(getActivity(), TestHistoryActivity.class);
                        intent.putExtra("bankcard", mCardTestItems.get(position).getCard().getBankcard_num());
                        intent.putExtra("card", mCardTestItems.get(position).getCard());
                        startActivity(intent);
                        break;
                }
            }
        });
        loadData();
    }

    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.card_test));
        mLoadingIndicatorView = view.findViewById(R.id.av_loading_view);
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_card_test;
    }


    //查询用户测评过的卡片列表
    public void loadData() {
        mLoadingIndicatorView.setVisibility(View.VISIBLE);
        mLogic.loadTestCardList(getActivity(), new SimpleCallBack<BaseBean<List<CardTestItem>>>() {
            @Override
            public void onSuccess(BaseBean<List<CardTestItem>> baseBean) {
                mLoadingIndicatorView.setVisibility(View.GONE);
                LogUtils.e("获取测评卡片列表->"+baseBean.getJsonObject().toString());
                if(RequestUtils.SUCCESS.equals(baseBean.getRespCode())){
                    List<CardTestItem> items=baseBean.getRespData();
                    for (int i=0;i<items.size();i++){
                        String cs=items.get(i).getApr_send_datas().replace("\\","");
                        CardTestItem.Card card=new GsonBuilder().create().fromJson(cs, CardTestItem.Card.class);
                        items.get(i).setCard(card);
                    }
                    mCardTestItems.clear();
                    mCardTestItems.addAll(items);
                    mCardTestAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                mLoadingIndicatorView.setVisibility(View.GONE);
                LogUtils.e("获取测评卡片列表失败 " + throwable.toString());
            }
        });
    }

    //卡评测-获取评测价格
    public void loadTestMoney(final CardTestItem.Card card) {
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.loadTestMoney(getActivity(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                try {
                    LogUtils.e("卡测评->" + baseBean.getJsonObject().toString());
                    JSONObject object = baseBean.getJsonObject();
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        Intent intent = new Intent(getActivity(), CardTestActivity.class);
                        intent.putExtra("money", object.getDouble("respData"));
                        if (card != null) {
                            intent.putExtra("card", card);
                        }
                        startActivityForResult(intent, RESULT_CODE_UPDATE);
                    } else {
                        ToastUtils.show(getActivity(), object.optString("respDesc"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity(), "请求失败 " + throwable.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_run_test:
                loadTestMoney(null);
                break;
            case R.id.text_view_example:
                TestResultActivity.actionStart(getActivity(), null, System.currentTimeMillis());
                break;
            case R.id.text_view_history_test:
                startActivity(new Intent(getActivity(), TestHistoryActivity.class));
                break;
        }
    }
}