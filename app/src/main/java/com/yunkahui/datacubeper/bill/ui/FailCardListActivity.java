package com.yunkahui.datacubeper.bill.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.adapter.FailCardListAdapter;
import com.yunkahui.datacubeper.bill.logic.FailCardListLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.FailBankCard;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import java.util.ArrayList;
import java.util.List;

//交易关闭卡片列表页面
public class FailCardListActivity extends AppCompatActivity implements IActivityStatusBar {

    private TextView mTextViewTips;
    private RecyclerView mRecyclerViewFailCard;

    private FailCardListLogic mLogic;
    private FailCardListAdapter mAdapter;
    private List<FailBankCard> mBankCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fail_card_list);
        super.onCreate(savedInstanceState);
        setTitle("交易关闭卡片");
    }

    @Override
    public void initData() {
        mLogic = new FailCardListLogic();
        mBankCardList = new ArrayList<>();
        int num = getIntent().getIntExtra("num", 0);
        mTextViewTips.setText(Html.fromHtml(String.format(getResources().getString(R.string.fail_card_list_tips), num)));

        mAdapter = new FailCardListAdapter(R.layout.layout_list_item_fail_card, mBankCardList);
        mRecyclerViewFailCard.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewFailCard.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                outRect.bottom = 10;
            }
        });
        mRecyclerViewFailCard.setAdapter(mAdapter);
        onItemChildClick();
        loadData();
    }

    @Override
    public void initView() {
        mTextViewTips = findViewById(R.id.text_view_tips);
        mRecyclerViewFailCard = findViewById(R.id.recycler_view);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }


    private void loadData() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.loadFailCardList(this, new SimpleCallBack<BaseBean<List<FailBankCard>>>() {
            @Override
            public void onSuccess(BaseBean<List<FailBankCard>> baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("失败卡片列表-->" + baseBean.toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mBankCardList.clear();
                    mBankCardList.addAll(baseBean.getRespData());
                    mAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }

    //根据ID查询卡片信息
    public void loadBankCardDataForId(final int id, final String cardName, final String cardNum) {
        LoadingViewDialog.getInstance().show(this);
        mLogic.loadBankCardDataForId(this, id, new SimpleCallBack<BaseBean<BillCreditCard>>() {
            @Override
            public void onSuccess(BaseBean<BillCreditCard> baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("卡片信息->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    BillCreditCard.CreditCard creditCard = baseBean.getRespData().getCardDetail().get(0);
                    startActivity(new Intent(FailCardListActivity.this, AutoPlanActivity.class)
                            .putExtra("time", creditCard.getRepayDayDate())
                            .putExtra("user_credit_card_id", id)
                            .putExtra("bank_card_num", cardNum)
                            .putExtra("bank_card_name", cardName));
                } else {
                    ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }

    //列表子控件点击事件
    public void onItemChildClick() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.text_view_right_desc:
                        break;
                    case R.id.button_submit_1:
                        break;
                    case R.id.button_submit_2:
                        loadBankCardDataForId(mBankCardList.get(position).getBankcard_id(), mBankCardList.get(position).getBankcard_num(), mBankCardList.get(position).getBankcard_name());
                        break;
                }
            }
        });
    }

}
