package com.yunkahui.datacubeper.mine.adapter;

import android.content.res.Resources;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.CardDoctorApplication;
import com.yunkahui.datacubeper.common.bean.MineItem;
import com.yunkahui.datacubeper.common.bean.MyCardBean;

import java.util.List;

/**
 * Created by YD1 on 2018/4/3
 */
public class MyCardAdapter extends BaseQuickAdapter<MyCardBean, BaseViewHolder> {

    public MyCardAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCardBean item) {
        if (item != null) {
            helper.setVisible(R.id.iv_sample, false);
            Resources resources = CardDoctorApplication.getContext().getResources();
            helper.setBackgroundRes(R.id.iv_bank_icon, item.getIcon());
            helper.setText(R.id.tv_bank_name, item.getBankCardName());
            helper.setText(R.id.tv_bank_card_id, String.format(resources.getString(R.string.format_card_id), item.getBankCardId()));
            helper.setText(R.id.tv_card_holder, item.getCardHolder());
            helper.setText(R.id.tv_bill_date, String.format(resources.getString(R.string.format_bill_date), item.getBillDate()));
            helper.setText(R.id.tv_repay_date, String.format(resources.getString(R.string.format_repay_date), item.getRepayDate()));

            helper.addOnClickListener(R.id.tv_unbind);
        } else {
            helper.setVisible(R.id.iv_sample, true);
        }
    }

}
