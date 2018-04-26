package com.yunkahui.datacubeper.bill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.view.ExportTipView;

public class BillSyncFragment extends BaseFragment implements View.OnClickListener {

    private ExportTipView mEtwCardId;
    private ExportTipView mEtwPsd;
    private TextView mTvCommit;

    public static Fragment newInstance(int kind) {
        BillSyncFragment fragment = new BillSyncFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("kind", kind);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        if (getArguments().getInt("kind") == 0) {
            mEtwCardId.getMsg().setHint("用户名");
            mEtwCardId.getTip().setText("官方网上银行设置的用户名");
        } else {
            mEtwCardId.getMsg().setHint("卡号");
            mEtwCardId.getTip().setText("信用卡卡号 (12-20位数字)");
        }
        mEtwPsd.getMsg().setHint("登录密码");
        mEtwPsd.getTip().setText("开通网银时的登录密码，未开通网银可登录官网自助开通");
        mEtwCardId.getMsg().addTextChangedListener(new CustomTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setCommitButtonBg(s);
            }
        });
        mEtwPsd.getMsg().addTextChangedListener(new CustomTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setCommitButtonBg(s);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                break;
            case R.id.tv_service_agreement:
                startActivity(new Intent(mActivity, WebShowActivity.class).putExtra("url", "authorization_agreement.html"));
                break;
        }
    }

    private void setCommitButtonBg(CharSequence s) {
        if (s.length() > 0 && mEtwPsd.getMsg().getText().toString().length() > 0) {
            mTvCommit.setSelected(true);
        } else {
            mTvCommit.setSelected(false);
        }
    }

    @Override
    public void initView(View view) {
        mEtwCardId = view.findViewById(R.id.etw_card_id);
        mEtwPsd = view.findViewById(R.id.etw_psd);
        mTvCommit = view.findViewById(R.id.tv_commit);
        view.findViewById(R.id.tv_service_agreement).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bill_sync;
    }
}
