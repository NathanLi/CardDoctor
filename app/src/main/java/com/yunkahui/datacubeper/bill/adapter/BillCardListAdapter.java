package com.yunkahui.datacubeper.bill.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.view.BillCardView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2018/3/27
 */

public class BillCardListAdapter extends BaseQuickAdapter<BillCreditCard.CreditCard, BaseViewHolder> {

    private Context mContext;

    public BillCardListAdapter(Context context, int layoutResId, List data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BillCreditCard.CreditCard item) {
        BillCardView billCardView = helper.getView(R.id.bill_card);
        billCardView.setIcon(DataUtils.getBankIconMap().containsKey(item.getBankCardName()) ? DataUtils.getBankIconMap().get(item.getBankCardName()) : R.mipmap.bank_other);
        billCardView.setBankName(item.getBankCardName());
        billCardView.setCardId(String.format(mContext.getResources().getString(R.string.bank_card_tail_num), item.getBankCardNum().substring(item.getBankCardNum().length() - 4, item.getBankCardNum().length())));
        billCardView.setShouldRepayAmount(String.valueOf(item.getThisShouldRepay()));
        billCardView.setShouldRepay(Integer.parseInt(item.getRepayStatus()) == 1 ? "已经还清" : "应还金额");
        billCardView.setLeaveDate(String.valueOf(item.getDistanceDay()));
        billCardView.setLimit(item.getIsOverRepay() == 1 ? "天出账" : "天到期");
        billCardView.setRepayDate(TimeUtils.format("MM-dd", item.getRepayDayDate()));
        billCardView.setBillAmount("账单金额：" + "-");
        billCardView.setUnrepayAmount("剩余未还：" + item.getSurPlusRepay());
        billCardView.setFixedAmount("固定金额：" + item.getFixLine());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(item.getBillDayDate());
        String cBillDay = TimeUtils.format("MM.dd", calendar.getTimeInMillis());
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String nBillDay = TimeUtils.format("MM.dd", calendar.getTimeInMillis());
        billCardView.setBillCycle("账单周期：" + cBillDay + "-" + nBillDay);
    }

}

