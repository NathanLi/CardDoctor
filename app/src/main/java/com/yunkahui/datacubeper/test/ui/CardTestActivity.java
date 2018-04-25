package com.yunkahui.datacubeper.test.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.CardTestItem;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.PayHelper;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.test.logic.CardTestLogic;

import org.json.JSONObject;

public class CardTestActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private SimpleEditTextView mEditTextViewName;
    private SimpleEditTextView mEditTextViewIdCard;
    private SimpleEditTextView mEditTextViewBankCardNumber;
    private SimpleEditTextView mEditTextViewPhone;
    private CheckBox mCheckBoxAgreement;
    private TextView mTextViewAgreement;
    private TextView mTextViewMoney;
    private Button mButtonSubmit;

    private double mMoney;
    private String mPayResult;
    private CardTestItem.Card mCard;
    private CardTestLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_card_test);
        super.onCreate(savedInstanceState);
        setTitle("卡·测评");

    }

    @Override
    public void initData() {
        mLogic = new CardTestLogic();
        mMoney = getIntent().getDoubleExtra("money", 0);
        mCard = (CardTestItem.Card) getIntent().getSerializableExtra("card");
        if (mCard != null) {
            mEditTextViewName.setText(mCard.getCardholder());
            mEditTextViewIdCard.setText(mCard.getIdentify_ID());
            mEditTextViewBankCardNumber.setText(mCard.getBankcard_num());
            mEditTextViewPhone.setText(mCard.getBankcard_tel());
        }
        mTextViewMoney.setText(Html.fromHtml("本次测评需要支付<font color='#ff0000'>¥" + mMoney + "</font>"));
    }

    @Override
    public void initView() {

        mEditTextViewName = findViewById(R.id.simple_input_item_name);
        mEditTextViewIdCard = findViewById(R.id.simple_input_item_id_card);
        mEditTextViewBankCardNumber = findViewById(R.id.simple_input_item_bank_card_number);
        mEditTextViewPhone = findViewById(R.id.simple_input_item_phone);
        mCheckBoxAgreement = findViewById(R.id.check_box_agreement);
        mTextViewAgreement = findViewById(R.id.text_view_agreement);
        mTextViewMoney = findViewById(R.id.text_view_money);
        mButtonSubmit = findViewById(R.id.button_submit);

        mEditTextViewName.getEditTextInput().addTextChangedListener(new InnerTextChangeListener());
        mEditTextViewIdCard.getEditTextInput().addTextChangedListener(new InnerTextChangeListener());
        mEditTextViewBankCardNumber.getEditTextInput().addTextChangedListener(new InnerTextChangeListener());
        mEditTextViewPhone.getEditTextInput().addTextChangedListener(new InnerTextChangeListener());
        mTextViewMoney.setOnClickListener(this);
        mButtonSubmit.setOnClickListener(this);

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    private class InnerTextChangeListener extends CustomTextChangeListener {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(mEditTextViewName.getText()) || TextUtils.isEmpty(mEditTextViewIdCard.getText())
                    || TextUtils.isEmpty(mEditTextViewBankCardNumber.getText()) || TextUtils.isEmpty(mEditTextViewPhone.getText())) {
                mButtonSubmit.setEnabled(false);
                mButtonSubmit.setBackgroundColor(getResources().getColor(R.color.bg_button_gray_a9a9a9));
            } else {
                mButtonSubmit.setEnabled(true);
                mButtonSubmit.setBackgroundResource(R.drawable.bg_button_blue_selector);
            }
        }
    }

    //创建支付订单，获取支付orderInfo
    private void createCardTestPayOrder() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.createCardTestPayOrder(this, mMoney+"", "ALIPAY", new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("卡测评-支付订单->" + baseBean.toString());
                JSONObject object=baseBean.getJsonObject();
                if(RequestUtils.SUCCESS.equals(baseBean.getRespCode())){
                    mPayResult=object.optJSONObject("respData").optString("out_trade_no");
                    String orderInfo=object.optJSONObject("respData").optString("order_info");
                    pay(orderInfo);
                }else{
                    ToastUtils.show(getApplicationContext(), object.optString("respDesc"));
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });

    }

    //支付宝支付
    private void pay(String orderInfo) {
        PayHelper.PayEvent event = PayHelper.newPayEvnet();
        event.setOnPayListener(new PayHelper.OnPayListener() {
            @Override
            public void onSuccess(String data) {
                LogUtils.e("支付宝成功->" + data);
                startTest();
            }
            @Override
            public void onFill(String error) {
                LogUtils.e("支付宝失败->" + error);
            }
        });
        event.pay(this, orderInfo);
    }

    //开始测评-获取评测结果
    private void startTest() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.submitCardTest(this, mEditTextViewName.getText(), mEditTextViewIdCard.getText(), mEditTextViewBankCardNumber.getText(), mEditTextViewPhone.getText(), mPayResult, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("发起测评-->" + baseBean.toString());
                JSONObject object=baseBean.getJsonObject();
                ToastUtils.show(getApplicationContext(),baseBean.getRespDesc());
                if(RequestUtils.SUCCESS.equals(baseBean.getRespCode())){
                    String data=object.optJSONObject("respData").optJSONObject("result").toString();
                    TestResultActivity.actionStart(CardTestActivity.this, data, System.currentTimeMillis());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
                LogUtils.e("发起测评-->" + throwable.toString());
            }
        });
    }

    private boolean check() {
        if (TextUtils.isEmpty(mEditTextViewName.getText()) || TextUtils.isEmpty(mEditTextViewIdCard.getText()) || TextUtils.isEmpty(mEditTextViewBankCardNumber.getText())
                || TextUtils.isEmpty(mEditTextViewPhone.getText())) {
            ToastUtils.show(getApplicationContext(), "请完善信息");
            return false;
        }
        if (!mCheckBoxAgreement.isChecked()) {
            ToastUtils.show(getApplicationContext(), "请阅读并同意授权协议");
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                if (check()) {
                    createCardTestPayOrder();
                }
                break;
            case R.id.text_view_agreement:
                break;
        }
    }
}
