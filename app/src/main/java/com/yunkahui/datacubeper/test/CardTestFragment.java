package com.yunkahui.datacubeper.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class CardTestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.tab_item_card_test));
        TextView tv = view.findViewById(R.id.tv);
        tv.setText("CardTestFragment");
        return view;
    }

}