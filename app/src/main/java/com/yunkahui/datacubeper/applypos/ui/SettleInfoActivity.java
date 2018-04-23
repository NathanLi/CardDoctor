package com.yunkahui.datacubeper.applypos.ui;

import android.content.Intent;
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
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.DialogSub;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.mine.logic.AddCashCardLogic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

//结算信息
public class SettleInfoActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private final int RESULT_CODE=1001;

    private SimpleEditTextView mEditTextViewName;
    private SimpleEditTextView mEditTextViewBankCardNumber;
    private SimpleEditTextView mEditTextViewBankCardName;
    private TextView mTextViewArea;
    private TextView mTextViewBranch;
    private SimpleEditTextView mEditTextViewBranchNumber;

    private DialogSub mDialogArea;
    private String mProvince;
    private String mCity;
    private ApplyPosLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settle_info);
        super.onCreate(savedInstanceState);
        setTitle("结算信息");

    }

    @Override
    public void initData() {
        mLogic=new ApplyPosLogic();
        mDialogArea=new DialogSub(this);
        mEditTextViewName.setText(DataUtils.getInfo().getTruename());
        loadData();
    }

    @Override
    public void initView() {

        mEditTextViewName=findViewById(R.id.simple_input_item_account);
        mEditTextViewBankCardNumber=findViewById(R.id.simple_input_item_bank_card_number);
        mEditTextViewBankCardName=findViewById(R.id.simple_input_item_bank_card_name);
        mTextViewArea=findViewById(R.id.text_view_area);
        mTextViewBranch=findViewById(R.id.text_view_branch);
        mEditTextViewBranchNumber=findViewById(R.id.simple_input_item_branch_number);

        mEditTextViewBranchNumber.setEnabled(false);

        findViewById(R.id.button_submit).setOnClickListener(this);
        mTextViewArea.setOnClickListener(this);
        mTextViewBranch.setOnClickListener(this);
        mEditTextViewBankCardNumber.getEditTextInput().addTextChangedListener(new CustomTextChangeListener(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==10){
                    checkBankCardName();
                }
            }
        });
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    //查询发卡行
    public void checkBankCardName(){
        new AddCashCardLogic().checkBankCardName(this, mEditTextViewBankCardNumber.getText(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                try {
                    LogUtils.e("发卡行->"+baseBean.getJsonObject().toString());
                    JSONObject object=baseBean.getJsonObject();
                    if(RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                        mEditTextViewBankCardName.setText(object.optJSONObject("respData").optString("bankName"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
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

    private void updateData(PosApplyInfo respData) {
        mProvince=respData.getDeposit_province();
        mCity=respData.getDeposit_city();
        mEditTextViewBankCardNumber.setText(respData.getBank_card_num());
        mEditTextViewBankCardName.setText(respData.getBank_card_name());
        mTextViewArea.setText(respData.getDeposit_province()+"-"+respData.getDeposit_city());
        mTextViewBranch.setText(respData.getDeposit_bank());
        mEditTextViewBranchNumber.setText(respData.getCouplet_num());
    }

    private boolean check(){
        if(TextUtils.isEmpty(mEditTextViewBankCardNumber.getText())){
            ToastUtils.show(getApplicationContext(),"请输入银行卡号");
            return false;
        }
        if(TextUtils.isEmpty(mEditTextViewBankCardName.getText())){
            ToastUtils.show(getApplicationContext(),"发卡行信息获取有误");
            return false;
        }
        if(TextUtils.isEmpty(mTextViewArea.getText().toString())){
            ToastUtils.show(getApplicationContext(),"请选择所在地");
            return false;
        }
        if(TextUtils.isEmpty(mTextViewBranch.getText().toString())||TextUtils.isEmpty(mEditTextViewBranchNumber.getText())){
            ToastUtils.show(getApplicationContext(),"请完善开户支行信息");
            return false;
        }
        return true;
    }

    private void upLoadSettleInfo(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.upLoadSettleInfo(this, mEditTextViewBankCardNumber.getText(), mEditTextViewBankCardName.getText(), mProvince, mCity, mTextViewBranch.getText().toString(),
                mEditTextViewBranchNumber.getText(), new SimpleCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        LoadingViewDialog.getInstance().dismiss();
                        LogUtils.e("结算信息提交->"+baseBean.getJsonObject().toString());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK&&requestCode==RESULT_CODE){
            mTextViewBranch.setText(data.getStringExtra("bank_name"));
            mEditTextViewBranchNumber.setText(data.getStringExtra("bank_cnaps"));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                if(check()){
                    upLoadSettleInfo();
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
            case R.id.text_view_branch:

                if(TextUtils.isEmpty(mEditTextViewBankCardNumber.getText())||TextUtils.isEmpty(mEditTextViewBankCardName.getText())||TextUtils.isEmpty(mTextViewArea.getText().toString())){
                    ToastUtils.show(getApplicationContext(),"请先完善信息");
                }else{
                    Intent intent=new Intent(this,BranchInformationActivity.class);
                    intent.putExtra("card_number",mEditTextViewBankCardNumber.getText());
                    intent.putExtra("bank_name",mEditTextViewBankCardName.getText());
                    intent.putExtra("deposit_province",mProvince);
                    intent.putExtra("deposit_city",mCity);
                    startActivityForResult(intent,RESULT_CODE);
                }

                break;
        }
    }
}
