package com.yunkahui.datacubeper.login.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordFragment extends Fragment implements View.OnClickListener {

    private EditText mEditTextPhone;
    private EditText mEditTextAuthCode;
    private TextView mTextViewSendCode;
    private Button mButtonNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_forget_password, null, false);

        mEditTextPhone=view.findViewById(R.id.edit_text_phone);
        mEditTextAuthCode =view.findViewById(R.id.edit_text_auth_code);
        mTextViewSendCode=view.findViewById(R.id.text_view_send_auth_code);
        mButtonNext=view.findViewById(R.id.button_next);

        mEditTextPhone.addTextChangedListener(new InnerTextChangerListener());
        mEditTextAuthCode.addTextChangedListener(new InnerTextChangerListener());
        mTextViewSendCode.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_view_send_auth_code:
                break;
            case R.id.button_next:
                break;
        }
    }

    private class InnerTextChangerListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(!TextUtils.isEmpty(mEditTextPhone.getText().toString())&&!TextUtils.isEmpty(mEditTextAuthCode.getText().toString())){
                mButtonNext.setEnabled(true);
                mButtonNext.setBackgroundResource(R.drawable.bg_button_login_selector);
            }else{
                mButtonNext.setEnabled(false);
                mButtonNext.setBackgroundColor(getResources().getColor(R.color.bg_button_gray_a9a9a9));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
