package com.yunkahui.datacubeper.bill.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BillCard;
import com.yunkahui.datacubeper.common.view.BillCardView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27
 */

public class BillCardListAdapter extends BaseQuickAdapter<BillCard, BaseViewHolder> {

    public BillCardListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillCard item) {
        BillCardView billCardView = helper.getView(R.id.bill_card);
        billCardView.setIcon(item.getIcon());
        billCardView.setBankName(item.getBankName());
        billCardView.setCardId(item.getCardId());
        billCardView.setShouldRepayAmount(item.getShouldRepayAmount());
        billCardView.setLeaveDate(item.getLeaveDate());
        billCardView.setRepayDate(item.getRepayDate());
        billCardView.setBillAmount(item.getBillAmount());
        billCardView.setUnrepayAmount(item.getUnrepayAmount());
        billCardView.setFixedAmount(item.getFixedAmount());
        billCardView.setBillCycle(item.getBillCycle());
    }

}

