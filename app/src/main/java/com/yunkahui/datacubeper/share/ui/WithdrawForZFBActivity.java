package com.yunkahui.datacubeper.share.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.incrementadapter.NoDoubleClickListener;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.DispostResultActivity;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.SizeUtils;
import com.yunkahui.datacubeper.share.logic.WithdrawForZFBLogic;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YD1 on 2018/4/10
 */
public class WithdrawForZFBActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private TextView mTvUserBalance;
    private TextView mTvCardSelected;
    private EditText mEtInputMoney;

    private WithdrawForZFBLogic mLogic;
    private String mWithdrawType;
    private String mAlipayId;

    @Override
    public void initData() {
        mLogic = new WithdrawForZFBLogic();
        mWithdrawType = getIntent().getStringExtra("withdrawType");
        requestSharePageInfo();
        try {
            JSONObject json = new JSONObject(getIntent().getStringExtra("json"));
            JSONObject respData = json.optJSONObject("respData");
            mAlipayId = String.valueOf(respData.optInt("alipay_id"));
            String alipayAccount = respData.optString("alipay_account");
            String name = respData.optString("ail_true_name");
            mTvCardSelected.setText(alipayAccount + "(" + name + ")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //******** 获取分享页面数据 ********
    private void requestSharePageInfo() {
        mLogic.requestSharePageInfo(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("分润->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    JSONObject object = baseBean.getJsonObject();
                    JSONObject respData = object.optJSONObject("respData");
                    mTvUserBalance.setText(respData.optString("00".equals(mWithdrawType) ? "userCommissions" : "userFenruns"));
                } else {
                    Toast.makeText(WithdrawForZFBActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(WithdrawForZFBActivity.this, "获取分润金额失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initView() {
        mTvUserBalance = findViewById(R.id.tv_user_balance);
        mTvCardSelected = findViewById(R.id.tv_card_selected);
        mEtInputMoney = findViewById(R.id.et_input_money);

        findViewById(R.id.btn_commit).setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_withdraw_for_zfb);
        super.onCreate(savedInstanceState);
        setTitle("余额提现");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                getWithdrawFee();
                break;
        }
    }

    private void getWithdrawFee() {
        mLogic.queryWithdrawFee(this, Float.parseFloat(mEtInputMoney.getText().toString()), mWithdrawType, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("提现手续费->" + baseBean.getJsonObject().toString());
                Toast.makeText(WithdrawForZFBActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    showWithdrawDialog(baseBean.getJsonObject().optJSONObject("respData").optString("fee"));
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(WithdrawForZFBActivity.this, "查询提现手续费失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showWithdrawDialog(String fee) {
        if (TextUtils.isEmpty(mEtInputMoney.getText().toString())) {
            Toast.makeText(this, "请填写提现金额", Toast.LENGTH_SHORT).show();
            return;
        }
        final View codeView = LayoutInflater.from(WithdrawForZFBActivity.this).inflate(R.layout.layout_withdraw_dialog, null);
        final Dialog dialog = new Dialog(WithdrawForZFBActivity.this, R.style.WithdrawDialog);
        dialog.setContentView(codeView);
        dialog.show();
        final String money = String.format("%.2f", Float.parseFloat(mEtInputMoney.getText().toString()));
        ((TextView) codeView.findViewById(R.id.show_title)).setText(mWithdrawType.equals("00") ? "分佣提现" : mWithdrawType.equals("01") ? "分润提现" : "余额提现");
        ((TextView) codeView.findViewById(R.id.show_money)).setText(money);
        ((TextView) codeView.findViewById(R.id.tv_card_count)).setText("单笔提现手续费：" + fee + "元");
        SizeUtils.setDialogAttribute(WithdrawForZFBActivity.this, dialog, 0.90, 0);
        codeView.findViewById(R.id.show_sure).setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                dialog.dismiss();
                withdraw(money);
            }
        });
        codeView.findViewById(R.id.show_cancel).setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //******** 支付宝提现 ********
    private void withdraw(String money) {
        LogUtils.e("test->" + mAlipayId + ", " + money + ", " + mWithdrawType);
        mLogic.withdrawMoney(this, mAlipayId, money, mWithdrawType, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("提现->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    Intent intent = new Intent(WithdrawForZFBActivity.this, DispostResultActivity.class);
                    intent.putExtra("money", mEtInputMoney.getText().toString());
                    intent.putExtra("type", DispostResultActivity.TYPE_WITHDRAW);
                    startActivity(intent);
                } else {
                    Toast.makeText(WithdrawForZFBActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(WithdrawForZFBActivity.this, "提现失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
