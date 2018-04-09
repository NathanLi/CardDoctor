package com.yunkahui.datacubeper.share;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class ShareFragment extends BaseFragment {

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.tab_item_share));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_share;
    }

}