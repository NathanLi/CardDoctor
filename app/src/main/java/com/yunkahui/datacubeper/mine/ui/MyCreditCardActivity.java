package com.yunkahui.datacubeper.mine.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.MyCardBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.mine.adapter.MyCardAdapter;
import com.yunkahui.datacubeper.mine.logic.MyCreditCardLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的信用卡（新）
 */

public class MyCreditCardActivity extends AppCompatActivity implements IActivityStatusBar {

    private RecyclerView mRecyclerView;
    private List<BillCreditCard.CreditCard> mList;
    private SimpleToolbar mToolbar;
    private MyCreditCardLogic mLogic;
    private MyCardAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_credit);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        mLogic = new MyCreditCardLogic();

        mToolbar.setLeftIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setRightIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mList = new ArrayList<>();
        mAdapter = new MyCardAdapter(R.layout.layout_list_item_my_card, mList);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.tv_unbind:
                        showDeleteDialog(mList.get(position).getUserCreditCardId());
                        break;
                }
            }
        });
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setEmptyView(R.layout.layout_no_data);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.tool_bar);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    //获取信用卡列表
    public void loadData(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.queryCreditCardList(this, new SimpleCallBack<BaseBean<BillCreditCard>>() {
            @Override
            public void onSuccess(BaseBean<BillCreditCard> billCreditCardBaseBean) {
                LoadingViewDialog.getInstance().dismiss();
                List<BillCreditCard.CreditCard> details = billCreditCardBaseBean.getRespData().getCardDetail();
                mList.clear();
                mList.addAll(details);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
            }
        });
    }

    //提示是否删除信用卡弹窗
    private void showDeleteDialog(final int cardId) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("确定删除改卡片?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCreditCard(cardId);
                    }
                })
                .setNeutralButton("取消", null)
                .create();
        dialog.show();
    }

    //删除信用卡
    private void deleteCreditCard(int cardId) {
        LoadingViewDialog.getInstance().show(this);
        mLogic.deleteCreditCard(this, cardId, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("删除信用卡->" + baseBean.getJsonObject().toString());
                ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    loadData();
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
