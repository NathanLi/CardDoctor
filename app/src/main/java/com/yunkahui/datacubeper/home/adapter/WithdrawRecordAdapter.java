package com.yunkahui.datacubeper.home.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.RechargeRecord;
import com.yunkahui.datacubeper.common.bean.WithdrawRecord;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import java.util.List;

public class WithdrawRecordAdapter extends BaseQuickAdapter<WithdrawRecord.WithdrawDetail, BaseViewHolder> {

    public WithdrawRecordAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawRecord.WithdrawDetail item) {
        helper.setText(R.id.tv_title, "账户提现");
        helper.setText(R.id.tv_money, String.valueOf(item.getWithdraw_amount()));
        helper.setText(R.id.tv_time, TimeUtils.format("yyyy-MM-dd hh:mm:ss", item.getCreate_time()));
        String status;
        int color;
        switch (item.getOrder_state()) {
            case "0":
                status = "提现初始化";
                color = Color.parseColor("#0085FF");
                break;
            case "1":
                status = "提现成功";
                color = Color.parseColor("#8E8E93");
                break;
            case "2":
                status = "提现失败";
                color = Color.parseColor("#FF3B30");
                break;
            default:
                status = "提现处理中";
                color = Color.parseColor("#8E8E93");
                break;
        }
        helper.setTextColor(R.id.tv_status, color);
        helper.setText(R.id.tv_status, status);
    }

}
