package com.yunkahui.datacubeper.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yunkahui.datacubeper.GlideApp;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.ImageCompress;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.StringUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.mine.logic.RealNameAuthLogic;
import com.yunkahui.datacubeper.mine.logic.RealNameHandAuthLogic;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

//手动实名认证
public class RealNameHandAuthActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private final int RESULT_CODE_IMAGE_FRONT = 1001;
    private final int RESULT_CODE_IMAGE_BACK = 1002;

    private SimpleEditTextView mEditTextViewRealName;
    private SimpleEditTextView mEditTextViewIdCard;
    private SimpleEditTextView mEditTextViewPhone;
    private EditText mEditTextAuthCode;
    private TextView mTextViewSendCode;
    private ImageView mImageViewIdCardFront;
    private ImageView mImageViewIdCardBack;

    private String mFront;
    private String mBack;
    private int mSeconds = 60;
    private boolean mRunning;
    private RealNameHandAuthLogic mLogic;
    private final SmsHandler mSmsHandler = new SmsHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_real_name_hand_auth);
        super.onCreate(savedInstanceState);
        setTitle("实名认证");
    }

    @Override
    public void initData() {
        mLogic = new RealNameHandAuthLogic();

    }

    @Override
    public void initView() {

        mEditTextViewRealName = findViewById(R.id.simple_input_item_real_name);
        mEditTextViewIdCard = findViewById(R.id.simple_input_item_id_card);
        mEditTextViewPhone = findViewById(R.id.simple_input_item_phone);
        mEditTextAuthCode = findViewById(R.id.edit_text_auth_code);
        mTextViewSendCode = findViewById(R.id.text_view_send_auth_code);
        mImageViewIdCardFront = findViewById(R.id.image_view_id_card_front);
        mImageViewIdCardBack = findViewById(R.id.image_view_id_card_back);

        findViewById(R.id.button_submit).setOnClickListener(this);
        mTextViewSendCode.setOnClickListener(this);
        mImageViewIdCardFront.setOnClickListener(this);
        mImageViewIdCardBack.setOnClickListener(this);

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    //发送验证码按钮倒计时
    public void countDown() {
        mTextViewSendCode.setText(mSeconds + "秒后可重新发送");
        mTextViewSendCode.setTextColor(getResources().getColor(R.color.text_color_gray_949494));
    }

    //还原发送验证码按钮
    public void sendCodeReset() {
        mTextViewSendCode.setText(getResources().getString(R.string.send_auth_code));
        mTextViewSendCode.setTextColor(getResources().getColor(R.color.colorPrimary));
        mTextViewSendCode.setEnabled(true);
    }

    //发送短信
    private void sendSMS() {
        if (!StringUtils.verifyPhone(mEditTextViewPhone.getText())) {
            ToastUtils.show(getApplicationContext(), "请输入有效手机号");
            return;
        }
        LoadingViewDialog.getInstance().show(this);
        mLogic.sendSMS(this, mEditTextViewPhone.getText(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mRunning = true;
                    mTextViewSendCode.setEnabled(false);
                    new InnerThread().start();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "发送失败 " + throwable.toString());
            }
        });
    }

    //短信倒计时handler
    private static class SmsHandler extends Handler {

        private final WeakReference<RealNameHandAuthActivity> mActivity;

        private SmsHandler(RealNameHandAuthActivity activity) {
            this.mActivity = new WeakReference<RealNameHandAuthActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RealNameHandAuthActivity authActivity = mActivity.get();
            if (authActivity != null) {
                switch (msg.what) {
                    case 0:
                        authActivity.countDown();
                        break;
                    case 1:
                        authActivity.sendCodeReset();
                        break;
                }
            }
        }
    }


    class InnerThread extends Thread {
        @Override
        public void run() {
            while (mRunning) {
                mSeconds--;
                if (mSeconds <= 0) {
                    mRunning = false;
                    mSeconds = 60;
                    Message.obtain(mSmsHandler, 1).sendToTarget();
                } else {
                    Message.obtain(mSmsHandler, 0).sendToTarget();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //上传身份证图片
    private void submitRealNameAuthImage() {
        LoadingViewDialog.getInstance().show(this);
        new RealNameAuthLogic().submitRealNameAuthImage(this, mFront, mBack, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    submitRealNameHandAuthInfo();
                } else {
                    LoadingViewDialog.getInstance().dismiss();
                    ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }

    //手动实名认证文字信息上传
    private void submitRealNameHandAuthInfo() {
        mLogic.submitRealNameHandAuthInfo(this, mEditTextViewRealName.getText(), mEditTextViewIdCard.getText(), mEditTextViewPhone.getText(), mEditTextAuthCode.getText().toString(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    setResult(RESULT_OK);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && (requestCode == RESULT_CODE_IMAGE_FRONT || requestCode == RESULT_CODE_IMAGE_BACK)) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                switch (requestCode) {
                    case RESULT_CODE_IMAGE_FRONT:
                        GlideApp.with(this).load(images.get(0).path).into(mImageViewIdCardFront);
                        ImageCompress.compress(images.get(0).path, new ImageCompress.onCompressListener() {
                            @Override
                            public void onFinish(String path) {
                                mFront = path;
                            }
                        });
                        break;
                    case RESULT_CODE_IMAGE_BACK:
                        GlideApp.with(this).load(images.get(0).path).into(mImageViewIdCardBack);
                        ImageCompress.compress(images.get(0).path, new ImageCompress.onCompressListener() {
                            @Override
                            public void onFinish(String path) {
                                mBack = path;
                            }
                        });
                        break;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_send_auth_code:
                sendSMS();
                break;
            case R.id.image_view_id_card_front:
                ImagePicker.getInstance().setSelectLimit(1);
                startActivityForResult(new Intent(this, ImageGridActivity.class), RESULT_CODE_IMAGE_FRONT);
                break;
            case R.id.image_view_id_card_back:
                ImagePicker.getInstance().setSelectLimit(1);
                startActivityForResult(new Intent(this, ImageGridActivity.class), RESULT_CODE_IMAGE_BACK);
                break;
            case R.id.button_submit:
                if (TextUtils.isEmpty(mEditTextViewRealName.getText()) || TextUtils.isEmpty(mEditTextViewIdCard.getText())
                        || TextUtils.isEmpty(mEditTextViewPhone.getText()) || TextUtils.isEmpty(mEditTextAuthCode.getText().toString())
                        || TextUtils.isEmpty(mFront) || TextUtils.isEmpty(mBack)) {
                    ToastUtils.show(getApplicationContext(), "请完善信息");
                    return;
                }
                submitRealNameAuthImage();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mRunning = false;
        super.onDestroy();
    }
}
