package com.yunkahui.datacubeper.home.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.bill.logic.BillSynchronousLogic;
import com.yunkahui.datacubeper.bill.ui.BillDetailActivity;
import com.yunkahui.datacubeper.bill.ui.BillSynchronousActivity;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.PagingScrollHelper;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.PageRecyclerView.PageRecyclerView;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;
import com.yunkahui.datacubeper.home.adapter.HomeNewAdapter;
import com.yunkahui.datacubeper.home.adapter.HomeNewItemAdapter;
import com.yunkahui.datacubeper.home.logic.HomeLogic;

import java.util.ArrayList;
import java.util.List;


/**
 * 新版首页
 */
public class HomeNewFragment extends Fragment {

    private static final int HOME_MENU_PAGE_SIZE = 4;  //首页菜单每页数量

    private SimpleToolbar mSimpleToolBar;
    private RecyclerView mRecyclerView;

    private TextView mTextViewBalance;
    private TextView mTextViewFenRun;
    private RecyclerView mRecyclerViewMenu;
    private RadioGroup mRadioGroupIndicator;

    private HomeNewAdapter mAdapter;
    private List<HomeItem> mMenuHomeItems;
    private HomeLogic mLogic;
    private int mScrollY = 0;
    private List<BillCreditCard.CreditCard> mCreditCardList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_new, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSimpleToolBar = view.findViewById(R.id.tool_bar);

        mLogic = new HomeLogic();
        mMenuHomeItems = new ArrayList<>();
        mCreditCardList = new ArrayList<>();
        mAdapter = new HomeNewAdapter(R.layout.layout_list_item_bill_card, mCreditCardList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        initHeaderView();
        initFooterView();
        onScrollChangeEvent();
        initMenuRecyclerView();
        adapterClickEvent();
        getCreditCardList();
        return view;
    }

    //adapter点击事件
    private void adapterClickEvent() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (mCreditCardList.get(position) == null) {
                    return;
                }
                switch (view.getId()) {
                    case R.id.btn_bill_sync:
                        if (BillSynchronousLogic.judgeBank(mCreditCardList.get(position).getBankCardName()).length == 0) {
                            ToastUtils.show(getActivity(), "暂不支持该银行");
                            return;
                        }
                        Intent intent = new Intent(getActivity(), BillSynchronousActivity.class);
                        intent.putExtra("bank_card_name", mCreditCardList.get(position).getBankCardName());
                        intent.putExtra("bank_card_num", mCreditCardList.get(position).getBankCardNum());
                        startActivity(intent);
                        break;
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mCreditCardList.get(position) == null) {
                    return;
                }
                final String itemTime = TimeUtils.format("yyyy-MM-dd", mCreditCardList.get(position).getRepayDayDate());
                startActivity(new Intent(getActivity(), BillDetailActivity.class)
                        .putExtra("title", mCreditCardList.get(position).getBankCardName())
                        .putExtra("user_credit_card_id", mCreditCardList.get(position).getUserCreditCardId())
                        .putExtra("card_holder", mCreditCardList.get(position).getCardHolder())
                        .putExtra("bank_card_num", mCreditCardList.get(position).getBankCardNum())
                        .putExtra("bank_card_name", mCreditCardList.get(position).getBankCardName())
                        .putExtra("reday_date", itemTime.substring(5))
                        .putExtra("bill_date", mCreditCardList.get(position).getBillDayDate()));
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.e("长安" + position);
                if (mCreditCardList.get(position) == null) {
                    return true;
                }
                showDeleteDialog(mCreditCardList.get(position).getUserCreditCardId());
                return true;
            }
        });

    }

    //首页页面以recyclerView 的头部view形式加入
    private void initHeaderView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_list_header_view_home, null);
        mTextViewBalance = view.findViewById(R.id.text_view_balance);
        mTextViewFenRun = view.findViewById(R.id.text_view_FenRun);
        mRecyclerViewMenu = view.findViewById(R.id.recycler_view_menu);
        mRadioGroupIndicator = view.findViewById(R.id.radio_group_indicator);
        mAdapter.addHeaderView(view);
        mTextViewBalance.setText(Html.fromHtml(String.format(getResources().getString(R.string.account_balance), "-")));
        mTextViewFenRun.setText(Html.fromHtml(String.format(getResources().getString(R.string.add_up_FenRun), "-")));

    }

    //添加尾部
    private void initFooterView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_list_footer_view_home, null);
        mAdapter.addFooterView(view);
    }

    //初始化滑动菜单
    private void initMenuRecyclerView() {
        mMenuHomeItems.addAll(mLogic.parsingJSONForHomeItem(getActivity()));
        int addSize = HOME_MENU_PAGE_SIZE - mMenuHomeItems.size() % HOME_MENU_PAGE_SIZE;
        for (int i = 0; i < addSize; i++) {
            mMenuHomeItems.add(new HomeItem());
        }
        HomeNewItemAdapter adapter = new HomeNewItemAdapter(R.layout.layout_list_item_home_menu, mMenuHomeItems);
        mRecyclerViewMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewMenu.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        PagingScrollHelper helper = new PagingScrollHelper();
        helper.setUpRecycleView(mRecyclerViewMenu);
        helper.setIndicator(getActivity(), mRadioGroupIndicator, mMenuHomeItems.size() / HOME_MENU_PAGE_SIZE, R.drawable.ic_radio_indicator_selector);
    }

    //标题栏滑动背景事件
    private void onScrollChangeEvent() {
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollY += dy;
                float percent = mScrollY * 1f / 255;//百分比
                int alpha = (int) (percent * 255);
                if (alpha > 255) {
                    alpha = 255;
                }
                mSimpleToolBar.setBackgroundColor(Color.argb(alpha, 0, 122, 255));
            }
        });
    }

    //提示删除信用卡弹窗
    private void showDeleteDialog(final int cardId) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
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
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.deleteCreditCard(getActivity(), cardId, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("删除信用卡->" + baseBean.getJsonObject().toString());
                ToastUtils.show(getActivity(), baseBean.getRespDesc());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    getCreditCardList();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity(), "请求失败 " + throwable.toString());
            }
        });
    }

    //******** 查询信用卡列表 ********
    private void getCreditCardList() {
        mLogic.queryCreditCardList(getActivity(), new SimpleCallBack<BaseBean<BillCreditCard>>() {
            @Override
            public void onSuccess(BaseBean<BillCreditCard> baseBean) {
                LogUtils.e("账单->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    DataUtils.setRealName(baseBean.getRespData().getTrueName());
                    List<BillCreditCard.CreditCard> details = baseBean.getRespData().getCardDetail();
                    mCreditCardList.clear();
                    if (details != null) {
                        if (details.size() > 0) {
                            mCreditCardList.addAll(details);
                        } else {
                            mCreditCardList.add(null);
                        }
                    } else {
                        mCreditCardList.add(null);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    mCreditCardList.add(null);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(getActivity(), "获取卡列表失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
