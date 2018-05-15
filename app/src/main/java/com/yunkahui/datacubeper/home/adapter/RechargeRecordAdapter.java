package com.yunkahui.datacubeper.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.MineItem;
import com.yunkahui.datacubeper.common.bean.RechargeRecord;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import java.util.List;

public class RechargeRecordAdapter extends BaseQuickAdapter<RechargeRecord.RechargeDetail, BaseViewHolder> {

    public RechargeRecordAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeRecord.RechargeDetail item) {
        helper.setText(R.id.tv_title, item.getDescr());
        helper.setText(R.id.tv_money, item.getAmount());
        helper.setText(R.id.tv_time, TimeUtils.format("yyyy-MM-dd hh:mm:ss", item.getCreate_time()));
        String status;
        int color;
        switch (item.getOrder_state()) {
            case "0":
                status = "充值初始化";
                color = Color.parseColor("#0085FF");
                break;
            case "1":
                status = "充值成功";
                color = Color.parseColor("#8E8E93");
                break;
            case "2":
                status = "充值失败";
                color = Color.parseColor("#FF3B30");
                break;
            default:
                status = "充值处理中";
                color = Color.parseColor("#8E8E93");
                break;
        }
        helper.setTextColor(R.id.tv_status, color);
        helper.setText(R.id.tv_status, status);
    }

}
