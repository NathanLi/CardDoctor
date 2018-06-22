package com.yunkahui.datacubeper.mine.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.mine.logic.AddCashCardLogic;

import org.json.JSONObject;

//添加储蓄卡
public class AddCashCardActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private SimpleEditTextView mEditTextViewCardNumber;
    private TextView mTextViewBankName;
    private SimpleEditTextView mEditTextViewPhone;

    private String mBankName;
    private String mBankNameBin;
    private AddCashCardLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_cash_card);
        super.onCreate(savedInstanceState);
        setTitle("添加储蓄卡");
    }

    @Override
    public void initData() {
        mLogic = new AddCashCardLogic();
    }

    @Override
    public void initView() {
        mEditTextViewCardNumber = findViewById(R.id.simple_input_item_card_number);
        mTextViewBankName = findViewById(R.id.text_view_bank_name);
        mEditTextViewPhone = findViewById(R.id.simple_input_item_phone);
        findViewById(R.id.button_submit).setOnClickListener(this);

        mEditTextViewCardNumber.getEditTextInput().addTextChangedListener(new CustomTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 10) {
                    checkBankCardName();
                } else if (s.length() < 10) {
                    mBankName = "";
                    mTextViewBankName.setText(R.string.bank_card_name);
                }
            }
        });

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    private void checkBankCardName() {

        mLogic.checkBankCardName(this, mEditTextViewCardNumber.getText(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                try {
                    LogUtils.e("查询所属银行->" + baseBean.getJsonObject().toString());
                    JSONObject object = baseBean.getJsonObject();
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
                        mBankName = object.optJSONObject("respData").optString("bankName");
                        mBankNameBin = object.optJSONObject("respData").optString("bankNameEn");
                        mTextViewBankName.setText(mBankName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LogUtils.e("查询所属银行失败->" + throwable.toString());
            }
        });

    }


    private boolean check() {
        if (TextUtils.isEmpty(mEditTextViewCardNumber.getText())) {
            ToastUtils.show(getApplicationContext(), R.string.card_number_tips);
            return false;
        }
        if (TextUtils.isEmpty(mEditTextViewPhone.getText())) {
            ToastUtils.show(getApplicationContext(), R.string.bank_phone_tips);
            return false;
        }
        if (TextUtils.isEmpty(mBankName)) {
            ToastUtils.show(getApplicationContext(), "尚未获取该卡号的发卡行");
            return false;
        }
        return true;
    }


    private void addCashCard() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.addCashCard(this, mEditTextViewCardNumber.getText(), mBankName, mBankNameBin, mEditTextViewPhone.getText(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                try {
                    LogUtils.e("添加储蓄卡->" + baseBean.getJsonObject().toString());
                    JSONObject object = baseBean.getJsonObject();
                    ToastUtils.show(getApplicationContext(), object.optString("respDesc"));
                    if (RequestUtils.SUCCESS.equals(object.optString("respCode"))) {
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
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                if (check()) {
                    addCashCard();
                }
                break;
        }
    }
}
