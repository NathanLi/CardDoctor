package com.yunkahui.datacubeper.home.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import java.util.List;

/**
 * Created by pc1994 on 2018/3/23
 */
public class ProfitWithdrawAdapter extends BaseQuickAdapter<TradeRecordDetail, BaseViewHolder> {

    public ProfitWithdrawAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TradeRecordDetail item) {
        helper.setText(R.id.tv_title, "账户充值");
        helper.setText(R.id.tv_money, item.getMoney());
        helper.setText(R.id.tv_time, item.getTime());
        String title = "";
        switch (item.getTitle()) {
            case "00":
                title = "佣金提现";
                break;
            case "01":
                title = "分润提现";
                break;
            case "02":
                title = "余额提现";
                break;
        }
        helper.setText(R.id.tv_title, title);
        String status = "";
        int color = Color.parseColor("#0085FF");
        if ("0".equals(item.getTradeType())) {
            status = "提现初始化";
            color = Color.parseColor("#0085FF");
        } else if ("1".equals(item.getTradeType())) {
            status = "提现成功";
            color = Color.parseColor("#8E8E93");
        } else if ("2".equals(item.getTradeType())) {
            status = "提现失败";
            color = Color.parseColor("#FF3B30");
        } else if ("3".equals(item.getTradeType())) {
            status = "提现处理中";
            color = Color.parseColor("#0085FF");
        }
        helper.setTextColor(R.id.tv_status, color);
        helper.setText(R.id.tv_status, status);
    }

}
