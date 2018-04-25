package com.yunkahui.datacubeper.test.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.GsonBuilder;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.CardTestItem;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.test.adapter.TestHistoryListAdapter;
import com.yunkahui.datacubeper.test.logic.TestHistoryLogic;

import java.util.ArrayList;
import java.util.List;

public class TestHistoryActivity extends AppCompatActivity implements IActivityStatusBar {

    private RecyclerView mRecyclerView;
    private ImageView mImageViewNoData;

    private TestHistoryListAdapter mListAdapter;
    private TestHistoryLogic mLogic;
    private String mBankCardNumber;
    private List<CardTestItem> mCardTestItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test_history);
        super.onCreate(savedInstanceState);
        this.setTitle("历史测评");
    }

    @Override
    public void initData() {
        mBankCardNumber = getIntent().getStringExtra("bankcard");
        mLogic = new TestHistoryLogic();
        mCardTestItems = new ArrayList<>();
        mListAdapter = new TestHistoryListAdapter(R.layout.layout_lsit_item_test_history, mCardTestItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                outRect.bottom = 3;
            }
        });
        mRecyclerView.setAdapter(mListAdapter);

        mListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                loadTestRecordDetail(mCardTestItems.get(position).getApr_id());
            }
        });
        loadData();
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mImageViewNoData = findViewById(R.id.iv_no_data);
    }

    //查询列表数据
    private void loadData() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.loadTestHistoryRecord(this, mBankCardNumber, new SimpleCallBack<BaseBean<List<CardTestItem>>>() {
            @Override
            public void onSuccess(BaseBean<List<CardTestItem>> baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("历史测评->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    List<CardTestItem> items = baseBean.getRespData();
                    for (int i = 0; i < items.size(); i++) {
                        String cs = items.get(i).getApr_send_datas().replace("\\", "");
                        CardTestItem.Card card = new GsonBuilder().create().fromJson(cs, CardTestItem.Card.class);
                        items.get(i).setCard(card);
                    }
                    mCardTestItems.clear();
                    mCardTestItems.addAll(items);
                    mListAdapter.notifyDataSetChanged();

                    if (mCardTestItems.size() > 0) {
                        mImageViewNoData.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    //查看测评记录详情数据
    private void loadTestRecordDetail(int id) {
        LoadingViewDialog.getInstance().show(this);
        mLogic.loadTestRecordDetail(this, id, new SimpleCallBack<BaseBean<CardTestItem>>() {
            @Override
            public void onSuccess(BaseBean<CardTestItem> baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("卡测评详情->" + baseBean.getJsonObject().toString());
                if(RequestUtils.SUCCESS.equals(baseBean.getRespCode())){
                    TestResultActivity.actionStart(TestHistoryActivity.this,baseBean.getRespData().getApr_return_datas(),System.currentTimeMillis());
                }else{
                    ToastUtils.show(getApplicationContext(),baseBean.getRespDesc());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });

    }


}
