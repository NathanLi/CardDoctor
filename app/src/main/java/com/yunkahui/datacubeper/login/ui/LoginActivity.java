package com.yunkahui.datacubeper.login.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yunkahui.datacubeper.MainActivity;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTextViewForgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mTextViewForgetPassword=findViewById(R.id.text_view_forget_password);

        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.button_register).setOnClickListener(this);
        mTextViewForgetPassword.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_view_forget_password:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.button_login:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.button_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
