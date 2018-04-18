package com.yunkahui.datacubeper.upgradeJoin.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.upgradeJoin.logic.PayVipLogic;

import org.json.JSONArray;
import org.json.JSONObject;

public class PayOpenVipActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private LinearLayout mLinearLayoutZFB;
    private TextView mTextViewMoney;
    private SimpleEditTextView mEditTextViewActivateCode;
    private CheckBox mCheckBoxZFB;

    private String mMoney;
    private String mVipId;
    private PayVipLogic mLogic;
    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pay_open_vip);
        super.onCreate(savedInstanceState);
        setTitle("会员升级");
    }

    @Override
    public void initData() {
        mLogic=new PayVipLogic();
        mMoney=getIntent().getStringExtra("money");
        mVipId=getIntent().getStringExtra("vip_id");
        mTextViewMoney.setText(mMoney);
        loadUpdateInfo();
    }

    @Override
    public void initView() {
        mLinearLayoutZFB=findViewById(R.id.linear_layout_zfb);
        mTextViewMoney=findViewById(R.id.tv_money);
        mEditTextViewActivateCode=findViewById(R.id.simple_input_item_activate_code);
        mCheckBoxZFB=findViewById(R.id.check_box_select_zfb);
        findViewById(R.id.button_submit).setOnClickListener(this);
        mCheckBoxZFB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mType="ALIPAY";
                }else{
                    mType="";
                }

            }
        });
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    //获取支付页面信息
    public void loadUpdateInfo(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.updatePayInfo(this, new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("升级信息->"+jsonObject.toString());
                try {
                    JSONObject object=new JSONObject(jsonObject.toString());
                    if(RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                        JSONArray payArray=object.optJSONObject("respData").optJSONArray("payments");
                        for (int i=0;i<payArray.length();i++){
                            if("ALIPAY".equals(payArray.optJSONObject(i).optString("payment_code"))){
                                mLinearLayoutZFB.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
            }
        });
    }

    //获取支付信息orderInfo
    public void loadOrderInfo(){
        if(TextUtils.isEmpty(mType)){
            ToastUtils.show(this,"请选择支付方式");
            return;
        }
        LoadingViewDialog.getInstance().show(this);
        mLogic.payVip(this, mVipId,mType, new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("支付信息->"+jsonObject.toString());
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
            }
        });
    }

    //使用激活码升级
    public void updateVipByActivateCode(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.updateVipByActivateCode(this, mEditTextViewActivateCode.getText(), new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("激活码升级->"+jsonObject.toString());
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("激活码升级失败->"+throwable.toString());
                ToastUtils.show(getApplicationContext(),"升级请求失败");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                if(TextUtils.isEmpty(mEditTextViewActivateCode.getText())){
                    loadOrderInfo();
                }else{
                    updateVipByActivateCode();
                }
                break;
        }
    }
}
