package com.yunkahui.datacubeper.home.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
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
public class RechargeActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private ImageView mIvWithdrawMode;
    private TextView mTvUserBalance;
    private EditText mEtInputMoney;
    private TextView mTvCardSelected;

    private RechargeLogic mLogic;
    private ArrayList<CardSelectorBean> mList;
    private String mCurrentCardNum;

    @Override
    public void initData() {
        mLogic = new RechargeLogic();
        mList = new ArrayList<>();
        if (getIntent().getStringExtra("money") != null)
            mTvUserBalance.setText(getIntent().getStringExtra("money"));
        LoadingViewDialog.getInstance().show(this);
        String fromPage = getIntent().getStringExtra("from");
        if ("share".equals(fromPage)) {
            checkUserZFB();
        } else {
            queryCreditCardList();
        }
    }

    //******** 查询支付宝信息 ********
    private void checkUserZFB() {
        mIvWithdrawMode.setBackgroundResource(R.mipmap.ic_zfb_blue);
        mLogic.checkUserZFB(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("支付宝信息->" + baseBean.getJsonObject().toString());
                JSONObject object = baseBean.getJsonObject();
                if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                    String account = object.optJSONObject("respData").optString("alipay_account");
                    String name = object.optJSONObject("respData").optString("ail_true_name");
                    mTvCardSelected.setText(account + "(" + name + ")");
                } else {
                    showBindZFBDialog();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Toast.makeText(RechargeActivity.this, "获取支付宝信息失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //******** 查询已添加卡列表 ********
    private void queryCreditCardList() {
        mLogic.queryCreditCardList(this, new SimpleCallBack<BaseBean<BillCreditCard>>() {
            @Override
            public void onSuccess(BaseBean<BillCreditCard> baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("查询卡列表->" + baseBean.toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mList.clear();
                    CardSelectorBean bean;
                    for (BillCreditCard.CreditCard item : baseBean.getRespData().getCardDetail()) {
                        bean = new CardSelectorBean();
                        bean.setBankCardName(item.getBankCardName());
                        bean.setBankCardNum(item.getBankCardNum());
                        bean.setChecked(false);
                        mList.add(bean);
                    }
                    mList.get(0).setChecked(true);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Toast.makeText(RechargeActivity.this, "获取卡信息失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBindZFBDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("尚未绑定支付宝，请前往绑定")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(RechargeActivity.this, BindZFBActivity.class));
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

        findViewById(R.id.ll_show_dialog).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_recharge);
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

    //******** 充值 ********
    private void recharge() {
        if (mCurrentCardNum == null) {
            Toast.makeText(this, "银行卡号为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mLogic.rechargeMoney(this, mCurrentCardNum, mEtInputMoney.getText().toString(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("充值->" + baseBean.toString());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(RechargeActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSelectCardDialog() {
        CardSelectorDialogFragment dialog = new CardSelectorDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", mList);
        dialog.setArguments(bundle);
        dialog.setOnCheckedChangeListener(new CardSelectorDialogFragment.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(String bankName, String num) {
                mCurrentCardNum = num;
                mTvCardSelected.setText(bankName + String.format(getResources().getString(R.string.bank_card_tail_num), num.substring(num.length() - 4, num.length())));
            }
        });
        dialog.show(getSupportFragmentManager(), "DialogFragment");
    }
}
