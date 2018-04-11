package com.yunkahui.datacubeper.mine.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunkahui.datacubeper.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditPasswordFragment extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_edit_password, container, false);
        view.findViewById(R.id.text_view_reset_password).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_view_reset_password:
                ((EditPasswordActivity)getActivity()).startToResetPassword();
                break;
        }
    }
}
