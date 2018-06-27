package com.yunkahui.datacubeper.applyreceipt.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;

/**
 * Created by Administrator on 2018/6/25 0025
 */

public class ExamineResultFragment extends BaseFragment {

    public static final int EXAMINE_SUCCESS = 801;
    public static final int EXAMINE_FAIL = 802;

    private ImageView mIvIcon;
    private TextView mTvResult;
    private TextView mTvDesc;

    @Override
    public void initData() {
        int type = getArguments().getInt("type");
        if (type == EXAMINE_SUCCESS) {
            mIvIcon.setBackgroundResource(R.mipmap.ic_dispose_result);
            mTvResult.setText("审核通过");
        } else {
            mIvIcon.setBackgroundResource(R.mipmap.ic_dispose_fail);
            mTvResult.setText("审核未通过");
        }
    }

    @Override
    public void initView(View view) {
        mIvIcon = view.findViewById(R.id.iv_icon);
        mTvResult = view.findViewById(R.id.tv_result);
        mTvDesc = view.findViewById(R.id.tv_desc);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_examine_result;
    }
}
