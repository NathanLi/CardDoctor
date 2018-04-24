package com.yunkahui.datacubeper.applypos.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.applypos.logic.PosManageLogic;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.PosApplyInfo;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleTextView;

import org.json.JSONObject;

//POS管理
public class PosManageActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private final int RESULT_CODE_UPDATE=1001;

    private SimpleTextView mSimpleTextViewBusinessNumber;
    private SimpleTextView mSimpleTextViewTerminalNumber;
    private SimpleTextView mSimpleTextViewApplyName;
    private SimpleTextView mSimpleTextViewIdType;
    private SimpleTextView mSimpleTextViewIdCard;
    private SimpleTextView mSimpleTextViewPhone;
    private SimpleTextView mSimpleTextViewApplyArea;
    private SimpleTextView mSimpleTextViewBusAddress;
    private SimpleTextView mSimpleTextViewAccountName;
    private SimpleTextView mSimpleTextViewBankCardNumber;
    private SimpleTextView mSimpleTextViewBankCardName;
    private SimpleTextView mSimpleTextViewBranchAddress;
    private SimpleTextView mSimpleTextViewBranch;
    private SimpleTextView mSimpleTextViewBranchNumber;

    private PosManageLogic mLogic;
    private PosApplyInfo mApplyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pos_manager);
        super.onCreate(savedInstanceState);
        setTitle("POS管理");
    }

    @Override
    public void initData() {
        mLogic = new PosManageLogic();
        loadData();
    }

    @Override
    public void initView() {
        mSimpleTextViewBusinessNumber = findViewById(R.id.simple_text_view_business_number);
        mSimpleTextViewTerminalNumber = findViewById(R.id.simple_text_view_terminal_number);
        mSimpleTextViewApplyName = findViewById(R.id.simple_text_view_apply_name);
        mSimpleTextViewIdType = findViewById(R.id.simple_text_view_id_type);
        mSimpleTextViewIdCard = findViewById(R.id.simple_text_view_id_card_number);
        mSimpleTextViewPhone = findViewById(R.id.simple_text_view_apply_phone);
        mSimpleTextViewApplyArea = findViewById(R.id.simple_text_view_apply_area);
        mSimpleTextViewBusAddress = findViewById(R.id.simple_text_view_apply_address);
        mSimpleTextViewAccountName = findViewById(R.id.simple_text_view_account_name);
        mSimpleTextViewBankCardNumber = findViewById(R.id.simple_text_view_bank_card_number);
        mSimpleTextViewBankCardName = findViewById(R.id.simple_text_view_bank_card_name);
        mSimpleTextViewBranchAddress = findViewById(R.id.simple_text_view_branch_area);
        mSimpleTextViewBranch = findViewById(R.id.simple_text_view_branch_name);
        mSimpleTextViewBranchNumber = findViewById(R.id.simple_text_view_branch_number);

        mSimpleTextViewBankCardNumber.setOnClickListener(this);
        mSimpleTextViewPhone.setOnClickListener(this);

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }


    private void updateData(PosApplyInfo info){

        mSimpleTextViewBusinessNumber.setText(info.getMerchant_number());
        mSimpleTextViewTerminalNumber.setText(info.getTerminal_number());
        mSimpleTextViewApplyName.setText(info.getLegal_name());
        mSimpleTextViewIdType.setText("身份证");
        mSimpleTextViewIdCard.setText(info.getLegal_identity_num());
        mSimpleTextViewPhone.setText(info.getLegal_phone());
        mSimpleTextViewApplyArea.setText(info.getLegal_province()+"-"+info.getLegal_city());
        mSimpleTextViewBusAddress.setText(info.getManage_address());

        mSimpleTextViewAccountName.setText(info.getUser_name());
        mSimpleTextViewBankCardNumber.setText(info.getBank_card_num());
        mSimpleTextViewBankCardName.setText(info.getBank_card_name());
        mSimpleTextViewBranchAddress.setText(info.getDeposit_province()+"-"+info.getDeposit_city());
        mSimpleTextViewBranch.setText(info.getDeposit_bank());
        mSimpleTextViewBranchNumber.setText(info.getCouplet_num());

    }


    public void loadData() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.loadPosManageData(this, new SimpleCallBack<BaseBean<PosApplyInfo>>() {
            @Override
            public void onSuccess(BaseBean<PosApplyInfo> baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("POS管理->" + baseBean.getJsonObject().toString());
                if(RequestUtils.SUCCESS.equals(baseBean.getRespCode())){
                    mApplyInfo=baseBean.getRespData();
                    updateData(baseBean.getRespData());
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
        if(resultCode==RESULT_OK&&requestCode==RESULT_CODE_UPDATE){
            loadData();
        }
    }

    //检查POS申请状态
    private void checkPosStatus(){
        LoadingViewDialog.getInstance().show(this);
        mLogic.checkPosApplyStatus(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                JSONObject object=baseBean.getJsonObject();
                if(RequestUtils.SUCCESS.equals(object.optString("respCode"))){
                    switch (object.optJSONObject("respData").optString("tua_status")){
                        case "7":  //pos申请完成
                        case "13":  //pos结算信息修改审核通过
                            Intent intent=new Intent(PosManageActivity.this,UpdateSettleActivity.class);
                            intent.putExtra("name",mApplyInfo.getUser_name());
                            startActivity(intent);
                            break;
                        case "12":  //pos结算信息修改审核中
                            break;
                        case "14":  //pos结算信息修改审核不通过
                            break;
                        default:
                            ToastUtils.show(getApplicationContext(),"暂未开放");
                            break;
                    }
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
            case R.id.simple_text_view_apply_phone:
                Intent intent=new Intent(this,UpdateApplyPhoneActivity.class);
                intent.putExtra("phone",mApplyInfo.getLegal_phone());
                startActivityForResult(intent,RESULT_CODE_UPDATE);
                break;
            case R.id.simple_text_view_bank_card_number:
                checkPosStatus();
                break;
        }
    }
}
