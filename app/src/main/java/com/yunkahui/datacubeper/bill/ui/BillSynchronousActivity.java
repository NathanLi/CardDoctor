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
import android.text.InputType;
import android.text.TextUtils;
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
import com.yunkahui.datacubeper.bill.logic.BillSynchronousLogic;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.share.ui.WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

//账单同步
public class BillSynchronousActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    public static final int TYPE_CARD_NUMBER = 1001;  //卡号
    public static final int TYPE_USER = 1002;   //用户名
    public static final int TYPE_ID_CARD = 1003;  //身份证
    public static final int TYPE_PHONE = 1004;    //手机号
    public static final int TYPE_ID_CARD_NO_PASSWORD = 1005;  //身份证没密码
    public static final int TYPE_CARD_NUMBER_NO_PASSWORD = 1006;  //卡号没密码
    public static final int TYPE_SPIDER_COMPLETE = 1007;


    private TabLayout mTabLayout;
    private EditText mEditTextAccount;
    private TextView mTextViewAccountMessage;
    private EditText mEditTextPassword;
    private TextView mTextViewPasswordMessage;
    private LinearLayout mLinearLayoutPassword;

    private List<BillSynchronousLogic.Tabs> mTabList;
    private boolean mIsBind;

    private LocalBroadcastManager mBroadcastManager;
    private MyBroadcastReceiver mReceiver;
    private ServiceConnection mServiceConnection;
    private String mBankName;
    private int mType;
    private BillSynchronousLogic mLogic;
    private TextView mTvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bill_synchronous);
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("bank_name"));

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
        mBankName = getIntent().getStringExtra("bank_card_name");
        mLogic = new BillSynchronousLogic();
        mTabList = mLogic.getTabs(BillSynchronousLogic.judgeBank(mBankName));
        for (int i = 0; i < mTabList.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTabList.get(i).getTitle()));
            LogUtils.e("支持->" + mTabList.get(i).getTitle());
        }
        selectTabs(mTabList.size() > 0 ? mTabList.get(0).getId() : 1001);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectTabs(mTabList.get(tab.getPosition()).getId());
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
        mTvError = findViewById(R.id.tv_error);

        findViewById(R.id.button_submit).setOnClickListener(this);
        findViewById(R.id.text_view_agreement).setOnClickListener(this);
    }

    //切换tab
    private void selectTabs(int type) {
        mEditTextAccount.getText().clear();
        mEditTextPassword.getText().clear();
        mType = type;
        switch (type) {
            case TYPE_CARD_NUMBER:
                initCardNumberTabs();
                break;
            case TYPE_USER:
                initAccountNameTabs();
                break;
            case TYPE_ID_CARD:
                initIdCardTabs();
                break;
            case TYPE_PHONE:
                initPhoneTabs();
                break;
            case TYPE_ID_CARD_NO_PASSWORD:
                initIdCardNoPasswordTabs();
                break;
            case TYPE_CARD_NUMBER_NO_PASSWORD:
                initCardNumberNoPasswordTabs();
                break;
        }
    }

    //用户名设置
    private void initAccountNameTabs() {
        mEditTextAccount.setHint("请输入用户名");
        mEditTextAccount.setInputType(InputType.TYPE_CLASS_TEXT);
        mTextViewAccountMessage.setText("官方网上银行设置的用户名");
        mLinearLayoutPassword.setVisibility(View.VISIBLE);
        mEditTextPassword.setHint("登陆密码");
        mTextViewPasswordMessage.setText("开通网银时的登陆密码，未开通网银可登陆官网自助开通");
    }

    //卡号设置
    private void initCardNumberTabs() {
        mEditTextAccount.setHint("请输入卡号");
        mEditTextAccount.setInputType(InputType.TYPE_CLASS_NUMBER);
        mTextViewAccountMessage.setText("信用卡卡号（12-20位数字）");
        mLinearLayoutPassword.setVisibility(View.VISIBLE);
        mEditTextPassword.setHint("登陆密码");
        mTextViewPasswordMessage.setText("开通网银时的登陆密码，未开通网银可登陆官网自助开通");
    }

    //身份证设置
    private void initIdCardTabs() {
        mEditTextAccount.setHint("请输入身份证号");
        mEditTextAccount.setInputType(InputType.TYPE_CLASS_TEXT);
        mTextViewAccountMessage.setText("证件号码如有字母，请注意区分大小写");
        mLinearLayoutPassword.setVisibility(View.VISIBLE);
        mEditTextPassword.setHint("登陆密码");
        mTextViewPasswordMessage.setText("6位数字，忘记密码请登陆官网重置");
    }

    //手机号设置
    private void initPhoneTabs() {
        mEditTextAccount.setHint("请输入电话号码");
        mEditTextAccount.setInputType(InputType.TYPE_CLASS_NUMBER);
        mTextViewAccountMessage.setText("输入绑定的电话号码");
        mLinearLayoutPassword.setVisibility(View.VISIBLE);
        mEditTextPassword.setHint("登陆密码");
        mTextViewPasswordMessage.setText("开通网银时的登陆密码，未开通网银可登陆官网自助开通");
    }

    //身份证设置（不输入密码）
    private void initIdCardNoPasswordTabs() {
        mEditTextAccount.setHint("请输入身份证号");
        mEditTextAccount.setInputType(InputType.TYPE_CLASS_TEXT);
        mTextViewAccountMessage.setText("证件号码如有字母，请注意区分大小写");
        mLinearLayoutPassword.setVisibility(View.INVISIBLE);
    }

    //卡号设置（不输入密码）
    private void initCardNumberNoPasswordTabs() {
        mEditTextAccount.setHint("请输入卡号");
        mEditTextAccount.setInputType(InputType.TYPE_CLASS_NUMBER);
        mTextViewAccountMessage.setText("信用卡卡号（12-20位数字）");
        mLinearLayoutPassword.setVisibility(View.INVISIBLE);
    }

    private boolean verify() {
        switch (mType) {
            case TYPE_CARD_NUMBER:
            case TYPE_USER:
            case TYPE_ID_CARD:
            case TYPE_PHONE:
                if (TextUtils.isEmpty(mEditTextAccount.getText().toString()) || TextUtils.isEmpty(mEditTextPassword.getText().toString())) {
                    return false;
                }
            case TYPE_ID_CARD_NO_PASSWORD:
            case TYPE_CARD_NUMBER_NO_PASSWORD:
                if (TextUtils.isEmpty(mEditTextAccount.getText().toString())) {
                    return false;
                }
        }
        return true;
    }


    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                if (verify()) {
                    LoadingViewDialog.getInstance().show(this);
                    Intent intent = new Intent(this, BillSynchronousService.class)
                            .putExtra("bank_card_num", getIntent().getStringExtra("bank_card_num"))
                            .putExtra("account", mEditTextAccount.getText().toString())
                            .putExtra("password", mEditTextPassword.getText().toString());
                    if (mIsBind) {
                        unbindService(mServiceConnection);
                    }
                    bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
                    mTvError.setText("");
                    mIsBind = true;
                } else {
                    ToastUtils.show(getApplicationContext(), "请完善信息");
                }
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
                try {
                    int index = message.indexOf("{");
                    if (index != -1) {
                        final JSONObject object = new JSONObject(message.substring(index));
                        LogUtils.e("处理的消息为 = " + object.toString());
                        switch (object.optString("type")) {
                            case "returnImgUrl":    //接收图片验证码
                                showImageCodeDialog(object);
                                break;
                            case "Phonecheck":      //接收短信验证码
                                showMessageCodeDialog(object);
                                break;
                            case "complete":
                                LogUtils.e("->接收成功，即将结束");
                                LoadingViewDialog.getInstance().dismiss();
                                Toast.makeText(context, "获取数据成功", Toast.LENGTH_SHORT).show();
                                BillSynchronousActivity.this.setResult(TYPE_SPIDER_COMPLETE);
                                finish();
                                break;
                        }
                        //******** 无数据 ********
                        if ("failed".equals(object.optString("result")) && object.toString().contains("reason")) {
                            LoadingViewDialog.getInstance().dismiss();
                            mTvError.setText(object.optString("reason"));
                        }
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
