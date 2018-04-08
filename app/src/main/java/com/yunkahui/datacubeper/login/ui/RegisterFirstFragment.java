package com.yunkahui.datacubeper.login.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yunkahui.datacubeper.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFirstFragment extends Fragment {

    private EditText mEditTextPhone;
    private EditText mEditTextAuthCode;
    private EditText mEditTextInviteCode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register_first, container, false);
        return view;
    }

}
