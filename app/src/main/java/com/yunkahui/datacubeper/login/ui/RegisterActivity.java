package com.yunkahui.datacubeper.login.ui;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.login.logic.RegisterLogic;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements IActivityStatusBar {

    private RegisterLogic mLogic;
    private String mPhone;
    private String mNickName;
    private String mPassword;
    private String mInviteCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        setTitle("快速注册");
    }

    @Override
    public void initData() {
        mLogic = new RegisterLogic();
    }

    @Override
    public void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new RegisterFirstFragment()).commit();
    }

    /**
     * 跳转到注册第二步
     */
    public void registerSecond() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_fragment_enter, R.anim.anim_fragment_exit)
                .replace(R.id.frame_layout, new RegisterSecondFragment()).commitNowAllowingStateLoss();
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    public RegisterLogic getLogic() {
        return mLogic;
    }

    public void verifyPhone(final String phone, String code, final String inviteCode) {
        LoadingViewDialog.getInstance().show(this);
        mLogic.checkSMSCode(this, phone, code, inviteCode,new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("注册验证短信->" + baseBean.toString());
                JSONObject object = baseBean.getJsonObject();
                ToastUtils.show(getApplicationContext(), object.optString("respDesc"));
                if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                    setPhone(phone);
                    setInviteCode(inviteCode);
                    registerSecond();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "短信验证失败");
            }
        });

    }

    public void register() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.register(this, mPhone, mNickName, mPassword, mInviteCode, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("注册->" + baseBean.getJsonObject().toString());
                JSONObject object = baseBean.getJsonObject();
                ToastUtils.show(getApplicationContext(), object.optString("respDesc"));
                if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "注册请求失败");

            }
        });
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public void setNickName(String nickName) {
        mNickName = nickName;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public void setInviteCode(String invtiteCode) {
        mInviteCode = invtiteCode;
    }
}
