package com.yunkahui.datacubeper.applypos.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.applypos.logic.ApplyPosLogic;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.PosApplyInfo;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;

import org.json.JSONException;
import org.json.JSONObject;

//填写POS邮寄信息
public class PosMailInfoActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private SimpleEditTextView mEditTextViewName;
    private SimpleEditTextView mEditTextViewPhone;
    private SimpleEditTextView mEditTextViewAddress;


    private ApplyPosLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pos_mail_info);
        super.onCreate(savedInstanceState);
        setTitle("POS机器邮寄信息");

    }

    @Override
    public void initData() {
        mLogic=new ApplyPosLogic();
        loadData();
    }

    @Override
    public void initView() {
        mEditTextViewName=findViewById(R.id.simple_input_item_name);
        mEditTextViewPhone=findViewById(R.id.simple_input_item_phone);
        mEditTextViewAddress=findViewById(R.id.simple_input_item_address);
        findViewById(R.id.button_submit).setOnClickListener(this);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }


    private void submit(){
        if(TextUtils.isEmpty(mEditTextViewName.getText())||TextUtils.isEmpty(mEditTextViewPhone.getText())||TextUtils.isEmpty(mEditTextViewAddress.getText())){
            ToastUtils.show(getApplicationContext(),"请完善信息");
            return;
        }
        LoadingViewDialog.getInstance().show(this);
        mLogic.upLoadMailInfo(this, mEditTextViewName.getText(), mEditTextViewPhone.getText(), mEditTextViewAddress.getText(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                try {
                    JSONObject object=baseBean.getJsonObject();
                    ToastUtils.show(getApplicationContext(),object.optString("respDesc"));
                    if(RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                        setResult(RESULT_OK);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(),"请求失败 "+throwable.toString());
            }
        });

    }

    private void loadData(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.checkPosApplyUploadData(this, new SimpleCallBack<BaseBean<PosApplyInfo>>() {
            @Override
            public void onSuccess(BaseBean<PosApplyInfo> bean) {
                LoadingViewDialog.getInstance().dismiss();
                if(RequestUtils.SUCCESS.equals(bean.getRespCode())){
                    updateData(bean.getRespData());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(),"请求失败 "+throwable.toString());
            }
        });
    }

    private void updateData(PosApplyInfo info){
        mEditTextViewName.setText(info.getReceive_name());
        mEditTextViewPhone.setText(info.getReceive_phone());
        mEditTextViewAddress.setText(info.getReceive_address());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                submit();
                break;
        }
    }
}
