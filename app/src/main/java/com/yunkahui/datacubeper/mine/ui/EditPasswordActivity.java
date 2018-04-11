package com.yunkahui.datacubeper.mine.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

public class EditPasswordActivity extends AppCompatActivity implements IActivityStatusBar{

    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_password);
        super.onCreate(savedInstanceState);
        mTitle="密码安全";
        setTitle(mTitle);
    }

    @Override
    public void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new EditPasswordFragment()).commit();
    }

    @Override
    public void initData() {

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    /**
     * 跳转到重置密码
     */
    public void startToResetPassword(){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_fragment_enter,R.anim.anim_fragment_exit)
                .replace(R.id.frame_layout,new ResetLoginPasswordFragment())
                .addToBackStack("ResetLoginPasswordFragment").commit();
    }


}
