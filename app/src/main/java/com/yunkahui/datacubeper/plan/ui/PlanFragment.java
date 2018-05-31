package com.yunkahui.datacubeper.plan.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.Message;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.view.PlanSpinner;
import com.yunkahui.datacubeper.mine.ui.MessageItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment implements PlanSpinner.OnSpinnerClickListener {

    private RecyclerView mRecyclerView;
    private TextView mTextViewPosPlan;

    private PlanSpinner mPlanSpinnerDataType;
    private PlanSpinner mPlanSpinnerListType;
    private PlanSpinner mPlanSpinnerCardType;

    private AppBarLayout mAppBarLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mPlanSpinnerDataType = view.findViewById(R.id.plan_spinner_data_type);
        mPlanSpinnerListType = view.findViewById(R.id.plan_spinner_list_type);
        mPlanSpinnerCardType = view.findViewById(R.id.plan_spinner_card_type);
        mAppBarLayout = view.findViewById(R.id.app_bar_layout);

        initSpinner();

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("测试--> " + i);
        }
        MyAdapter adapter = new MyAdapter(R.layout.layout_list_item_message, datas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mPlanSpinnerDataType.updatePopupWindow();
                mPlanSpinnerListType.updatePopupWindow();
                mPlanSpinnerCardType.updatePopupWindow();
            }
        });

        return view;
    }

    private void initSpinner() {

        mPlanSpinnerDataType.setOnSpinnerClickListener(this);
        mPlanSpinnerListType.setOnSpinnerClickListener(this);
        mPlanSpinnerCardType.setOnSpinnerClickListener(this);

        List<String> dataTypes = new ArrayList<>();
        dataTypes.add("全部");
        dataTypes.add("POS规划");
        dataTypes.add("自动规划");
        dataTypes.add("其他消费");
        mPlanSpinnerDataType.setList(dataTypes);

        List<String> listTypes = new ArrayList<>();
        listTypes.add("不限");
        listTypes.add("今日操作");
        listTypes.add("明日操作");
        mPlanSpinnerListType.setList(listTypes);
    }

    @Override
    public void onSpinnerClick() {
        mAppBarLayout.setExpanded(false);
    }


    class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public MyAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            Message message = new Message();
            message.setTitle(item);
            ((MessageItemView) helper.getView(R.id.message_item_view)).setData(message);
        }
    }

}
