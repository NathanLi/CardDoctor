package com.yunkahui.datacubeper.applypos.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.applypos.logic.ApplyPosLogic;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.PosApplyInfo;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.DialogSub;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;

import org.json.JSONException;
import org.json.JSONObject;

//终端信息
public class TerminalInfoActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private SimpleEditTextView mEditTextViewName;
    private SimpleEditTextView mEditTextViewIdCard;
    private SimpleEditTextView mEditTextViewPhone;
    private SimpleEditTextView mEditTextViewAddress;

    private TextView mTextViewArea;
    private DialogSub mDialogArea;
    private String mProvince;
    private String mCity;

    private ApplyPosLogic mLogic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_terminal_info);
        super.onCreate(savedInstanceState);
        setTitle("终端信息");
    }

    @Override
    public void initData() {
        mLogic=new ApplyPosLogic();
        mDialogArea= new DialogSub(this);
        loadData();
    }

    @Override
    public void initView() {

        mEditTextViewName=findViewById(R.id.simple_input_item_name);
        mEditTextViewIdCard =findViewById(R.id.simple_input_item_id_card);
        mEditTextViewPhone=findViewById(R.id.simple_input_item_phone);
        mEditTextViewAddress=findViewById(R.id.simple_input_item_address);
        mTextViewArea=findViewById(R.id.text_view_area);

        findViewById(R.id.button_submit).setOnClickListener(this);
        mTextViewArea.setOnClickListener(this);

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
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
        mEditTextViewName.setText(info.getLegal_name());
        mEditTextViewIdCard.setText(info.getLegal_identity_num());
        mEditTextViewPhone.setText(info.getLegal_phone());
        mTextViewArea.setText(info.getLegal_province()+" "+info.getLegal_city());
        mEditTextViewAddress.setText(info.getManage_address());
    }

    public boolean check(){
        if(TextUtils.isEmpty(mEditTextViewName.getText())){
            ToastUtils.show(getApplicationContext(),"请输入申请人名称");
            return false;
        }
        if(TextUtils.isEmpty(mEditTextViewIdCard.getText())){
            ToastUtils.show(getApplicationContext(),"请输入申请人身份证号码");
            return false;
        }
        if(TextUtils.isEmpty(mEditTextViewPhone.getText())){
            ToastUtils.show(getApplicationContext(),"请输入申请人手机号");
            return false;
        }
        if(TextUtils.isEmpty(mTextViewArea.getText().toString())){
            ToastUtils.show(getApplicationContext(),"请选择商户所在地");
            return false;
        }
        if(TextUtils.isEmpty(mEditTextViewAddress.getText())){
            ToastUtils.show(getApplicationContext(),"请输入详细地址");
            return false;
        }
        return true;
    }

    private void submit(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.upLoadTerminalInfo(this, mEditTextViewName.getText(), mEditTextViewIdCard.getText(), mEditTextViewPhone.getText(),
                mProvince + "-" + mCity, mEditTextViewAddress.getText(), new SimpleCallBack<BaseBean>() {
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                if(check()){
                    submit();
                }
                break;
            case R.id.text_view_area:
                mDialogArea.showLocalCityPicker(new DialogSub.CityPickerListener() {
                    @Override public void picker(String province, String city) {
                        mProvince = province;
                        mCity = city;
                        mTextViewArea.setText(mProvince+" "+mCity);
                    }
                });
                break;
        }
    }
}
