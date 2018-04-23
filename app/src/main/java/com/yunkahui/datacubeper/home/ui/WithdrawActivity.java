package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.CardSelectorBean;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.home.logic.RechargeLogic;
import com.yunkahui.datacubeper.home.logic.WithdrawLogic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by YD1 on 2018/4/10
 */
public class WithdrawActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private static final String TAG = "WithdrawActivity";
    private WithdrawLogic mLogic;
    private ArrayList<CardSelectorBean> mList;
    private TextView mTvUserBalance;
    private LinearLayout mLlShowDialog;
    private TextView mTvCardSelected;
    private EditText mEtInputMoney;
    private String mCurrentCardNum;

    @Override
    public void initData() {
        mLogic = new WithdrawLogic();
        mList = new ArrayList<>();
        if (getIntent().getStringExtra("money") != null) {
            mTvUserBalance.setText(getIntent().getStringExtra("money"));
        }
        LoadingViewDialog.getInstance().show(this);
        mLogic.queryCreditCardList(this, new SimpleCallBack<BaseBean<BillCreditCard>>() {
            @Override
            public void onSuccess(BaseBean<BillCreditCard> baseBean) {
                LoadingViewDialog.getInstance().dismiss();
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

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Log.e(TAG, "onFailure: "+throwable.getMessage());
                Toast.makeText(WithdrawActivity.this, "连接超时", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initView() {
        mTvUserBalance = findViewById(R.id.tv_user_balance);
        mLlShowDialog = findViewById(R.id.ll_show_dialog);
        mTvCardSelected = findViewById(R.id.tv_card_selected);
        mEtInputMoney = findViewById(R.id.et_input_money);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        mLlShowDialog.setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_withdraw);
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
                if (mCurrentCardNum == null)
                    return;
                mLogic.withdrawMoney(this, mCurrentCardNum, mEtInputMoney.getText().toString(), "", new SimpleCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        try {
                            JSONObject object = baseBean.getJsonObject();
                            Log.e(TAG, "onSuccess: "+object.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(TAG, "onFailure: "+throwable.getMessage());
                    }
                });
                break;
        }
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
                mTvCardSelected.setText(bankName+String.format(getResources().getString(R.string.bank_card_tail_num), num.substring(num.length() - 4, num.length())));
            }
        });
        dialog.show(getSupportFragmentManager(), "DialogFragment");
    }
}
