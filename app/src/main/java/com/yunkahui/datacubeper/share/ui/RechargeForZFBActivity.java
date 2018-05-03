package com.yunkahui.datacubeper.share.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.DispostResultActivity;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.CardSelectorBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.home.logic.RechargeLogic;
import com.yunkahui.datacubeper.mine.ui.BindZFBActivity;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by YD1 on 2018/4/10
 */
public class RechargeForZFBActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private ImageView mIvWithdrawMode;
    private TextView mTvUserBalance;
    private EditText mEtInputMoney;
    private TextView mTvCardSelected;
    private Button mBtnCommit;

    private RechargeLogic mLogic;
    private ArrayList<CardSelectorBean> mList;
    private String mAlipayId;

    @Override
    public void initData() {
        mLogic = new RechargeLogic();
        mList = new ArrayList<>();
        if (getIntent().getStringExtra("money") != null)
            mTvUserBalance.setText(getIntent().getStringExtra("money"));
        LoadingViewDialog.getInstance().show(this);
        checkUserZFB();
    }

    //******** 查询支付宝信息 ********
    private void checkUserZFB() {
        mIvWithdrawMode.setBackgroundResource(R.mipmap.ic_zfb_blue);
        mLogic.checkUserZFB(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                mBtnCommit.setEnabled(true);
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("支付宝信息->" + baseBean.getJsonObject().toString());
                JSONObject object = baseBean.getJsonObject();
                if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                    JSONObject respData = object.optJSONObject("respData");
                    mAlipayId = String.valueOf(respData.optInt("alipay_id"));
                    String mAlipayAccount = respData.optString("alipay_account");
                    String name = respData.optString("ail_true_name");
                    mTvCardSelected.setText(mAlipayAccount + "(" + name + ")");
                } else {
                    showBindZFBDialog();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Toast.makeText(RechargeForZFBActivity.this, "获取支付宝信息失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBindZFBDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("尚未绑定支付宝，请前往绑定")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(RechargeForZFBActivity.this, BindZFBActivity.class));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void initView() {
        mTvUserBalance = findViewById(R.id.tv_user_balance);
        mTvCardSelected = findViewById(R.id.tv_card_selected);
        mEtInputMoney = findViewById(R.id.et_input_money);
        mIvWithdrawMode = findViewById(R.id.iv_withdraw_mode);
        mBtnCommit = findViewById(R.id.btn_commit);

        findViewById(R.id.btn_commit).setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_recharge_for_zfb);
        super.onCreate(savedInstanceState);
        setTitle("余额充值");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                recharge();
                break;
        }
    }

    //******** 使用支付宝充值 ********
    private void recharge() {
        if (TextUtils.isEmpty(mEtInputMoney.getText().toString())) {
            Toast.makeText(this, "请填写充值金额", Toast.LENGTH_SHORT).show();
            return;
        }
        String money = String.format("%.2f", Float.parseFloat(mEtInputMoney.getText().toString()));
        mLogic.rechargeMoney(this, mAlipayId, money, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("支付宝充值->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    Intent intent = new Intent(RechargeForZFBActivity.this, DispostResultActivity.class);
                    intent.putExtra("money", mEtInputMoney.getText().toString());
                    intent.putExtra("type", DispostResultActivity.TYPE_TOP_UP);
                    startActivity(intent);
                } else {
                    Toast.makeText(RechargeForZFBActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(RechargeForZFBActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
