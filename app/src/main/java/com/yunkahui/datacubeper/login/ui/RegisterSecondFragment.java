package com.yunkahui.datacubeper.login.ui;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterSecondFragment extends BaseFragment implements View.OnClickListener {

    private EditText mEditTextNickName;
    private EditText mEditTextPassword;
    private EditText mEditTextPasswordRepeat;
    private Button mButtonSubmit;

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        mEditTextNickName = view.findViewById(R.id.edit_text_nick_name);
        mEditTextPassword = view.findViewById(R.id.edit_text_password);
        mEditTextPasswordRepeat = view.findViewById(R.id.edit_text_password_repeat);
        mButtonSubmit = view.findViewById(R.id.button_next);

        mEditTextNickName.addTextChangedListener(new InnerTextChangerListener());
        mEditTextPassword.addTextChangedListener(new InnerTextChangerListener());
        mEditTextPasswordRepeat.addTextChangedListener(new InnerTextChangerListener());
        mButtonSubmit.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register_second;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_next:
                if (!mEditTextPassword.getText().toString().equals(mEditTextPasswordRepeat.getText().toString())) {
                    ToastUtils.show(getActivity(), "两次密码输入不一致");
                    return;
                }
                if (mEditTextPassword.getText().toString().length() < 6 || mEditTextPassword.getText().toString().length() > 16) {
                    ToastUtils.show(getActivity(), "密码长度必须为6-16位字符，请重新输入");
                    return;
                }
                RegisterActivity activity = (RegisterActivity) getActivity();
                activity.setNickName(mEditTextNickName.getText().toString());
                activity.setPassword(mEditTextPassword.getText().toString());
                activity.register();
                break;
        }
    }

    private class InnerTextChangerListener extends CustomTextChangeListener {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(mEditTextNickName.getText().toString()) && !TextUtils.isEmpty(mEditTextPassword.getText().toString())
                    && !TextUtils.isEmpty(mEditTextPasswordRepeat.getText().toString())) {
                mButtonSubmit.setEnabled(true);
                mButtonSubmit.setBackgroundResource(R.drawable.bg_button_login_selector);
            } else {
                mButtonSubmit.setEnabled(false);
                mButtonSubmit.setBackgroundColor(Color.parseColor("#CCCCCC"));
            }
        }
    }
}
