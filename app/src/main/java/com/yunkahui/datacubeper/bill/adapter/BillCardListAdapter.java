package com.yunkahui.datacubeper.bill.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.bill.ui.PlanPickerActivity;
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


    public BillCardListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final BillCreditCard.CreditCard item) {
        helper.addOnClickListener(R.id.btn_bill_sync);
//        helper.addOnClickListener(R.id.layout_card_item);
        //******** item 为空，证明没数据，显示默认样例 ********
        if (item != null) {
            helper.setVisible(R.id.iv_sample, false);
            BillCardView billCardView = helper.getView(R.id.bill_card);
            billCardView.setIsExtend(true);
            billCardView.setIcon(DataUtils.getBankIconMap().containsKey(item.getBankCardName()) ? DataUtils.getBankIconMap().get(item.getBankCardName()) : R.mipmap.bank_other);
            billCardView.setBankName(item.getBankCardName());
            final String cardIdFormat = String.format(mContext.getResources().getString(R.string.bank_card_tail_num), item.getBankCardNum().substring(item.getBankCardNum().length() - 4, item.getBankCardNum().length()));
            billCardView.setCardId(cardIdFormat);
            billCardView.setShouldRepayAmount(String.valueOf(item.getThisShouldRepay()));
            billCardView.setShouldRepay(Integer.parseInt(item.getRepayStatus()) == 1 ? "已经还清" : "应还金额");
            billCardView.setLeaveDate(String.valueOf(item.getDistanceDay()));
            billCardView.setLimit(item.getIsOverRepay() == 1 ? "天出账" : "天到期");
            billCardView.setBillAmount("账单金额：" + "-");
            billCardView.setUnrepayAmount("剩余未还：" + item.getSurPlusRepay());
            billCardView.setFixedAmount("固定金额：" + item.getFixLine());

            //******** 设置账单周期 ********
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(item.getBillDayDate());
            final String startDay = TimeUtils.format("yyyy.MM.dd", calendar.getTimeInMillis());
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            final String endDay = TimeUtils.format("yyyy.MM.dd", calendar.getTimeInMillis());
            billCardView.setBillCycle("账单周期：" + startDay.substring(5) + "-" + endDay.substring(5));
            //******** 设置还款日期 ********
            final String itemTime = TimeUtils.format("yyyy-MM-dd", item.getRepayDayDate());
            billCardView.setRepayDate(itemTime.substring(5));
            //******** 设置是否显示智能规划 ********
            //********
            // 若当前时间大于还款日
            // 若当前状态为不能规划
            // 若明天为还款日，不显示 ********
            Calendar repayCalendar = TimeUtils.getCalendar(item.getRepayDayDate());
            Calendar currentCalendar = TimeUtils.getCalendar(System.currentTimeMillis());
            boolean isShouldRepayTomorrow = repayCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) &&
                    repayCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH) &&
                    repayCalendar.get(Calendar.DAY_OF_MONTH) == currentCalendar.get(Calendar.DAY_OF_MONTH) + 1;
            if (item.getBillDayDate() + TimeUtils.DAY_MILLIS < System.currentTimeMillis() &&
                    System.currentTimeMillis() < item.getRepayDayDate() - TimeUtils.DAY_MILLIS &&
                    item.getCanPlanning() == 1 && !isShouldRepayTomorrow) {
                billCardView.setSmartPlanVisibility(View.VISIBLE);
                billCardView.setOnClickSmartPlanListener(new BillCardView.OnClickSmartPlanListener() {

                    @Override
                    public void onClickSmartPlan() {
                        mContext.startActivity(new Intent(mContext, PlanPickerActivity.class)
                                .putExtra("time", item.getRepayDayDate())
                                .putExtra("user_credit_card_id", item.getUserCreditCardId())
                                .putExtra("bank_card_name", item.getBankCardName())
                                .putExtra("bank_card_num", item.getBankCardNum()));
                    }
                });
            } else {
                billCardView.setSmartPlanVisibility(View.INVISIBLE);
            }
        } else {
            BillCardView billCardView = helper.getView(R.id.bill_card);
            billCardView.setIsExtend(false);
            helper.setVisible(R.id.iv_sample, true);
        }
    }

}

