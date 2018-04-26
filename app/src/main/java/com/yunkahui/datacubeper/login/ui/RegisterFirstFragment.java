package com.yunkahui.datacubeper.login.ui;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.StringUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFirstFragment extends Fragment implements View.OnClickListener {

    private EditText mEditTextPhone;
    private EditText mEditTextAuthCode;
    private EditText mEditTextInviteCode;
    private TextView mTextViewSendCode;
    private Button mButtonNext;

    private int mSeconds = 60;
    private boolean mRunning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_first, container, false);
        mEditTextPhone = view.findViewById(R.id.edit_text_phone);
        mEditTextAuthCode = view.findViewById(R.id.edit_text_auth_code);
        mEditTextInviteCode = view.findViewById(R.id.edit_text_invite_code);
        mTextViewSendCode = view.findViewById(R.id.text_view_send_auth_code);
        mButtonNext = view.findViewById(R.id.button_next);

        mEditTextPhone.addTextChangedListener(new InnerTextChangerListener());
        mEditTextAuthCode.addTextChangedListener(new InnerTextChangerListener());
        mEditTextInviteCode.addTextChangedListener(new InnerTextChangerListener());

        mTextViewSendCode.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mButtonNext.setEnabled(false);
        return view;
    }

    //短信倒数handler
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_send_auth_code:
                if (TextUtils.isEmpty(mEditTextPhone.getText().toString())) {
                    ToastUtils.show(getActivity(), R.string.phone_tips2);
                    return;
                } else if (!StringUtils.verifyPhone(mEditTextPhone.getText().toString())) {
                    ToastUtils.show(getActivity(), R.string.phone_tips3);
                    return;
                }
                sendSMS(mEditTextPhone.getText().toString());
                break;
            case R.id.button_next:
                if (!StringUtils.verifyPhone(mEditTextPhone.getText().toString())) {
                    ToastUtils.show(getActivity(), R.string.phone_tips3);
                    return;
                }
                ((RegisterActivity) getActivity()).verifyPhone(mEditTextPhone.getText().toString(), mEditTextAuthCode.getText().toString(), mEditTextInviteCode.getText().toString());
                break;
        }

    }

    /**
     * 发送验证码
     */
    public void sendSMS(String phone) {
        LoadingViewDialog.getInstance().show(getActivity());
        ((RegisterActivity) getActivity()).getLogic().sendSMS(getActivity(), phone, new SimpleCallBack<BaseBean>() {
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


    private class InnerTextChangerListener extends CustomTextChangeListener {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!TextUtils.isEmpty(mEditTextPhone.getText().toString()) && !TextUtils.isEmpty(mEditTextAuthCode.getText().toString())
                    && !TextUtils.isEmpty(mEditTextInviteCode.getText().toString())) {
                mButtonNext.setEnabled(true);
                mButtonNext.setBackgroundResource(R.drawable.bg_button_login_selector);
            } else {
                mButtonNext.setEnabled(false);
                mButtonNext.setBackgroundColor(Color.parseColor("#CCCCCC"));
            }

        }
    }

    @Override
    public void onDestroyView() {
        mRunning = false;
        super.onDestroyView();
    }
}
