package com.yunkahui.datacubeper.bill.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.bill.ui.BillDetailActivity;
import com.yunkahui.datacubeper.bill.ui.BillSyncActivity;
import com.yunkahui.datacubeper.bill.ui.PlanPickerActivity;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.view.BillCardView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2018/3/27
 */

public class BillCardListAdapter extends BaseQuickAdapter<BillCreditCard.CreditCard, BaseViewHolder> {

    private static final int DAY_MILLIIS = 1000 * 1 * 60 * 60 * 24;
    private Context mContext;

    public BillCardListAdapter(Context context, int layoutResId, List data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    private static final String TAG = "BillCardListAdapter";

    @Override
    protected void convert(BaseViewHolder helper, final BillCreditCard.CreditCard item) {
        //******** item 为空，证明没数据，显示默认样例 ********
        if (item != null) {
            helper.setVisible(R.id.iv_sample, false);
            BillCardView billCardView = helper.getView(R.id.bill_card);
            //******** 设置银行图标 ********
            billCardView.setIcon(DataUtils.getBankIconMap().containsKey(item.getBankCardName()) ? DataUtils.getBankIconMap().get(item.getBankCardName()) : R.mipmap.bank_other);
            //******** 设置银行名字 ********
            billCardView.setBankName(item.getBankCardName());
            //******** 设置银行卡id ********
            final String cardIdFormat = String.format(mContext.getResources().getString(R.string.bank_card_tail_num), item.getBankCardNum().substring(item.getBankCardNum().length() - 4, item.getBankCardNum().length()));
            billCardView.setCardId(cardIdFormat);
            //******** 设置相关还款信息 ********
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
            //******** 设置银行卡和账单同步的点击事件 ********
            billCardView.setOnClickBillCardListener(new BillCardView.CustomBillCardListenr() {
                @Override
                public void onClickBillSync() {
                    mContext.startActivity(new Intent(mContext, BillSyncActivity.class)
                            .putExtra("ban_card_num", item.getBankCardName()));
                }

                @Override
                public void onClickBillCard() {
                    mContext.startActivity(new Intent(mContext, BillDetailActivity.class)
                            .putExtra("user_credit_card_id", item.getUserCreditCardId())
                            .putExtra("card_holder", item.getCardHolder())
                            .putExtra("card_num", item.getBankCardNum())
                            .putExtra("reday_date", itemTime.substring(5))
                            .putExtra("bill_date", item.getBillDayDate()));
                }
            });
            //******** 设置是否显示智能规划 ********
            if (item.getBillDayDate() + DAY_MILLIIS < System.currentTimeMillis() && System.currentTimeMillis() < item.getRepayDayDate() - DAY_MILLIIS && item.getCanPlanning() == 1) {
                billCardView.setSmartPlanVisibility(View.VISIBLE);
                billCardView.setOnClickSmartPlanListener(new BillCardView.OnClickSmartPlanListener() {

                    @Override
                    public void onClickSmartPlan() {
                        mContext.startActivity(new Intent(mContext, PlanPickerActivity.class)
                                .putExtra("time", item.getRepayDayDate())
                                .putExtra("user_credit_card_id", item.getUserCreditCardId())
                                .putExtra("bank_card_name", item.getBankCardName())
                                .putExtra("bank_card_num", cardIdFormat));
                    }
                });
            } else {
                billCardView.setSmartPlanVisibility(View.INVISIBLE);
            }
        } else {
            helper.setVisible(R.id.iv_sample, true);
        }
    }

}

