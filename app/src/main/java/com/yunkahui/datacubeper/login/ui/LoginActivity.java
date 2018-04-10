package com.yunkahui.datacubeper.login.ui;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yunkahui.datacubeper.base.CardDoctorApplication;
import com.yunkahui.datacubeper.base.MainActivity;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityBase;
import com.yunkahui.datacubeper.common.api.BaseUrl;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.login.logic.LoginLogic;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements IActivityBase, View.OnClickListener {

    private TextView mTextViewForgetPassword;
    private EditText mEditTextPhone;
    private EditText mEditTextPassword;
    private LoginLogic mLogic;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);

//        RxPermissions rxPermissions=new RxPermissions(this);
//        rxPermissions
//                .requestEach(Manifest.permission.CAMERA,
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_PHONE_STATE)
//                .subscribe(new SimpleCallBack<Permission>() {
//                    @Override
//                    public void onSuccess(Permission permission) {
//                        if(permission.granted){
//
//                        }else{
//                            ToastUtils.show(getApplicationContext(),"请允许打开需要的权限");
//                            finish();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Throwable throwable) {
//
//                    }
//                });

    }

    @Override
    public void initData() {
        mLogic=new LoginLogic();
    }

    @Override
    public void initView() {
        mEditTextPhone=findViewById(R.id.edit_text_phone);
        mEditTextPassword=findViewById(R.id.edit_text_password);

        mTextViewForgetPassword=findViewById(R.id.text_view_forget_password);

        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.button_register).setOnClickListener(this);
        mTextViewForgetPassword.setOnClickListener(this);
    }

    public boolean check(){
        if(TextUtils.isEmpty(mEditTextPhone.getText().toString())){
            ToastUtils.show(getApplicationContext(),R.string.phone_tips2);
            return false;
        }
        if(TextUtils.isEmpty(mEditTextPassword.getText().toString())){
            ToastUtils.show(getApplicationContext(),R.string.password_tips);
            return false;
        }
        return true;
    }

    public void login(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.login(this, mEditTextPhone.getText().toString(), mEditTextPassword.getText().toString(), new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                LoadingViewDialog.getInstance().dismiss();
                try {
                    LogUtils.e("登陆->"+jsonObject.toString());
                    JSONObject object=new JSONObject(jsonObject.toString());
                    ToastUtils.show(getApplicationContext(),object.optString("respDesc"));
                    if(RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                       BaseUrl.setUSER_ID(object.optJSONObject("respData").optString("user_code"));
                       BaseUrl.setKEY(object.optJSONObject("respData").optString("key"));
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(),"登陆失败");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_view_forget_password:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.button_login:
                if(check()){
                    login();
                }
                break;
            case R.id.button_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

}
