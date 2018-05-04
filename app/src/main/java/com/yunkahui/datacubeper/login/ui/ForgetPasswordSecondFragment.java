package com.yunkahui.datacubeper.login.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;
import com.yunkahui.datacubeper.common.view.SimpleEditTextView;
import com.yunkahui.datacubeper.login.logic.ForgetPasswordLogic;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordSecondFragment extends Fragment implements View.OnClickListener {


    private TextView mTextViewPhone;
    private SimpleEditTextView mEditTextViewPassword;
    private SimpleEditTextView mEditTextViewPassword2;
    private String phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password_second, container, false);
        mTextViewPhone = view.findViewById(R.id.text_view_phone);
        mEditTextViewPassword = view.findViewById(R.id.simple_input_item_password);
        mEditTextViewPassword2 = view.findViewById(R.id.simple_input_item_password_repeat);
        view.findViewById(R.id.button_submit).setOnClickListener(this);

        phone = getArguments().getString("phone");
        mTextViewPhone.setText("绑定手机：" + phone);

        return view;
    }

    public void submit() {
        LoadingViewDialog.getInstance().show(getActivity());
        new ForgetPasswordLogic().submitForgetPassword(getActivity(), phone, mEditTextViewPassword.getText(), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity(), baseBean.getRespDesc());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getActivity(), "请求失败 " + throwable.toString());
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                if (TextUtils.isEmpty(mEditTextViewPassword.getText()) || TextUtils.isEmpty(mEditTextViewPassword2.getText())) {
                    ToastUtils.show(getActivity(), "请完善信息");
                    return;
                }
                if (!mEditTextViewPassword.getText().equals(mEditTextViewPassword2.getText())) {
                    ToastUtils.show(getActivity(), "两次输入密码不一致");
                    return;
                }
                if (mEditTextViewPassword.getText().length() < 6 || mEditTextViewPassword.getText().length() > 16) {
                    ToastUtils.show(getActivity(), "密码长度必须为6-16位字符，请重新输入");
                    return;
                }
                submit();
                break;
        }
    }
}
