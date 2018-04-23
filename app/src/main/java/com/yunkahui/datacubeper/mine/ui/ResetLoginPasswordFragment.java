package com.yunkahui.datacubeper.mine.ui;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.mine.logic.ResetLoginPasswordLogic;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 重置密码
 */
public class ResetLoginPasswordFragment extends Fragment implements View.OnClickListener {

    private SimpleEditTextView mEditTextViewPhone;
    private EditText mEditTextAuthCode;
    private TextView mTextViewSendCode;
    private SimpleEditTextView mEditTextViewOldPassword;
    private SimpleEditTextView mEditTextViewNewPassword;
    private SimpleEditTextView mEditTextViewConfirmNewPassword;
    private Button mButtonReset;

    private int mSecond=60;
    private boolean mRunning;
    private ResetLoginPasswordLogic mLogic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_reset_login_password, container, false);

        mEditTextViewPhone=view.findViewById(R.id.simple_input_item_phone);
        mEditTextAuthCode=view.findViewById(R.id.edit_text_auth_code);
        mTextViewSendCode=view.findViewById(R.id.text_view_send_auth_code);
        mEditTextViewOldPassword=view.findViewById(R.id.simple_input_item_old_password);
        mEditTextViewNewPassword=view.findViewById(R.id.simple_input_item_new_password);
        mEditTextViewConfirmNewPassword=view.findViewById(R.id.simple_input_item_repeat_password);
        mButtonReset=view.findViewById(R.id.button_reset);

        mEditTextViewPhone.setText(DataUtils.getInfo().getUser_mobile());
        mEditTextViewPhone.setEnabled(false);
        mTextViewSendCode.setOnClickListener(this);
        mButtonReset.setOnClickListener(this);
        mEditTextViewPhone.getEditTextInput().addTextChangedListener(new InnerTextChangeListener());
        mEditTextAuthCode.addTextChangedListener(new InnerTextChangeListener());
        mEditTextViewOldPassword.getEditTextInput().addTextChangedListener(new InnerTextChangeListener());
        mEditTextViewNewPassword.getEditTextInput().addTextChangedListener(new InnerTextChangeListener());
        mEditTextViewConfirmNewPassword.getEditTextInput().addTextChangedListener(new InnerTextChangeListener());

        mLogic=new ResetLoginPasswordLogic();

        return view;
    }


    private class InnerTextChangeListener extends CustomTextChangeListener{
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(TextUtils.isEmpty(mEditTextAuthCode.getText().toString())||TextUtils.isEmpty(mEditTextViewOldPassword.getText())
                    ||TextUtils.isEmpty(mEditTextViewNewPassword.getText())||TextUtils.isEmpty(mEditTextViewConfirmNewPassword.getText())){
                mButtonReset.setEnabled(false);
                mButtonReset.setBackgroundColor(getActivity().getResources().getColor(R.color.bg_button_gray_a9a9a9));
            }else{
                mButtonReset.setEnabled(true);
                mButtonReset.setBackgroundResource(R.drawable.bg_button_blue_selector);
            }
        }
    }

    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    mTextViewSendCode.setTextColor(getActivity().getResources().getColor(R.color.text_color_gray_949494));
                    mTextViewSendCode.setText(mSecond+"秒后可重新发送");
                    break;
                case 1:
                    mTextViewSendCode.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                    mTextViewSendCode.setText("发送验证码");
                    mTextViewSendCode.setEnabled(true);
                    break;
            }
            return true;
        }
    });


    private class InnerThread extends Thread{
        @Override
        public void run() {
            while (mRunning){
                    if(mSecond>0){
                        mSecond--;
                        Message.obtain(mHandler,0).sendToTarget();
                    }else{
                        mRunning=false;
                        mSecond=60;
                        Message.obtain(mHandler,1).sendToTarget();
                    }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //发送短信
    private void sendSMS(){
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.sendSMS(getActivity(), DataUtils.getInfo().getUser_mobile(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("发送短信->"+baseBean.getJsonObject().toString());
                try {
                    JSONObject object=baseBean.getJsonObject();
                    ToastUtils.show(getActivity(),object.optString("respDesc"));
                    if(RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                        mTextViewSendCode.setEnabled(false);
                        mRunning=true;
                        new InnerThread().start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity(),"发送短信失败");
            }
        });
    }

    //修改密码
    private void editPassword(){
        if(!mEditTextViewNewPassword.getText().equals(mEditTextViewConfirmNewPassword.getText())){
            ToastUtils.show(getActivity().getApplicationContext(),"两次密码输入不一致");
            return;
        }
        LoadingViewDialog.getInstance().show(getActivity());
        mLogic.editPassword(getActivity(), mEditTextViewPhone.getText(), mEditTextViewOldPassword.getText(), mEditTextViewNewPassword.getText(),
                mEditTextAuthCode.getText().toString(), new SimpleCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        LoadingViewDialog.getInstance().dismiss();
                        LogUtils.e("修改密码->"+baseBean.getJsonObject().toString());
                        try {
                            JSONObject object=baseBean.getJsonObject();
                            ToastUtils.show(getActivity(),object.optString("respDesc"));
                            if(RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                                getActivity().onBackPressed();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        LoadingViewDialog.getInstance().dismiss();
                        ToastUtils.show(getActivity().getApplicationContext(),"请求失败");
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_reset:
                editPassword();
                break;
            case R.id.text_view_send_auth_code:
                sendSMS();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        mRunning=false;
        super.onDestroyView();
    }
}
