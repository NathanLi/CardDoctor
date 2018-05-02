package com.yunkahui.datacubeper.login.ui;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.StringUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.login.logic.ForgetPasswordLogic;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordFragment extends BaseFragment implements View.OnClickListener {

    private EditText mEditTextPhone;
    private EditText mEditTextAuthCode;
    private Button mButtonNext;
    private TextView mTextViewSendCode;
    private boolean mRunning;
    private ForgetPasswordLogic mLogic;
    private int mSeconds = 60;

    @Override
    public void initData() {
        mLogic = new ForgetPasswordLogic();

    }

    @Override
    public void initView(View view) {
        mEditTextPhone = view.findViewById(R.id.edit_text_phone);
        mEditTextAuthCode = view.findViewById(R.id.edit_text_auth_code);
        mButtonNext = view.findViewById(R.id.button_next);
        mTextViewSendCode = view.findViewById(R.id.text_view_send_auth_code);

        mEditTextPhone.addTextChangedListener(new InnerTextChangerListener());
        mEditTextAuthCode.addTextChangedListener(new InnerTextChangerListener());
        view.findViewById(R.id.text_view_send_auth_code).setOnClickListener(this);
        mButtonNext.setOnClickListener(this);

        mEditTextPhone.addTextChangedListener(new InnerTextChangerListener());
        mEditTextAuthCode.addTextChangedListener(new InnerTextChangerListener());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_forget_password;
    }


    /**
     * 发送验证码
     */
    public void sendSMS(String phone) {
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.sendSMS(getActivity(), phone, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("发送短信->" + baseBean.getJsonObject().toString());
                JSONObject object = baseBean.getJsonObject();
                ToastUtils.show(getActivity().getApplicationContext(), object.optString("respDesc"));
                if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                    mRunning = true;
                    mTextViewSendCode.setEnabled(false);
                    new InnerThread().start();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity().getApplicationContext(), "发送失败");
            }
        });
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                mTextViewSendCode.setText(mSeconds + "秒后可重新发送");
                mTextViewSendCode.setTextColor(Color.parseColor("#CCCCCC"));
            } else {
                mTextViewSendCode.setText("发送验证码");
                mSeconds = 60;
                mTextViewSendCode.setEnabled(true);
                mTextViewSendCode.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            }
            return true;
        }
    });

    //短信倒数线程
    private class InnerThread extends Thread {
        @Override
        public void run() {
            while (mRunning) {
                mSeconds--;
                if (mSeconds <= 0) {
                    mRunning = false;
                    Message.obtain(mHandler, 1).sendToTarget();
                } else {
                    Message.obtain(mHandler, 0).sendToTarget();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //验证短信验证码
    public void verifyCode() {
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.checkSMSCode(getActivity(), mEditTextPhone.getText().toString(), mEditTextAuthCode.getText().toString(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    ((ForgetPasswordActivity) getActivity()).passwordSecond(mEditTextPhone.getText().toString());
                } else {
                    ToastUtils.show(getActivity(), baseBean.getRespDesc());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity(), "请求失败 " + throwable.toString());
            }
        });
    }

    private class InnerTextChangeListener extends CustomTextChangeListener {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(mEditTextPhone.getText().toString()) || TextUtils.isEmpty(mEditTextAuthCode.getText().toString())) {
                mButtonNext.setBackgroundResource(R.color.bg_button_gray_a9a9a9);
                mButtonNext.setEnabled(false);
            } else {
                mButtonNext.setEnabled(true);
                mButtonNext.setBackgroundResource(R.drawable.bg_button_blue_selector);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_send_auth_code:
                if (StringUtils.verifyPhone(mEditTextPhone.getText().toString())) {
                    sendSMS(mEditTextPhone.getText().toString());
                } else {
                    ToastUtils.show(getActivity(), "请输入有效手机号");
                }
                break;
            case R.id.button_next:
                verifyCode();
                break;
        }
    }

    private class InnerTextChangerListener extends CustomTextChangeListener {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(mEditTextPhone.getText().toString()) && !TextUtils.isEmpty(mEditTextAuthCode.getText().toString())) {
                mButtonNext.setEnabled(true);
                mButtonNext.setBackgroundResource(R.drawable.bg_button_login_selector);
            } else {
                mButtonNext.setEnabled(false);
                mButtonNext.setBackgroundColor(getResources().getColor(R.color.bg_button_gray_a9a9a9));
            }
        }
    }

    @Override
    public void onDestroyView() {
        mRunning = false;
        super.onDestroyView();
    }
}
