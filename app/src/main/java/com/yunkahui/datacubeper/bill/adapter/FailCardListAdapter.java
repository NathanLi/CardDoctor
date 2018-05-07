package com.yunkahui.datacubeper.bill.adapter;

import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.GlideApp;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.FailBankCard;
import com.yunkahui.datacubeper.common.utils.DataUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/5/7.
 */

public class FailCardListAdapter extends BaseQuickAdapter<FailBankCard, BaseViewHolder> {

    public FailCardListAdapter(int layoutResId, @Nullable List<FailBankCard> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FailBankCard item) {

        helper.setImageResource(R.id.image_view_icon, DataUtils.getBankIconMap().get(item.getBankcard_name()));
        helper.setText(R.id.text_view_title, item.getBankcard_name() + "[" + item.getBankcard_num() + "]");

        helper.addOnClickListener(R.id.text_view_right_desc)
                .addOnClickListener(R.id.button_submit_1)
                .addOnClickListener(R.id.button_submit_2);

        if (item.getIs_restart() == 0) {
            helper.getView(R.id.button_submit_1).setEnabled(false);
            helper.getView(R.id.button_submit_1).setAlpha(0.5f);
        } else {
            helper.getView(R.id.button_submit_1).setEnabled(true);
            helper.getView(R.id.button_submit_1).setAlpha(1);
        }

        if (item.getIs_replanning() == 0) {
            helper.getView(R.id.button_submit_2).setEnabled(false);
            helper.getView(R.id.button_submit_2).setAlpha(0.5f);
        } else {
            helper.getView(R.id.button_submit_2).setEnabled(true);
            helper.getView(R.id.button_submit_2).setAlpha(1);
        }


    }
}
