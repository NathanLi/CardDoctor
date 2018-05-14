package com.yunkahui.datacubeper.home.ui;

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
import android.widget.TextView;
import android.widget.Toast;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.ui.AutoPlanActivity;
import com.yunkahui.datacubeper.bill.ui.OpenAutoPlanActivity;
import com.yunkahui.datacubeper.common.DispostResultActivity;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.CardSelectorBean;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.home.logic.RechargeLogic;

import java.util.ArrayList;

/**
 * 使用银行卡充值
 */
public class RechargeForCardActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private TextView mTvUserBalance;
    private EditText mEtInputMoney;
    private TextView mTvCardSelected;
    private Button mBtnCommit;

    private RechargeLogic mLogic;
    private ArrayList<CardSelectorBean> mList;
    private CardSelectorBean mSelectorBean;
    private String mBindId;

    @Override
    public void initData() {
        mLogic = new RechargeLogic();
        mList = new ArrayList<>();
        if (getIntent().getStringExtra("money") != null)
            mTvUserBalance.setText(getIntent().getStringExtra("money"));
        LoadingViewDialog.getInstance().show(this);
        queryCreditCardList();
    }

    //******** 查询已添加卡列表 ********
    private void queryCreditCardList() {
        mLogic.queryCreditCardList(this, new SimpleCallBack<BaseBean<BillCreditCard>>() {
            @Override
            public void onSuccess(BaseBean<BillCreditCard> baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("查询卡列表->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mList.clear();
                    CardSelectorBean bean;
                    for (BillCreditCard.CreditCard item : baseBean.getRespData().getCardDetail()) {
                        bean = new CardSelectorBean();
                        bean.setBankCardName(item.getBankCardName());
                        bean.setBankCardNum(item.getBankCardNum());
                        bean.setCardId(item.getUserCreditCardId());
                        bean.setChecked(false);
                        mList.add(bean);
                    }
                    mList.get(0).setChecked(true);
                    mSelectorBean = mList.get(0);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Toast.makeText(RechargeForCardActivity.this, "获取卡信息失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initView() {
        mTvUserBalance = findViewById(R.id.tv_user_balance);
        mTvCardSelected = findViewById(R.id.tv_card_selected);
        mEtInputMoney = findViewById(R.id.et_input_money);
        mBtnCommit = findViewById(R.id.btn_commit);

        findViewById(R.id.ll_show_dialog).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_recharge_for_card);
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
            case R.id.ll_show_dialog:
                showSelectCardDialog();
                break;
            case R.id.btn_commit:
                recharge();
                break;
        }
    }

    //******** 使用银行卡充值 ********
    private void recharge() {
        if (mBindId == null) {
            Toast.makeText(this, "请选择卡号", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(mEtInputMoney.getText().toString())) {
            Toast.makeText(this, "请填写充值金额", Toast.LENGTH_SHORT).show();
            return;
        }
        String money = String.format("%.2f", Float.parseFloat(mEtInputMoney.getText().toString()));
        mLogic.rechargeMoney(this, mBindId, money, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("卡充值->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    Intent intent = new Intent(RechargeForCardActivity.this, DispostResultActivity.class);
                    intent.putExtra("money", mEtInputMoney.getText().toString());
                    intent.putExtra("type", DispostResultActivity.TYPE_TOP_UP);
                    startActivity(intent);
                }
                if ("0209".equals(baseBean.getRespCode())) {
                    showDialog(baseBean.getRespDesc());
                } else {
                    Toast.makeText(RechargeForCardActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(RechargeForCardActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //弹窗是否跳往健全
    private void showDialog(String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mSelectorBean != null) {
                            startActivity(new Intent(RechargeForCardActivity.this, OpenAutoPlanActivity.class)
                                    .putExtra("bank_card_name", mSelectorBean.getBankCardName())
                                    .putExtra("bank_card_num", mSelectorBean.getBankCardNum())
                                    .putExtra("user_credit_card_id", mSelectorBean.getCardId()));
                        }
                    }
                })
                .setNeutralButton("取消", null)
                .create();
        dialog.show();
    }

    private void showSelectCardDialog() {
        CardSelectorDialogFragment dialog = new CardSelectorDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", mList);
        dialog.setArguments(bundle);
        dialog.setOnCheckedChangeListener(new CardSelectorDialogFragment.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(CardSelectorBean bean) {
                mSelectorBean = bean;
                mBindId = String.valueOf(bean.getCardId());
                String cardNum = bean.getBankCardNum();
                mBtnCommit.setEnabled(true);
                mTvCardSelected.setText(bean.getBankCardName() + String.format(getResources().getString(R.string.bank_card_tail_num), cardNum.substring(cardNum.length() - 4, cardNum.length())));
            }
        });
        dialog.show(getSupportFragmentManager(), "DialogFragment");
    }
}
