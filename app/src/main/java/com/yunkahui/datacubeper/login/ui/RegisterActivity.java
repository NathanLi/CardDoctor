package com.yunkahui.datacubeper.login.ui;

import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseActivity;
import com.yunkahui.datacubeper.base.IActivityBase;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.login.logic.RegisterLogic;

public class RegisterActivity extends AppCompatActivity implements IActivityStatusBar {

    private String mPhone;
    private String mNickName;
    private String mPassword;
    private String mInvtiteCode;

    private RegisterLogic mLogic;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {
        mLogic=new RegisterLogic();
    }

    @Override
    public void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new RegisterFirstFragment()).commit();
    }

    /**
     * 跳转到注册第二步
     */
    public void registerSecond(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new RegisterSecondFragment()).commitNowAllowingStateLoss();
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }



    public RegisterLogic getLogic(){
        return mLogic;
    }


    public void verifyPhone(String phone,String code){
        LoadingViewDialog.getInstance().show(this);
        mLogic.checkSMSCode(phone, code, new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("验证短信->"+jsonObject.toString());
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(),"请求失败");
            }
        });

    }










}
