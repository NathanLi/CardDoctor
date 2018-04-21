package com.yunkahui.datacubeper.test.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.test.logic.CardTestLogic;

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
    private CardTestLogic mLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_card_test);
        super.onCreate(savedInstanceState);
        setTitle("卡·测评");

    }

    @Override
    public void initData() {
        mLogic=new CardTestLogic();
        mMoney=getIntent().getDoubleExtra("money",0);
        mTextViewMoney.setText(Html.fromHtml("本次测评需要支付<font color='#ff0000'>¥"+mMoney+"</font>"));
    }

    @Override
    public void initView() {

        mEditTextViewName=findViewById(R.id.simple_input_item_name);
        mEditTextViewIdCard=findViewById(R.id.simple_input_item_id_card);
        mEditTextViewBankCardNumber=findViewById(R.id.simple_input_item_bank_card_number);
        mEditTextViewPhone=findViewById(R.id.simple_input_item_phone);
        mCheckBoxAgreement=findViewById(R.id.check_box_agreement);
        mTextViewAgreement=findViewById(R.id.text_view_agreement);
        mTextViewMoney=findViewById(R.id.text_view_money);
        mButtonSubmit=findViewById(R.id.button_submit);

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

    private class InnerTextChangeListener extends CustomTextChangeListener{
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(TextUtils.isEmpty(mEditTextViewName.getText())||TextUtils.isEmpty(mEditTextViewIdCard.getText())
                    ||TextUtils.isEmpty(mEditTextViewBankCardNumber.getText())||TextUtils.isEmpty(mEditTextViewPhone.getText())){
                mButtonSubmit.setEnabled(false);
                mButtonSubmit.setBackgroundColor(getResources().getColor(R.color.bg_button_gray_a9a9a9));
            }else{
                mButtonSubmit.setEnabled(true);
                mButtonSubmit.setBackgroundResource(R.drawable.bg_button_blue_selector);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                break;
            case R.id.text_view_agreement:
                break;
        }
    }
}
