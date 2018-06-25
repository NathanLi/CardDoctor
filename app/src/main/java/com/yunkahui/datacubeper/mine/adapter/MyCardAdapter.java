package com.yunkahui.datacubeper.mine.adapter;

import android.content.res.Resources;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.CardDoctorApplication;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.MineItem;
import com.yunkahui.datacubeper.common.bean.MyCardBean;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import java.util.List;

/**
 * Created by YD1 on 2018/4/3
 */
public class MyCardAdapter extends BaseQuickAdapter<BillCreditCard.CreditCard, BaseViewHolder> {

    public MyCardAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillCreditCard.CreditCard item) {
        if (item != null) {
            helper.setVisible(R.id.iv_sample, false);
            Resources resources = CardDoctorApplication.getContext().getResources();
            helper.setBackgroundRes(R.id.iv_bank_icon, DataUtils.getBankIconForName(item.getBankCardName()));
            helper.setText(R.id.tv_bank_name, item.getBankCardName());
            String num = item.getBankCardNum().substring(item.getBankCardNum().length() - 4, item.getBankCardNum().length());
            helper.setText(R.id.tv_bank_card_id, String.format(resources.getString(R.string.format_card_id), num));
            helper.setText(R.id.tv_card_holder, item.getCardHolder());
            helper.setText(R.id.tv_bill_date, String.format(resources.getString(R.string.format_bill_date), TimeUtils.format("MM-dd", item.getBillDayDate())));
            helper.setText(R.id.tv_repay_date, String.format(resources.getString(R.string.format_repay_date), TimeUtils.format("MM-dd", item.getRepayDayDate())));

            helper.addOnClickListener(R.id.tv_unbind);
        } else {
            helper.setVisible(R.id.iv_sample, true);
        }
    }

}
