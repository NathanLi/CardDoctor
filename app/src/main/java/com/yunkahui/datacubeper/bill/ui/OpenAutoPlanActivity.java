package com.yunkahui.datacubeper.bill.ui;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.logic.OpenAutoPlanLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.SimpleDateFormatUtils;
import com.yunkahui.datacubeper.common.utils.StringUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.common.view.SimpleTextView;

import java.util.Date;

//开通自动规划
public class OpenAutoPlanActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private TextView mTextViewBankCardNumber;
    private TextView mTextViewBankCardName;
    private SimpleEditTextView mEditTextViewCVV2;
    private SimpleTextView mSimpleTextViewValidity;
    private EditText mEditTextPhone;
    private TextView mTextViewSendAuthCode;
    private SimpleEditTextView mEditTextViewAuthCode;

    private long mTime;
    private String mBankCardNumber;
    private int mBankCardId;
    private int mSecond = 60;
    private boolean mIsRunning;
    private OpenAutoPlanLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_open_auto_plan);
        super.onCreate(savedInstanceState);
        setTitle("开通自动规划");
    }

    @Override
    public void initData() {
        mLogic = new OpenAutoPlanLogic();
        mBankCardNumber = getIntent().getStringExtra("bank_card_num");
        mBankCardId = getIntent().getIntExtra("user_credit_card_id", 0);
        mTextViewBankCardNumber.setText("卡号：" + mBankCardNumber);
        mTextViewBankCardName.setText(getIntent().getStringExtra("bank_card_name"));
    }

    @Override
    public void initView() {
        mTextViewBankCardNumber = findViewById(R.id.text_view_bank_card_number);
        mTextViewBankCardName = findViewById(R.id.text_view_bank_card_name);
        mEditTextViewCVV2 = findViewById(R.id.simple_input_item_cvv2);
        mSimpleTextViewValidity = findViewById(R.id.simple_text_view_validity);
        mEditTextPhone = findViewById(R.id.edit_text_phone);
        mTextViewSendAuthCode = findViewById(R.id.text_view_send_auth_code);
        mEditTextViewAuthCode = findViewById(R.id.simple_input_item_auth_code);

        mTextViewSendAuthCode.setOnClickListener(this);
        mSimpleTextViewValidity.setOnClickListener(this);
        findViewById(R.id.button_submit).setOnClickListener(this);

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    private void sendSMS(String bankCardNumber, String phone) {
        if (!StringUtils.verifyPhone(phone)) {
            ToastUtils.show(getApplicationContext(), "请输入有效的手机号");
            return;
        }
        LoadingViewDialog.getInstance().show(this);
        mLogic.bindCardSendSMS(this, bankCardNumber, phone, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("发送短信->" + baseBean.getJsonObject().toString());
                ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mIsRunning = true;
                    mTextViewSendAuthCode.setEnabled(false);
                    mTextViewSendAuthCode.setTextColor(getResources().getColor(R.color.text_color_gray_949494));
                    new InnerThread().start();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mTextViewSendAuthCode.setText(mSecond + "秒后可重新发送");
                    break;
                case 1:
                    mTextViewSendAuthCode.setText(getResources().getString(R.string.auth_code_tips));
                    mTextViewSendAuthCode.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mTextViewSendAuthCode.setEnabled(true);
                    break;
            }
            return true;
        }
    });

    private class InnerThread extends Thread {
        @Override
        public void run() {
            while (mIsRunning) {
                if (mSecond > 0) {
                    mSecond--;
                    Message.obtain(mHandler, 0).sendToTarget();
                } else {
                    mSecond = 60;
                    mIsRunning = false;
                    Message.obtain(mHandler, 1).sendToTarget();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(mEditTextViewCVV2.getText())) {
            ToastUtils.show(getApplicationContext(), "请输入CVV2");
            return false;
        }
        if (mTime == 0) {
            ToastUtils.show(getApplicationContext(), "请选择有效期");
            return false;
        }
        if (!StringUtils.verifyPhone(mEditTextPhone.getText().toString())) {
            ToastUtils.show(getApplicationContext(), "请输入有效手机号");
            return false;
        }
        if (TextUtils.isEmpty(mEditTextViewAuthCode.getText())) {
            ToastUtils.show(getApplicationContext(), "请输入验证码");
            return false;
        }
        return true;
    }

    //鉴权
    private void authCreditCard() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.authCreditCard(this, mBankCardId, mEditTextViewCVV2.getText(), mTime, mEditTextPhone.getText().toString(), mEditTextViewAuthCode.getText(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("鉴权->" + baseBean.getJsonObject().toString());
                ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_send_auth_code:
                sendSMS(mBankCardNumber, mEditTextPhone.getText().toString());
                break;
            case R.id.button_submit:
                if (check()) {
                    authCreditCard();
                }
                break;
            case R.id.simple_text_view_validity:
                TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        mTime = date.getTime();
                        mSimpleTextViewValidity.setText(SimpleDateFormatUtils.formatYM(mTime));
                    }
                }).setType(new boolean[]{true, true, false, false, false, false}).build();
                pvTime.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mIsRunning = false;
        super.onDestroy();
    }
}
