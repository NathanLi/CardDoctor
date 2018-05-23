package com.yunkahui.datacubeper.share.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.wang.avi.AVLoadingIndicatorView;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.Member;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.share.adapter.MemberAdapter;
import com.yunkahui.datacubeper.share.logic.MemberLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD1 on 2018/4/10
 */
public class MemberActivity extends AppCompatActivity implements IActivityStatusBar {

    private RecyclerView mRecyclerView;
    private AVLoadingIndicatorView mAVLoadingIndicatorView;

    private MemberLogic mLogic;
    private List<Member.MemberData> mList;
    private MemberAdapter mAdapter;

    private int mPage = 1;

    @Override
    public void initData() {
        mLogic = new MemberLogic();
        mList = new ArrayList<>();
        getMemberList(mPage);
        mAdapter = new MemberAdapter(R.layout.layout_list_item_member, mList);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setEmptyView(R.layout.layout_no_data);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMemberList(mPage);
            }
        }, mRecyclerView);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setEnableLoadMore(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getMemberList(int page) {
        mLogic.getMemberList(this, getIntent().getBooleanExtra("isVip", false) ? "VIP" : "COMMON", 10, page, new SimpleCallBack<BaseBean<Member>>() {
            @Override
            public void onSuccess(BaseBean<Member> baseBean) {
                LogUtils.e("成员列表->" + baseBean.toString());
                mAVLoadingIndicatorView.setVisibility(View.GONE);
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mPage++;
                    mList.addAll(baseBean.getRespData().getList());
                    mAdapter.notifyDataSetChanged();
                    if (baseBean.getRespData().getPages() > mPage) {
                        mAdapter.loadMoreComplete();
                    } else {
                        mAdapter.loadMoreEnd();
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                mAVLoadingIndicatorView.setVisibility(View.GONE);
                mAdapter.loadMoreFail();
                Toast.makeText(MemberActivity.this, "获取成员失败" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mAVLoadingIndicatorView = findViewById(R.id.av_loading_view);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_member);
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getBooleanExtra("isVip", false) ? "VIP会员" : "普通会员");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}