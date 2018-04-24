package com.yunkahui.datacubeper.bill.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.view.ExportTipView;

/**
 * @author WYF on 2018/4/23/023.
 */
public class BillSyncActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private ExportTipView mEtwCardId;
    private ExportTipView mEtwPsd;
    private TextView mTvCommit;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bill_sync);
        super.onCreate(savedInstanceState);
        setTitle("工商银行");
    }

    @Override
    public void initData() {
        mTabLayout.addTab(mTabLayout.newTab().setText("卡号"));
        mEtwCardId.getMsg().setHint("卡号");
        mEtwCardId.getTip().setText("信用卡卡号 (12-20位数字)");
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

    private void setCommitButtonBg(CharSequence s) {
        if (s.length() > 0 && mEtwPsd.getMsg().getText().toString().length() > 0) {
            mTvCommit.setSelected(true);
        } else {
            mTvCommit.setSelected(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                break;
            case R.id.tv_service_agreement:
                startActivity(new Intent(this, WebShowActivity.class).putExtra("url", "authorization_agreement.html"));
                break;
        }
    }

    @Override
    public void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mEtwCardId = findViewById(R.id.etw_card_id);
        mEtwPsd = findViewById(R.id.etw_psd);
        mTvCommit = findViewById(R.id.tv_commit);
        findViewById(R.id.tv_service_agreement).setOnClickListener(this);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
