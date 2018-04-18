package com.yunkahui.datacubeper.home.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
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
import com.yunkahui.datacubeper.common.bean.BaseBeanList;
import com.yunkahui.datacubeper.common.bean.SmartPlanSub;
import com.yunkahui.datacubeper.common.bean.TodayOperationSub;
import com.yunkahui.datacubeper.home.adapter.DesignSubAdapter;
import com.yunkahui.datacubeper.home.logic.DesignSubLogic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/11
 */
public class DesignSubFragment extends BaseFragment {

    private static final String TAG = "DesignSubFragment";
    private List<TodayOperationSub.DesignSub> mTodayOperationSubList = new ArrayList<>();
    private List<SmartPlanSub> mSmartPlanSubList = new ArrayList<>();
    private SwipeMenuRecyclerView mRecyclerView;
    private DesignSubLogic mLogic;
    private DesignSubAdapter mDesignSubAdapter;
    private String mIsPos;
    private ImageView mIvNoData;
    private int mCurrentPage;
    private int mAllPages;
    private boolean mIsTodayOperation;
    private int mPosition;

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
        switch (kind) {
            case 0:
                mIsPos = "10";
                getTodayOperation(10, 1);
                break;
            case 1:
                mIsPos = "11";
                getTodayOperation(10, 1);
                break;
            case 2:
                mIsPos = "other";
                getTodayOperation(10, 1);
                break;
            case 3:
                mIsPos = "10";
                getSmartPlan(10, 1);
                break;
            case 4:
                mIsPos = "11";
                getSmartPlan(10, 1);
                break;
            case 5:
                mIsPos = "other";
                getSmartPlan(10, 1);
                break;
        }
        mIsTodayOperation = kind < 3;
        initRecyclerView();
    }

    private void initRecyclerView() {
        if ("11".equals(mIsPos)) {
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
                    mTodayOperationSubList.remove(adapterPosition);
                    notifyDataSetChanged();
                }
            };
            mRecyclerView.setSwipeMenuItemClickListener(swipeMenuItemClickListener);
        }
        mDesignSubAdapter = new DesignSubAdapter(mActivity, R.layout.layout_list_item_design_sub, mIsTodayOperation ? mTodayOperationSubList : mSmartPlanSubList, mIsPos);
        mDesignSubAdapter.bindToRecyclerView(mRecyclerView);
        mDesignSubAdapter.disableLoadMoreIfNotFullPage();
        mDesignSubAdapter.setEnableLoadMore(true);
        mDesignSubAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_sign:
                        //showSignDialog();
                        break;
                    case R.id.tv_status:
                        if ("11".equals(mIsPos) && "0".equals(mIsTodayOperation ?
                                mTodayOperationSubList.get(position).getOperation() : mSmartPlanSubList.get(position).getOperation())) {
                            String type = mIsTodayOperation ?
                                    mTodayOperationSubList.get(position).getPlan_type().equals("00") ? "消费" : "还款" :
                                    mSmartPlanSubList.get(position).getPlan_type().equals("00") ? "消费" : "还款";
                            double amount = mIsTodayOperation ?
                                    mTodayOperationSubList.get(position).getAmount() : mSmartPlanSubList.get(position).getAmount();
                            int id = mIsTodayOperation ?
                                    mTodayOperationSubList.get(position).getAp_id() : mSmartPlanSubList.get(position).getAp_id();
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
        mDesignSubAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mCurrentPage >= mAllPages) {
                    mDesignSubAdapter.loadMoreEnd();
                } else {
                    if (mIsTodayOperation) {
                        getTodayOperation(10, ++mCurrentPage);
                    } else {
                        getSmartPlan(10, ++mCurrentPage);
                    }
                }
            }
        });
        mRecyclerView.addItemDecoration(new DefaultItemDecoration(mActivity.getResources().getColor(R.color.bg_color_gray_f5f5f5)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mDesignSubAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            String amount = data.getStringExtra("amount");
            if (mIsTodayOperation) {
                mTodayOperationSubList.get(mPosition).setAmount(Double.parseDouble(amount));
            } else {
                mSmartPlanSubList.get(mPosition).setAmount(Double.parseDouble(amount));
            }
            // TODO: 2018/4/18 0018 set business type
            mDesignSubAdapter.notifyItemChanged(mPosition);
            Toast.makeText(mActivity, "信息更新完毕", Toast.LENGTH_SHORT).show();
        }
    }

    /*private void showSignDialog() {
        new AlertDialog.Builder(getActivity()).setTitle("标记交易成功")
                .setMessage("如此次笔交易已经还款成功，请进行标记交易成功")
                .setPositiveButton("标记", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, String> param = new HashMap<>();
                        param.put("auto_planning_id", bean.getId());
                        param.put("version",BaseApplication.getVersion());
                        RequestHelper helper = new RequestHelper(new SpecialConverterFactory(SpecialConverterFactory.ConverterType.Converter_Single));
                        helper.getRequestApi().requestCommon(getString(R.string.slink_do_huankuan_pos), param)
                                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<TopBean>() {
                            @Override public void onCompleted() {}
                            @Override public void onError(Throwable e) {
                                RemindUtil.dismiss();
                                Toast.makeText(BaseApplication.getContext(), "连接超时", Toast.LENGTH_SHORT).show();
                            }
                            @Override public void onNext(TopBean topBean) {
                                RemindUtil.dismiss();
                                if (topBean.getCode() == RequestHelper.success) {
                                    PosAdjustEvent event=new PosAdjustEvent();
                                    event.setEvent(PosAdjustEvent.TAG);
                                    event.setObj(bean);
                                    EventBus.getDefault().post(event);
                                }
                                Toast.makeText(BaseApplication.getContext(), topBean.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", null).show();
    }*/

    //******** 查询智能规划数据 ********
    /*private void getSmartPlan(String num, String page) {
        mLogic.requestSP(mActivity, mIsPos, num, page, new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject baseBean) {
                try {
                    String s = new JSONObject(baseBean.toString()).toString();
                    Log.e(TAG, "requestSP: "+mIsPos+", "+s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "getSmartPlan onFailure: " + mIsPos + ", " + throwable.toString());
            }
        });
    }

    //******** 查询今日操作数据 ********
    private void getTodayOperation(String num, String page) {
        mLogic.requestTO(mActivity, mIsPos, num, page, new SimpleCallBack<JsonObject>() {

            @Override
            public void onSuccess(JsonObject baseBean) {
                try {
                    String s = new JSONObject(baseBean.toString()).toString();
                    Log.e(TAG, "requestTO: "+mIsPos+", "+s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "getTodayOperation onFailure: " + mIsPos + ", " + throwable.toString());
            }
        });
    }*/

    //******** 查询智能规划数据 ********
    private void getSmartPlan(int num, int page) {
        mLogic.requestSmartPlan(mActivity, mIsPos, num, page, new SimpleCallBack<BaseBeanList<SmartPlanSub>>() {
            @Override
            public void onSuccess(BaseBeanList<SmartPlanSub> baseBean) {
                mSmartPlanSubList.clear();
                mSmartPlanSubList.addAll(baseBean.getRespData());
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "getSmartPlan onFailure: " + mIsPos + ", " + throwable.toString());
            }
        });
    }

    //******** 查询今日操作数据 ********
    private void getTodayOperation(int num, int page) {
        mLogic.requestTodayOperation(mActivity, mIsPos, num, page, new SimpleCallBack<BaseBean<TodayOperationSub>>() {

            @Override
            public void onSuccess(BaseBean<TodayOperationSub> baseBean) {
                mCurrentPage = baseBean.getRespData().getPageNum();
                mAllPages = baseBean.getRespData().getPages();
                mTodayOperationSubList.clear();
                mTodayOperationSubList.addAll(baseBean.getRespData().getList());
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "getTodayOperation onFailure: " + mIsPos + ", " + throwable.toString());
            }
        });
    }

    private void notifyDataSetChanged() {
        mDesignSubAdapter.notifyDataSetChanged();
        boolean noData = mIsTodayOperation ? mTodayOperationSubList.size() == 0 : mSmartPlanSubList.size() == 0;
        mIvNoData.setVisibility(noData ? View.VISIBLE : View.GONE);
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
