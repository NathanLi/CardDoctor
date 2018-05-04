package com.yunkahui.datacubeper.home.ui;

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
import com.yunkahui.datacubeper.common.bean.CardSelectorBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.SizeUtils;
import com.yunkahui.datacubeper.home.logic.WithdrawForCardLogic;

import org.json.JSONObject;

/**
 * Created by YD1 on 2018/4/10
 */
public class WithdrawForCardActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private TextView mTvUserBalance;
    private TextView mTvCardSelected;
    private EditText mEtInputMoney;
    private Button mBtnCommit;

    private WithdrawForCardLogic mLogic;
    private String mBindId;
    private String mWithdrawType;

    @Override
    public void initData() {
        mLogic = new WithdrawForCardLogic();
        mWithdrawType = getIntent().getStringExtra("withdrawType");

        initUserFinance();
    }

    @Override
    public void initView() {
        mTvUserBalance = findViewById(R.id.tv_user_balance);
        mTvCardSelected = findViewById(R.id.tv_card_selected);
        mEtInputMoney = findViewById(R.id.et_input_money);
        mBtnCommit = findViewById(R.id.btn_commit);

        findViewById(R.id.ll_show_dialog).setOnClickListener(this);
        mBtnCommit.setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_withdraw_for_card);
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
            case R.id.ll_show_dialog:
                showSelectCardDialog();
                break;
            case R.id.btn_commit:
                getWithdrawFee();
                break;
        }
    }

    //******** 查询提现手续费 ********
    private void getWithdrawFee() {
        mLogic.queryWithdrawFee(this, Float.parseFloat(mEtInputMoney.getText().toString()), mWithdrawType, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("提现手续费->" + baseBean.getJsonObject().toString());
                Toast.makeText(WithdrawForCardActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    showWithdrawDialog(baseBean.getJsonObject().optJSONObject("respData").optString("fee"));
                } else {
                    Toast.makeText(WithdrawForCardActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(WithdrawForCardActivity.this, "查询提现手续费失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //******** 提现 ********
    private void withdraw(String money) {
        mLogic.withdrawMoney(this, mBindId, money, mWithdrawType, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("提现->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    Intent intent = new Intent(WithdrawForCardActivity.this, DispostResultActivity.class);
                    intent.putExtra("money", mEtInputMoney.getText().toString());
                    intent.putExtra("type", DispostResultActivity.TYPE_WITHDRAW);
                    startActivity(intent);
                } else {
                    Toast.makeText(WithdrawForCardActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(WithdrawForCardActivity.this, "提现失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //******** 获取余额、分润 ********
    private void initUserFinance() {
        mLogic.loadUserFinance(WithdrawForCardActivity.this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("余额分润->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    JSONObject object = baseBean.getJsonObject();
                    JSONObject respData = object.optJSONObject("respData");
                    mTvUserBalance.setText(respData.optString("user_fenruns"));
                } else {
                    Toast.makeText(WithdrawForCardActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(WithdrawForCardActivity.this, "获取余额分润失败->" + throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showWithdrawDialog(String fee) {
        if (mBindId == null) {
            Toast.makeText(this, "请选择卡号", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(mEtInputMoney.getText().toString())) {
            Toast.makeText(this, "请填写提现金额", Toast.LENGTH_SHORT).show();
            return;
        }
        final View codeView = LayoutInflater.from(WithdrawForCardActivity.this).inflate(R.layout.layout_withdraw_dialog, null);
        final Dialog dialog = new Dialog(WithdrawForCardActivity.this, R.style.WithdrawDialog);
        dialog.setContentView(codeView);
        dialog.show();
        final String money = String.format("%.2f", Float.parseFloat(mEtInputMoney.getText().toString()));
        ((TextView) codeView.findViewById(R.id.show_title)).setText(mWithdrawType.equals("00") ? "分佣提现" : mWithdrawType.equals("01") ? "分润提现" : "余额提现");
        ((TextView) codeView.findViewById(R.id.show_money)).setText(money);
        ((TextView) codeView.findViewById(R.id.tv_card_count)).setText("单笔提现手续费：" + fee + "元");
        SizeUtils.setDialogAttribute(WithdrawForCardActivity.this, dialog, 0.90, 0);
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

    private void showSelectCardDialog() {
        CardSelectorDialogFragment dialog = new CardSelectorDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", getIntent().getParcelableArrayListExtra("list"));
        dialog.setArguments(bundle);
        dialog.setOnCheckedChangeListener(new CardSelectorDialogFragment.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(CardSelectorBean bean) {
                mBindId = String.valueOf(bean.getCardId());
                String cardNum = bean.getBankCardNum();
                mBtnCommit.setEnabled(true);
                mTvCardSelected.setText(bean.getBankCardName() + String.format(getResources().getString(R.string.bank_card_tail_num), cardNum.substring(cardNum.length() - 4, cardNum.length())));
            }
        });
        dialog.show(getSupportFragmentManager(), "DialogFragment");
    }

}
