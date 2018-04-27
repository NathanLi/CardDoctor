package com.yunkahui.datacubeper.bill.ui;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.api.BaseUrl;
import com.yunkahui.datacubeper.common.utils.LogUtils;

import org.json.JSONObject;

import java.util.List;

//账单同步
public class BillSynchronousActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private TabLayout mTabLayout;
    private EditText mEditTextAccount;
    private TextView mTextViewAccountMessage;
    private EditText mEditTextPassword;
    private TextView mTextViewPasswordMessage;
    private LinearLayout mLinearLayoutPassword;

    private List<String> mTabList;
    private boolean mIsBind;

    private LocalBroadcastManager mBroadcastManager;
    private MyBroadcastReceiver mReceiver;
    private ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bill_synchronous);
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("title"));

        mReceiver = new MyBroadcastReceiver();
        mBroadcastManager = LocalBroadcastManager.getInstance(this);

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BillSynchronousService.RADIO_RECEIVE_MESSAGE);
        mBroadcastManager.registerReceiver(mReceiver, filter);
    }

    @Override
    public void initData() {
        mTabList = getIntent().getStringArrayListExtra("tabs");
        for (int i = 0; i < mTabList.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTabList.get(i)));
        }
        selectTabs(mTabList.size() > 0 ? mTabList.get(0) : "");
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectTabs(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mEditTextAccount = findViewById(R.id.edit_text_account);
        mTextViewAccountMessage = findViewById(R.id.text_view_account_message);
        mEditTextPassword = findViewById(R.id.edit_text_password);
        mTextViewPasswordMessage = findViewById(R.id.text_view_password_message);
        mLinearLayoutPassword = findViewById(R.id.linear_layout_password);

        findViewById(R.id.button_submit).setOnClickListener(this);
        findViewById(R.id.text_view_agreement).setOnClickListener(this);
    }

    private void selectTabs(String tabs) {
        switch (tabs) {
            case "用户名":
                initAccountNameTabs();
                break;
            case "卡号":
                initCardNumberTabs();
                break;
        }
    }

    //用户名设置
    private void initAccountNameTabs() {
        mEditTextAccount.setHint("请输入用户名");
        mTextViewAccountMessage.setText("官方网上银行设置的用户名");
        mLinearLayoutPassword.setVisibility(View.VISIBLE);
        mEditTextPassword.setHint("登陆密码");
        mTextViewPasswordMessage.setText("开通网银时的登陆密码，未开通网银可登陆官网自助开通");
    }

    //卡号设置
    private void initCardNumberTabs() {
        mEditTextAccount.setHint("请输入卡号");
        mTextViewAccountMessage.setText("信用卡卡号（12-20位数字）");
        mLinearLayoutPassword.setVisibility(View.VISIBLE);
        mEditTextPassword.setHint("登陆密码");
        mTextViewPasswordMessage.setText("开通网银时的登陆密码，未开通网银可登陆官网自助开通");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                Intent intent = new Intent(this, BillSynchronousService.class);
//                startService(intent);
                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
                mIsBind = true;
                break;
            case R.id.text_view_agreement:
                LogUtils.e("点击发送RADIO_SEND_MESSAGE");
                Intent intent1 = new Intent(BillSynchronousService.RADIO_SEND_MESSAGE);
                intent1.putExtra("message", "{\"type\":\"analyzer_do_spider\",\"login_pwd\":\"909193\",\"login_uid\":\"450802199311042058\",\n" +
                        "\"card_id\":\"6225768607613864\",\"uid\":\"jjjjj7777744444\",\"user_code\":\"" + BaseUrl.getUSER_ID() + "\",\"org_number\":\"" + getResources().getString(R.string.org_number) + "\"}");
                mBroadcastManager.sendBroadcast(intent1);
                break;
        }
    }

    //爬虫（socket）回调广播接收
    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(BillSynchronousService.RADIO_RECEIVE_MESSAGE)) {
                String message = intent.getStringExtra("message");
                LogUtils.e("接收的消息为 = " + message);
                try {
                    JSONObject object = new JSONObject(message);

                    switch (object.optString("type")) {
                        case "returnImgUrl":    //接收图片验证码
                            break;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("接收的消息不为为JSON = " + message);
                }


            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBroadcastManager.unregisterReceiver(mReceiver);
    }

    @Override
    protected void onDestroy() {
        if (mIsBind)
            unbindService(mServiceConnection);
        mBroadcastManager.unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
