package com.yunkahui.datacubeper.share.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.Records;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.share.logic.RecordListLogic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.PUT;


/**
 * 一些收入指出的记录列表页面   （POS分润提现）
 */
public class RecordListFragment extends Fragment {

    public static final String TYPE_INTEGRAL_GAIN = "gain";   //积分收入
    public static final String TYPE_INTEGRAL_EXPEND = "expend";   //积分支出
    public static final String TYPE_POS_FEN_RUN_WITHDRAW="";    //pos分润提现

    private final int PAGE_SIZE = 20;

    private RecyclerView mRecyclerView;
    private InnerRecyclerViewAdapter mRecyclerViewAdapter;
    private List<Records.RecordData> mRecordDataList;
    private RecordListLogic mLogic;
    private int mCurrentPage = 1;

    private String mType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLogic = new RecordListLogic();
        mType = getArguments().getString("type") == null ? "" : getArguments().getString("type");
        initRecyclerView();
        loadData();
        return view;
    }

    private void initRecyclerView() {
        mRecordDataList = new ArrayList<>();
        mRecyclerViewAdapter = new InnerRecyclerViewAdapter(R.layout.layout_list_item_trade_record, mRecordDataList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, mRecyclerView);
        mRecyclerViewAdapter.disableLoadMoreIfNotFullPage();
        mRecyclerViewAdapter.setEnableLoadMore(true);
        mRecyclerViewAdapter.setEmptyView(R.layout.layout_no_data);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }


    private void loadData() {
        switch (mType) {
            case TYPE_POS_FEN_RUN_WITHDRAW:
                break;
            case TYPE_INTEGRAL_GAIN:
            case TYPE_INTEGRAL_EXPEND:
                loadIntegralData();
                break;
        }

    }

    private void loadIntegralData() {
        //mLogic.loadIntegealData(getActivity(), PAGE_SIZE, mCurrentPage, mType, new InnerCallBack());
    }


    class InnerCallBack extends SimpleCallBack<BaseBean<Records>> {

        @Override
        public void onSuccess(BaseBean<Records> recordsBaseBean) {
            LogUtils.e(mType + "记录数据->" + recordsBaseBean.getJsonObject().toString());
            if (RequestUtils.SUCCESS.equals(recordsBaseBean.getRespCode())) {
                mRecordDataList.addAll(recordsBaseBean.getRespData().getList());
                mRecyclerViewAdapter.notifyDataSetChanged();
                if (mCurrentPage >= recordsBaseBean.getRespData().getPages()) {
                    mRecyclerViewAdapter.loadMoreEnd();
                } else {
                    mRecyclerViewAdapter.loadMoreComplete();
                }
                mCurrentPage++;
            }
        }

        @Override
        public void onFailure(Throwable throwable) {
            ToastUtils.show(getActivity(), "请求失败" + throwable.toString());
        }
    }


    class InnerRecyclerViewAdapter extends BaseQuickAdapter<Records.RecordData, BaseViewHolder> {

        public InnerRecyclerViewAdapter(int layoutResId, @Nullable List<Records.RecordData> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Records.RecordData item) {
            helper.setText(R.id.tv_title, item.getTrade_type_desc());
            helper.setText(R.id.tv_time, TimeUtils.format(TimeUtils.DEFAULT_PATTERN_WITH_HMS, item.getCreate_time()));
            helper.setText(R.id.tv_money, item.getChange_amount());
        }
    }

}
