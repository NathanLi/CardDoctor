package com.yunkahui.datacubeper.share;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.view.DoubleBlockView;
import com.yunkahui.datacubeper.common.view.SimpleToolbar;

import org.w3c.dom.Text;

/**
 * Created by pc1994 on 2018/3/22.
 */

public class ShareFragment extends BaseFragment implements View.OnClickListener {

    private DoubleBlockView mDoubleBlockView1;
    private DoubleBlockView mDoubleBlockView2;

    @Override
    public void initData() {
        initListener();
    }

    private void initListener() {
        mDoubleBlockView1.setOnLeftBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //******** 我的钱包点击事件 ********
            }
        });
        mDoubleBlockView1.setOnRightBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //******** 累计分润点击事件 ********
            }
        });
        mDoubleBlockView2.setOnLeftBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //******** 普通会员点击事件 ********
            }
        });
        mDoubleBlockView2.setOnRightBlockClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //******** VIP会员点击事件 ********
            }
        });
    }

    @Override
    public void initView(View view) {
        SimpleToolbar toolbar = view.findViewById(R.id.tool_bar);
        toolbar.setTitleName(getString(R.string.tab_item_share));
        mDoubleBlockView1 = view.findViewById(R.id.double_block_view_1);
        mDoubleBlockView2 = view.findViewById(R.id.double_block_view_2);
        view.findViewById(R.id.btn_produce_activation_code).setOnClickListener(this);
        view.findViewById(R.id.tv_link_share).setOnClickListener(this);
        view.findViewById(R.id.tv_qr_share).setOnClickListener(this);
        view.findViewById(R.id.tv_vip_course).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_produce_activation_code:
                break;
            case R.id.tv_link_share:
                break;
            case R.id.tv_qr_share:
                break;
            case R.id.tv_vip_course:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_share;
    }
}