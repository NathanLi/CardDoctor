package com.yunkahui.datacubeper.applypos.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.applypos.logic.PosManageLogic;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;

//更变申请人手机号码
public class UpdateApplyPhoneActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private TextView mTextViewOldPhone;
    private SimpleEditTextView mEditTextViewNewPhone;

    private String mOldPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_apply_phone);
        super.onCreate(savedInstanceState);
        setTitle("更变申请人手机号码");
    }

    @Override
    public void initData() {
        mOldPhone=getIntent().getStringExtra("phone");
        mTextViewOldPhone.setText("原手机号码："+mOldPhone);
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


    private void submit(){
        LoadingViewDialog.getInstance().show(this);
        new PosManageLogic().updatePosApplyPhone(this, mEditTextViewNewPhone.getText(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(),baseBean.getRespDesc());
                if(RequestUtils.SUCCESS.equals(baseBean.getRespCode())){
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
                if(TextUtils.isEmpty(mEditTextViewNewPhone.getText())){
                    ToastUtils.show(getApplicationContext(),"手机号不能为空");
                    return;
                }
                submit();
                break;
        }
    }
}
