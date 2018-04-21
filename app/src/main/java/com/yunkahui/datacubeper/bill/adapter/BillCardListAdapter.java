package com.yunkahui.datacubeper.bill.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.bill.ui.PlanPickerActivity;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.view.BillCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2018/3/27
 */

public class BillCardListAdapter extends BaseQuickAdapter<BillCreditCard.CreditCard, BaseViewHolder> {

    private Context mContext;
    private String currentDate;

    public BillCardListAdapter(Context context, int layoutResId, List data) {
        super(layoutResId, data);
        this.mContext = context;
        currentDate = TimeUtils.format("yyyy-MM-dd", System.currentTimeMillis());
    }

    private static final String TAG = "BillCardListAdapter";

    @Override
    protected void convert(BaseViewHolder helper, final BillCreditCard.CreditCard item) {
        if (item != null) {
            try {
                helper.setVisible(R.id.iv_sample, false);
                BillCardView billCardView = helper.getView(R.id.bill_card);
                billCardView.setIcon(DataUtils.getBankIconMap().containsKey(item.getBankCardName()) ? DataUtils.getBankIconMap().get(item.getBankCardName()) : R.mipmap.bank_other);
                billCardView.setBankName(item.getBankCardName());
                billCardView.setCardId(String.format(mContext.getResources().getString(R.string.bank_card_tail_num), item.getBankCardNum().substring(item.getBankCardNum().length() - 4, item.getBankCardNum().length())));
                billCardView.setShouldRepayAmount(String.valueOf(item.getThisShouldRepay()));
                billCardView.setShouldRepay(Integer.parseInt(item.getRepayStatus()) == 1 ? "已经还清" : "应还金额");
                billCardView.setLeaveDate(String.valueOf(item.getDistanceDay()));
                billCardView.setLimit(item.getIsOverRepay() == 1 ? "天出账" : "天到期");
                billCardView.setBillAmount("账单金额：" + "-");
                billCardView.setUnrepayAmount("剩余未还：" + item.getSurPlusRepay());
                billCardView.setFixedAmount("固定金额：" + item.getFixLine());
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(item.getBillDayDate());
                final String cBillDay = TimeUtils.format("yyyy.MM.dd", calendar.getTimeInMillis());
                calendar.add(Calendar.MONTH, 1);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                final String nBillDay = TimeUtils.format("yyyy.MM.dd", calendar.getTimeInMillis());
                billCardView.setBillCycle("账单周期：" + cBillDay.substring(5) + "-" + nBillDay.substring(5));
                final String itemTime = TimeUtils.format("yyyy-MM-dd", item.getRepayDayDate());
                billCardView.setRepayDate(itemTime.substring(5));
                Calendar repayCalendar = TimeUtils.formatCalendar("yyyy-MM-dd", itemTime);
                Calendar curCalendar = TimeUtils.formatCalendar("yyyy-MM-dd", currentDate);
                Calendar startCalendar = TimeUtils.formatCalendar("yyyy-MM-dd", TimeUtils.format("yyyy-MM-dd", calendar.getTimeInMillis()));
                startCalendar.add(Calendar.MONTH, -1);
                startCalendar.add(Calendar.DAY_OF_MONTH, 1);
                if (curCalendar.get(Calendar.YEAR) >= startCalendar.get(Calendar.YEAR) &&
                        curCalendar.get(Calendar.YEAR) <= repayCalendar.get(Calendar.YEAR) &&
                        curCalendar.get(Calendar.MONTH)+1 >= startCalendar.get(Calendar.MONTH)+1 &&
                        curCalendar.get(Calendar.MONTH)+1 <= repayCalendar.get(Calendar.MONTH)+1 &&
                        curCalendar.get(Calendar.DAY_OF_MONTH) > startCalendar.get(Calendar.DAY_OF_MONTH) &&
                        curCalendar.get(Calendar.DAY_OF_MONTH) < repayCalendar.get(Calendar.DAY_OF_MONTH) &&
                        item.getCanPlanning() == 1) {
                    billCardView.setSmartPlanVisibility(View.VISIBLE);
                    billCardView.setOnClickBillCardListener(new BillCardView.OnClickBillCardListener() {
                        @Override
                        public void onClickBillSync() {

                        }

                        @Override
                        public void onClickSmartPlan() {
                            mContext.startActivity(new Intent(mContext, PlanPickerActivity.class)
                                    .putExtra("time", nBillDay)
                                    .putExtra("user_credit_card_id", item.getUserCreditCardId()));
                        }
                    });
                } else {
                    billCardView.setSmartPlanVisibility(View.INVISIBLE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            helper.setVisible(R.id.iv_sample, true);
        }
    }

}

