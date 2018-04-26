package com.yunkahui.datacubeper.login.ui;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yunkahui.datacubeper.base.MainActivity;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityBase;
import com.yunkahui.datacubeper.common.api.BaseUrl;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.SharedPreferencesUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.login.logic.LoginLogic;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements IActivityBase, View.OnClickListener {

    private EditText mEditTextPhone;
    private EditText mEditTextPassword;

    private LoginLogic mLogic;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);

        new RxPermissions(this)
                .requestEach(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(new SimpleCallBack<Permission>() {
                    @Override
                    public void onSuccess(Permission permission) {
                        if (permission.granted) {

                        } else {
                            ToastUtils.show(getApplicationContext(), "请允许打开需要的权限");
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void initData() {
        mLogic = new LoginLogic();
        mEditTextPhone.setText(SharedPreferencesUtils.getString(this, SharedPreferencesUtils.USER_NAME));
        mEditTextPassword.setText(SharedPreferencesUtils.getString(this, SharedPreferencesUtils.PASSWORD));
    }

    @Override
    public void initView() {
        mEditTextPhone = findViewById(R.id.edit_text_phone);
        mEditTextPassword = findViewById(R.id.edit_text_password);

        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.button_register).setOnClickListener(this);
        findViewById(R.id.text_view_forget_password).setOnClickListener(this);
    }

    public boolean check() {
        if (TextUtils.isEmpty(mEditTextPhone.getText().toString())) {
            ToastUtils.show(getApplicationContext(), R.string.phone_tips2);
            return false;
        } else if (TextUtils.isEmpty(mEditTextPassword.getText().toString())) {
            ToastUtils.show(getApplicationContext(), R.string.password_tips);
            return false;
        }
        return true;
    }

    public void login() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.login(this, mEditTextPhone.getText().toString(), mEditTextPassword.getText().toString(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                try {
                    LogUtils.e("登陆->" + baseBean.getJsonObject().toString());
                    JSONObject object = baseBean.getJsonObject();
                    ToastUtils.show(getApplicationContext(), object.optString("respDesc"));
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        SharedPreferencesUtils.save(LoginActivity.this, SharedPreferencesUtils.USER_NAME, mEditTextPhone.getText().toString());
                        SharedPreferencesUtils.save(LoginActivity.this, SharedPreferencesUtils.PASSWORD, mEditTextPassword.getText().toString());
                        JSONObject respData = object.optJSONObject("respData");
                        BaseUrl.setUSER_ID(respData.optString("user_code"));
                        BaseUrl.setKEY(respData.optString("key"));
                        DataUtils.getInfo().setIdentify_status(respData.optString("identify_status"));
                        DataUtils.getInfo().setVIP_status("08".equals(respData.optString("user_role")) ? "1" : "0");
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
                ToastUtils.show(getApplicationContext(), "登陆失败");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_forget_password:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.button_login:
                if (check()) {
                    login();
                }
                break;
            case R.id.button_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

}
