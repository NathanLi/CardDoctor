package com.yunkahui.datacubeper.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class HomeNewAdapter extends BaseQuickAdapter<BillCreditCard.CreditCard,BaseViewHolder> {

    public HomeNewAdapter(int layoutResId, @Nullable List<BillCreditCard.CreditCard> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillCreditCard.CreditCard item) {

    }
}
