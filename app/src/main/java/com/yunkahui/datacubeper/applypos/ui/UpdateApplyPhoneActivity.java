package com.yunkahui.datacubeper.applypos.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;

//更变申请人手机号码
public class UpdateApplyPhoneActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private TextView mTextViewOldPhone;
    private SimpleEditTextView mEditTextViewNewPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_apply_phone);
        super.onCreate(savedInstanceState);
        setTitle("更变申请人手机号码");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mTextViewOldPhone=findViewById(R.id.text_view_old_phone);
        mEditTextViewNewPhone=findViewById(R.id.simple_input_item_new_phone);
        findViewById(R.id.button_submit).setOnClickListener(this);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                break;
        }
    }
}
