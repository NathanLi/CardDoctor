package com.yunkahui.datacubeper.mine.ui;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.StringUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.mine.logic.BindNewPhoneLogic;

import java.util.Calendar;

public class BindNewPhoneActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private TextView mTextViewOldPhone;
    private SimpleEditTextView mEditTextViewNewPhone;
    private EditText mEditTextAuthCode;
    private TextView mTextViewSendAuthCode;

    private BindNewPhoneLogic mLogic;
    private int mSecond=60;
    private boolean mRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bind_new_phone);
        super.onCreate(savedInstanceState);
        setTitle("绑定新手机");
    }

    @Override
    public void initData() {
        mLogic=new BindNewPhoneLogic();
        mTextViewOldPhone.setText("原手机号码："+DataUtils.getInfo().getUser_mobile());
    }

    @Override
    public void initView() {
        mTextViewOldPhone=findViewById(R.id.text_view_old_phone);
        mEditTextViewNewPhone=findViewById(R.id.simple_input_item_new_phone);
        mEditTextAuthCode=findViewById(R.id.edit_text_auth_code);
        mTextViewSendAuthCode=findViewById(R.id.text_view_send_auth_code);

        mTextViewSendAuthCode.setOnClickListener(this);
        findViewById(R.id.button_submit).setOnClickListener(this);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    mTextViewSendAuthCode.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mTextViewSendAuthCode.setText("发送验证码");
                    mTextViewSendAuthCode.setEnabled(true);
                    break;
                case 1:
                    mTextViewSendAuthCode.setTextColor(getResources().getColor(R.color.text_color_gray_949494));
                    mTextViewSendAuthCode.setText(mSecond+"秒后可重新发送");
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
                    Message.obtain(mHandler,1).sendToTarget();
                }else{
                    mSecond=60;
                    mRunning=false;
                    Message.obtain(mHandler,0).sendToTarget();
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
    public void sendSMS(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.sendSMS(this, mEditTextViewNewPhone.getText(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("发送短信->"+baseBean.getJsonObject().toString());
                ToastUtils.show(getApplicationContext(),baseBean.getRespDesc());
                if(RequestUtils.SUCCESS.equals(baseBean.getRespCode())){
                    mRunning=true;
                    mTextViewSendAuthCode.setEnabled(false);
                    new InnerThread().start();
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(),"请求失败");
            }
        });
    }

    //绑定新手机
    private void bindNewPhone(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.bindNewPhone(this, mEditTextViewNewPhone.getText(), mEditTextAuthCode.getText().toString(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(),baseBean.getRespDesc());
                if(RequestUtils.SUCCESS.equals(baseBean.getRespCode())){
                    setResult(RESULT_OK);
                    finish();
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(),"请求失败 "+throwable.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                if(TextUtils.isEmpty(mEditTextViewNewPhone.getText())||TextUtils.isEmpty(mEditTextAuthCode.getText().toString())){
                    ToastUtils.show(getApplicationContext(),"请完善信息");
                    return;
                }
                bindNewPhone();
                break;
            case R.id.text_view_send_auth_code:
                if(!StringUtils.verifyPhone(mEditTextViewNewPhone.getText())){
                    ToastUtils.show(getApplicationContext(),"请输入有效的手机号码");
                    return;
                }
                sendSMS();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mRunning=false;
        super.onDestroy();
    }
}
