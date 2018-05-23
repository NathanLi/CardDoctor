package com.yunkahui.datacubeper.home.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.PlanList;
import com.yunkahui.datacubeper.common.bean.TodayOperationSub;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.home.adapter.DesignSubAdapter;
import com.yunkahui.datacubeper.home.logic.DesignSubLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/11
 */
public class DesignSubFragment extends BaseFragment {

    private SwipeMenuRecyclerView mRecyclerView;
    private View mLayoutLoading;
    private ImageView mImageViewNoData;

    private DesignSubLogic mLogic;
    private DesignSubAdapter mDesignSubAdapter;
    private List<TodayOperationSub.DesignSub> mTodayOperationSubList = new ArrayList<>();
    private List<PlanList.PlanListBean> mPlanListList = new ArrayList<>();
    private String mIsPos;
    private int mCurrentPage = 1;
    private int mAllPages;
    private boolean mIsTodayOperation;
    private int mPosition;
    private int mSize = 10;

    public static Fragment newInstance(int kind) {
        DesignSubFragment fragment = new DesignSubFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("kind", kind);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        mLogic = new DesignSubLogic();
        int kind = getArguments().getInt("kind");
        getDesignData(kind);
        mIsTodayOperation = kind < 3;
        initRecyclerView();
    }

    private void getDesignData(int kind) {
        switch (kind) {
            case 0:
                mIsPos = "10";
                getTodayOperation(mSize, mCurrentPage);
                break;
            case 1:
                mIsPos = "11";
                getTodayOperation(mSize, mCurrentPage);
                break;
            case 2:
                mIsPos = "other";
                getTodayOperation(mSize, mCurrentPage);
                break;
            case 3:
                mIsPos = "10";
                getPlanList(mSize, mCurrentPage);
                break;
            case 4:
                mIsPos = "11";
                getPlanList(mSize, mCurrentPage);
                break;
            case 5:
                mIsPos = "other";
                getPlanList(mSize, mCurrentPage);
                break;
        }
    }

    private void initRecyclerView() {
        if ("11".equals(mIsPos)) {
            //******** 设置侧滑删除 ********
            SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
                @Override
                public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                    int width = getResources().getDimensionPixelSize(R.dimen.album_dp_80);
                    SwipeMenuItem item = new SwipeMenuItem(getActivity());
                    item.setText("删除");
                    item.setTextSize(16);
                    item.setWidth(width);
                    item.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                    item.setBackgroundColor(Color.parseColor("#FF0000"));
                    item.setTextColor(Color.parseColor("#FFFFFF"));
                    rightMenu.addMenuItem(item);
                }
            };
            mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
            SwipeMenuItemClickListener swipeMenuItemClickListener = new SwipeMenuItemClickListener() {
                @Override
                public void onItemClick(SwipeMenuBridge menuBridge) {
                    menuBridge.closeMenu();
                    int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                    if (mIsTodayOperation && mTodayOperationSubList.size() > 0) {
                        mTodayOperationSubList.remove(adapterPosition);
                    } else if (!mIsTodayOperation && mPlanListList.size() > 0) {
                        mPlanListList.remove(adapterPosition);
                    }
                    mDesignSubAdapter.notifyDataSetChanged();
                }
            };
            mRecyclerView.setSwipeMenuItemClickListener(swipeMenuItemClickListener);
        }
        mDesignSubAdapter = new DesignSubAdapter(getActivity(), R.layout.layout_list_item_design_sub, mIsTodayOperation ? mTodayOperationSubList : mPlanListList, mIsPos);
        mRecyclerView.addItemDecoration(new DefaultItemDecoration(getActivity().getResources().getColor(R.color.bg_color_gray_f5f5f5)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDesignSubAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtils.e("-------------上拉加载--------");
                if (mIsTodayOperation) {
                    getTodayOperation(mSize, mCurrentPage);
                } else {
                    getPlanList(mSize, mCurrentPage);
                }
            }
        }, mRecyclerView);
        mDesignSubAdapter.disableLoadMoreIfNotFullPage();
        mDesignSubAdapter.setEnableLoadMore(true);
        mDesignSubAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_sign:
                        showSignDialog(position, mIsTodayOperation ? mTodayOperationSubList.get(position).getAp_id() : mPlanListList.get(position).getAp_id());
                        break;
                    case R.id.tv_status:
                        if ("11".equals(mIsPos) && "0".equals(mIsTodayOperation ?
                                mTodayOperationSubList.get(position).getOperation() : mPlanListList.get(position).getOperation())) {
                            String type = mIsTodayOperation ?
                                    mTodayOperationSubList.get(position).getPlan_type().equals("00") ? "消费" : "还款" :
                                    mPlanListList.get(position).getPlan_type().equals("00") ? "消费" : "还款";
                            double amount = mIsTodayOperation ?
                                    mTodayOperationSubList.get(position).getAmount() : mPlanListList.get(position).getAmount();
                            int id = mIsTodayOperation ?
                                    mTodayOperationSubList.get(position).getAp_id() : mPlanListList.get(position).getAp_id();
                            mPosition = position;
                            startActivityForResult(new Intent(mActivity, AdjustPlanActivity.class)
                                    .putExtra("type", type)
                                    .putExtra("amount", String.valueOf(amount))
                                    .putExtra("id", String.valueOf(id)), 1);
                        }
                        break;
                }
            }
        });

        mRecyclerView.setAdapter(mDesignSubAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            String amount = data.getStringExtra("amount");
            if ("repay".equals(data.getStringExtra("type"))) {
                if (mIsTodayOperation) {
                    mTodayOperationSubList.get(mPosition).setAmount(Integer.parseInt(amount));
                } else {
                    mPlanListList.get(mPosition).setAmount(Integer.parseInt(amount));
                }
                mDesignSubAdapter.notifyItemChanged(mPosition);
                Toast.makeText(mActivity, "信息更新完毕", Toast.LENGTH_SHORT).show();
            } else if ("expense".equals(data.getStringExtra("type"))) {
                String businessType = data.getStringExtra("business_type");
                if (mIsTodayOperation) {
                    mTodayOperationSubList.get(mPosition).setAmount(Integer.parseInt(amount));
                    mTodayOperationSubList.get(mPosition).setBusiness_name(businessType);
                } else {
                    mPlanListList.get(mPosition).setAmount(Integer.parseInt(amount));
                    mPlanListList.get(mPosition).setBusiness_name(businessType);
                }
                // TODO: 2018/4/18 0018 set business type
                mDesignSubAdapter.notifyItemChanged(mPosition);
                Toast.makeText(mActivity, "信息更新完毕", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void showSignDialog(final int position, final int id) {
        new AlertDialog.Builder(getActivity())
                .setTitle("标记交易成功")
                .setMessage("如此次笔交易已经还款成功，请进行标记交易成功")
                .setPositiveButton("标记", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadingViewDialog.getInstance().show(getActivity());
                        mLogic.signRepay(mActivity, id, new SimpleCallBack<BaseBean>() {
                            @Override
                            public void onSuccess(BaseBean baseBean) {
                                LoadingViewDialog.getInstance().dismiss();
                                LogUtils.e("标记->" + baseBean.getJsonObject().toString());
                                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                                    if (mIsTodayOperation) {
                                        mTodayOperationSubList.get(position).setOperation("1");
                                    } else {
                                        mPlanListList.get(position).setOperation("1");
                                    }
                                    mDesignSubAdapter.notifyItemChanged(position);
                                } else {
                                    Toast.makeText(mActivity, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                LoadingViewDialog.getInstance().dismiss();
                                Toast.makeText(mActivity, "标记交易失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    //******** 查询规划列表 ********
    private void getPlanList(int pageSize, int pageNum) {
        mLogic.requestPlanList(mActivity, mIsPos, pageSize, pageNum, new SimpleCallBack<BaseBean<PlanList>>() {
            @Override
            public void onSuccess(BaseBean<PlanList> baseBean) {
                mLayoutLoading.setVisibility(View.GONE);
                LogUtils.e("智能规划->" + mIsPos + ", " + baseBean.toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    if (baseBean.getRespData() == null) {
                        mDesignSubAdapter.loadMoreEnd();
                        return;
                    }
                    mPlanListList.addAll(baseBean.getRespData().getList());
                    mDesignSubAdapter.notifyDataSetChanged();
                    if (baseBean.getRespData().getPages() > mCurrentPage) {
                        mDesignSubAdapter.loadMoreComplete();
                    } else {
                        mDesignSubAdapter.loadMoreEnd();
                    }
                    mCurrentPage++;
                    if (mPlanListList.size() > 0) {
                        mImageViewNoData.setVisibility(View.GONE);
                    } else {
                        mImageViewNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    mDesignSubAdapter.loadMoreFail();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                mLayoutLoading.setVisibility(View.GONE);
                mDesignSubAdapter.loadMoreFail();
                Toast.makeText(mActivity, "获取智能规划失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //******** 查询今日操作 ********
    private void getTodayOperation(int pageSize, int pageNum) {
        mLogic.requestTodayOperation(mActivity, mIsPos, pageSize, pageNum, new SimpleCallBack<BaseBean<TodayOperationSub>>() {

            @Override
            public void onSuccess(BaseBean<TodayOperationSub> baseBean) {
                mLayoutLoading.setVisibility(View.GONE);
                LogUtils.e("今日操作-" + mIsPos + "->" + baseBean.toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    if (baseBean.getRespData() == null) {
                        mDesignSubAdapter.loadMoreEnd();
                        return;
                    }
                    mTodayOperationSubList.addAll(baseBean.getRespData().getList());
                    mDesignSubAdapter.notifyDataSetChanged();
                    if (baseBean.getRespData().getPages() > mCurrentPage) {
                        mDesignSubAdapter.loadMoreComplete();
                    } else {
                        LogUtils.e("------------  loadMoreEnd --------------------");
                        mDesignSubAdapter.loadMoreEnd();
                    }
                    mCurrentPage++;
                    if (mTodayOperationSubList.size() > 0) {
                        mImageViewNoData.setVisibility(View.GONE);
                    } else {
                        mImageViewNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    mDesignSubAdapter.loadMoreFail();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                mLayoutLoading.setVisibility(View.GONE);
                Toast.makeText(mActivity, "获取今日操作失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initView(View view) {
        mLayoutLoading = view.findViewById(R.id.rl_loading_view);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mImageViewNoData = view.findViewById(R.id.iv_no_data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_design_sub;
    }
}
