package com.yindian.carddoctor.share;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yindian.carddoctor.R;
import com.yindian.carddoctor.common.view.SimpleToolbar;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class ShareFragment extends Fragment {

    public static Fragment getInstance(String data){
        ShareFragment f = new ShareFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data",data);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.tab_item_share));
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(getArguments().getString("data"));
        return view;
    }


}