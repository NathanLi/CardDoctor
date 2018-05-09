package com.yunkahui.datacubeper.bill.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.FailBankCardDetail;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/5/8.
 */

public class FailCardPlanListAdapter extends BaseQuickAdapter<FailBankCardDetail.Plan, BaseViewHolder> {

    public FailCardPlanListAdapter(int layoutResId, @Nullable List<FailBankCardDetail.Plan> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FailBankCardDetail.Plan item) {
        helper.setImageResource(R.id.iv_icon, "00".equals(item.getPlan_type()) ? R.mipmap.ic_spending : R.mipmap.ic_repay);
        String type = "00".equals(item.getPlan_type()) ? "消费" : "还款";
        helper.setText(R.id.tv_title, type + "-" + item.getBankcard_name() + item.getBankcard_num());
        helper.setText(R.id.tv_time, TimeUtils.format("MM-dd hh:mm", item.getDate()));
        helper.setText(R.id.tv_status, "6".equals(item.getOperation()) ? "交易失败" : "交易关闭");
        helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.red));
        helper.setText(R.id.tv_spend_amount, item.getAmount() + "");
        helper.setText(R.id.tv_type, "自动");
    }
}
