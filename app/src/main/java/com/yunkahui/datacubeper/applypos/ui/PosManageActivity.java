package com.yunkahui.datacubeper.applypos.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.applypos.logic.PosManageLogic;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.PosApplyInfo;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleTextView;
import com.yunkahui.datacubeper.home.logic.HomeLogic;
import com.yunkahui.datacubeper.upgradeJoin.ui.UpgradeVipActivity;

import org.json.JSONObject;

//POS管理
public class PosManageActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private final int RESULT_CODE_UPDATE = 1001;

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
    private LinearLayout mLinearLayoutPosData;

    private PosManageLogic mLogic;
    private PosApplyInfo mApplyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pos_manager);
        super.onCreate(savedInstanceState);
        setTitle("POS管理");
    }

    /**
     * 跳转前置
     */
    public static void startAction(Activity activity) {
        checkApplyPosStatus(activity);
    }

    //跳转前查询POS开通状态
    private static void checkApplyPosStatus(final Activity activity) {
        LoadingViewDialog.getInstance().show(activity);
        new HomeLogic().checkPosApplyStatus(activity, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("POS状态->" + baseBean.getJsonObject().toString());
                JSONObject object = baseBean.getJsonObject();
                if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                    JSONObject json = object.optJSONObject("respData");
                    DataUtils.getInfo().setTruename(json.optString("truename"));
//                    if (!"1".equals(json.optString("VIP_status"))) {
//                        ToastUtils.show(activity, "请先升级VIP");
//                    } else {}
                    switch (json.optString("tua_status")) {
                        case "-1":
                            showDialog(activity);
                            break;
                        case "7":   //完成
                        case "10":
                        case "12":
                        case "13":
                        case "14":
                            Intent intent = new Intent(activity, PosManageActivity.class);
                            activity.startActivity(intent);
                            break;
                        default:
                            ToastUtils.show(activity.getApplicationContext(), "请先申请POS终端");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(activity.getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }

    //前往开通会员弹窗
    public static void showDialog(final Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage("请先开通落地POS")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(activity, UpgradeVipActivity.class);
                        activity.startActivity(intent);
                    }
                })
                .setNeutralButton("取消", null)
                .create();
        dialog.show();
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
        mLinearLayoutPosData = findViewById(R.id.linear_layout_pos_data);

        mSimpleTextViewBankCardNumber.setOnClickListener(this);
        mSimpleTextViewPhone.setOnClickListener(this);

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }


    private void updateData(PosApplyInfo info) {

        if (!TextUtils.isEmpty(info.getBank_card_num())) {
            mLinearLayoutPosData.setVisibility(View.VISIBLE);
            mSimpleTextViewApplyName.setText(info.getLegal_name());
            mSimpleTextViewIdType.setText("身份证");
            mSimpleTextViewIdCard.setText(info.getLegal_identity_num());
            mSimpleTextViewPhone.setText(info.getLegal_phone());
            mSimpleTextViewApplyArea.setText(info.getLegal_province() + "-" + info.getLegal_city());
            mSimpleTextViewBusAddress.setText(info.getManage_address());

            mSimpleTextViewAccountName.setText(info.getLegal_name());//TODO POS管理  账户名称待修改
            mSimpleTextViewBankCardNumber.setText(info.getBank_card_num());
            mSimpleTextViewBankCardName.setText(info.getBank_card_name());
            mSimpleTextViewBranchAddress.setText(info.getDeposit_province() + "-" + info.getDeposit_city());
            mSimpleTextViewBranch.setText(info.getDeposit_bank());
            mSimpleTextViewBranchNumber.setText(info.getCouplet_num());
        }

        mSimpleTextViewBusinessNumber.setText(info.getMerchant_number());
        mSimpleTextViewTerminalNumber.setText(info.getTerminal_number());


    }


    public void loadData() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.loadPosManageData(this, new SimpleCallBack<BaseBean<PosApplyInfo>>() {
            @Override
            public void onSuccess(BaseBean<PosApplyInfo> baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("POS管理->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mApplyInfo = baseBean.getRespData();
                    updateData(baseBean.getRespData());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_CODE_UPDATE) {
            loadData();
        }
    }

    //检查POS申请状态
    private void checkPosStatus() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.checkPosApplyStatus(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                JSONObject object = baseBean.getJsonObject();
                if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                    switch (object.optJSONObject("respData").optString("tua_status")) {
                        case "7":  //pos申请完成
                        case "13":  //pos结算信息修改审核通过
                        case "14":  //pos结算信息修改审核不通过 //TODO POS管理  账户名称待修改
                            Intent intent = new Intent(PosManageActivity.this, UpdateSettleActivity.class);
                            intent.putExtra("name", mApplyInfo.getLegal_name());
                            startActivity(intent);
                            break;
                        case "12":  //pos结算信息修改审核中
                            ToastUtils.show(getApplicationContext(), "结算信息变更审核中");
                            break;
                        default:
                            ToastUtils.show(getApplicationContext(), "暂未开放");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simple_text_view_apply_phone:
                Intent intent = new Intent(this, UpdateApplyPhoneActivity.class);
                intent.putExtra("phone", mApplyInfo.getLegal_phone());
                startActivityForResult(intent, RESULT_CODE_UPDATE);
                break;
            case R.id.simple_text_view_bank_card_number:
                checkPosStatus();
                break;
        }
    }
}
