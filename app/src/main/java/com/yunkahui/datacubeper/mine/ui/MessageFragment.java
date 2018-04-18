package com.yunkahui.datacubeper.mine.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.Message;
import com.yunkahui.datacubeper.common.bean.MessageGroup;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.mine.adapter.MessageListAdapter;
import com.yunkahui.datacubeper.mine.logic.MessageLogic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    public static final int TYPE_NOTICE = 101;
    public static final int TYPE_SYSTEM = 102;

    private RecyclerView mRecyclerViewMessage;
    private ImageView mImageViewNoData;

    private int mType;
    private long mStartTime;
    private int mPage = 1;
    private int mPageSum;
    private MessageLogic mLogic;
    private List<Message> mMessageList;
    private MessageListAdapter mListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        mType = getArguments().getInt("type");

        mRecyclerViewMessage = view.findViewById(R.id.recycler_view);
        mImageViewNoData = view.findViewById(R.id.iv_no_data);

        mLogic = new MessageLogic();
        mMessageList = new ArrayList<>();
        mListAdapter = new MessageListAdapter(R.layout.layout_list_item_message, mMessageList);
        mRecyclerViewMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewMessage.setAdapter(mListAdapter);
        initData();
        loadData();
        return view;
    }

    private void initData(){

        mListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(getActivity(),MessageDetailActivity.class);
                intent.putExtra("id",mMessageList.get(position).getSys_notice_id());
                startActivity(intent);
            }
        });

        mListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        },mRecyclerViewMessage);
    }


    public void loadData() {
        if (mStartTime == 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.MONTH, -2);
            mStartTime = calendar.getTimeInMillis() / 1000;
        }
        mLogic.checkNewMessageList(getActivity(), mStartTime + "", mType == TYPE_NOTICE ? "00" : "01", mPage, new SimpleCallBack<BaseBean<MessageGroup>>() {
            @Override
            public void onSuccess(BaseBean<MessageGroup> messageGroupBaseBean) {
                LogUtils.e("消息->" + messageGroupBaseBean.toString());
                if (RequestUtils.SUCCESS.equals(messageGroupBaseBean.getRespCode())) {
                    mPageSum=messageGroupBaseBean.getRespData().getPages();
                    mMessageList.addAll(messageGroupBaseBean.getRespData().getList());
                    mListAdapter.notifyDataSetChanged();
                    if(mMessageList.size()==0){
                        mImageViewNoData.setVisibility(View.VISIBLE);
                    }else{
                        mImageViewNoData.setVisibility(View.GONE);
                    }
                    if(mPage>=mPageSum){
                        mListAdapter.loadMoreEnd();
                    }else{
                        mPage++;
                        mListAdapter.loadMoreComplete();
                    }
                }else{
                    mListAdapter.loadMoreFail();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                ToastUtils.show(getActivity(),"请求失败 "+throwable.toString());
            }
        });

    }


}
