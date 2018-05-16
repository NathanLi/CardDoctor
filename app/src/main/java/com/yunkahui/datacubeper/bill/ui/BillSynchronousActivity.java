package com.yunkahui.datacubeper.bill.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.Image;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.api.BaseUrl;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.login.ui.LoginActivity;
import com.yunkahui.datacubeper.share.ui.WebViewActivity;

import org.json.JSONException;
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
                LoadingViewDialog.getInstance().show(this);
                Intent intent = new Intent(this, BillSynchronousService.class)
                        .putExtra("bank_card_num", getIntent().getStringExtra("bank_card_num"))
                        .putExtra("account", mEditTextAccount.getText().toString())
                        .putExtra("password", mEditTextPassword.getText().toString());
                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
                mIsBind = true;
                break;
            case R.id.text_view_agreement:
                Intent intent2 = new Intent(this, WebViewActivity.class);
                intent2.putExtra("title", "协议");
                intent2.putExtra("url", "file:///android_asset/test_agreement.html");
                startActivity(intent2);
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
                if (message.contains("settled")) {
                    LoadingViewDialog.getInstance().dismiss();
                    Toast.makeText(context, "获取数据成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                try {
                    final JSONObject object = new JSONObject(message.substring(message.indexOf("{")));
                    LogUtils.e("处理的消息为 = " + object.toString());
                    switch (object.optString("type")) {
                        case "returnImgUrl":    //接收图片验证码
                            showImageCodeDialog(object);
                            break;
                        case "Phonecheck":      //接收短信验证码
                            showMessageCodeDialog(object);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LogUtils.e("接收的消息不为JSON = " + message);
                }


            }

        }
    }

    private void showMessageCodeDialog(final JSONObject object) {
        LoadingViewDialog.getInstance().dismiss();
        final AlertDialog.Builder builder = new AlertDialog.Builder(BillSynchronousActivity.this);
        final View view = LayoutInflater.from(BillSynchronousActivity.this).inflate(R.layout.layout_verification_code_dialog, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        ((TextView) view.findViewById(R.id.tv_dialog_title)).setText("请输入短信验证码");
        view.findViewById(R.id.iv_verification_code).setVisibility(View.GONE);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingViewDialog.getInstance().show(BillSynchronousActivity.this);
                EditText etCode = view.findViewById(R.id.et_verification_code);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("type", "Phone_code");
                    jsonObject.put("contents", etCode.getText().toString());
                    jsonObject.put("uid", object.optString("uid"));
                    LogUtils.e("发送短信验证码->" + jsonObject.toString());
                    Intent intent = new Intent(BillSynchronousService.RADIO_SEND_MESSAGE);
                    intent.putExtra("message", jsonObject.toString());
                    mBroadcastManager.sendBroadcast(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
    }

    private void showImageCodeDialog(final JSONObject object) {
        LoadingViewDialog.getInstance().dismiss();
        final AlertDialog.Builder builder = new AlertDialog.Builder(BillSynchronousActivity.this);
        final View view = LayoutInflater.from(BillSynchronousActivity.this).inflate(R.layout.layout_verification_code_dialog, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        LogUtils.e("imageUrl->" + object.optString("imageUrl"));
        ((TextView) view.findViewById(R.id.tv_dialog_title)).setText("请输入图片验证码");
        Glide.with(BillSynchronousActivity.this).load(object.optString("imageUrl")).into(((ImageView) view.findViewById(R.id.iv_verification_code)));
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingViewDialog.getInstance().show(BillSynchronousActivity.this);
                EditText etCode = view.findViewById(R.id.et_verification_code);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("type", "Img_code");
                    jsonObject.put("contents", etCode.getText().toString());
                    jsonObject.put("uid", object.optString("uid"));
                    LogUtils.e("发送手机验证码->" + jsonObject.toString());
                    Intent intent = new Intent(BillSynchronousService.RADIO_SEND_MESSAGE);
                    intent.putExtra("message", jsonObject.toString());
                    mBroadcastManager.sendBroadcast(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
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
