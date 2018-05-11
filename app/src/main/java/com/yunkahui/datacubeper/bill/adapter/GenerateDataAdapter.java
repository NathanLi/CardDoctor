package com.yunkahui.datacubeper.bill.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.GeneratePlanItem;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import java.util.List;

public class GenerateDataAdapter extends BaseQuickAdapter<GeneratePlanItem, BaseViewHolder> {

    private boolean mIsPos;
    private String mBankName;
    private String mCardId;

    public GenerateDataAdapter(int layoutResId, List data, String bankName, String cardId, boolean isPos) {
        super(layoutResId, data);
        this.mBankName = bankName;
        this.mCardId = cardId;
        this.mIsPos = isPos;
    }

    @Override
    protected void convert(BaseViewHolder helper, GeneratePlanItem item) {
        helper.setImageResource(R.id.iv_icon, item.getType() == 0 ? R.mipmap.ic_spending : R.mipmap.ic_repay);
        helper.setText(R.id.tv_title, String.format("%s - %s", item.getType() == 0 ? "消费" : "还款", mBankName));
        if (item.getType() == 0 && mIsPos) {
            helper.getView(R.id.tv_msg).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_msg, item.getMccType());
        } else {
            helper.getView(R.id.tv_msg).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_card_id, mCardId);
        helper.setText(R.id.tv_time, TimeUtils.format("yyyy-MM-dd hh:mm:ss", item.getTimeStamp()));
        helper.setText(R.id.tv_spend_amount, String.valueOf(item.getMoney()));
        if (mIsPos) {
            helper.setText(R.id.tv_status, "调整 >");
            helper.addOnClickListener(R.id.tv_status);
        } else {
            helper.getView(R.id.tv_type).setVisibility(View.GONE);
        }
    }

}
