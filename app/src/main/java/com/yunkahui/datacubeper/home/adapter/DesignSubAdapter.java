package com.yunkahui.datacubeper.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.HomeDesignSub;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import java.sql.Time;
import java.util.List;

public class DesignSubAdapter extends BaseQuickAdapter<HomeDesignSub.DesignSub, BaseViewHolder> {

    public DesignSubAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeDesignSub.DesignSub item) {
        String type = item.getPlan_type().equals("00") ? "消费" : "还款";
        helper.setBackgroundRes(R.id.iv_icon, type.equals("消费") ? R.mipmap.ic_spending : R.mipmap.ic_repay);
        helper.setText(R.id.tv_title, type + "-" + item.getBankCardName());
        //helper.setText(R.id.tv_msg, );
        helper.setText(R.id.tv_time, TimeUtils.format("yyyy-MM-dd hh:mm:ss", item.getDate()));
        //helper.setText(R.id.tv_type, );
        //helper.setText(R.id.tv_sign, );
        helper.setText(R.id.tv_spend_amount, String.valueOf(item.getAmount()));
        String status = "";
        switch (item.getOperation()) {
            case "0":
                status = "未操作";
                break;
            case "1":
                status = "交易成功";
                break;
            case "6":
                status = "交易失败";
                break;
            case "7":
                status = "交易关闭";
                break;
            case "8":
                status = "交易处理中";
                break;
        }
        helper.setText(R.id.tv_status, status);
    }

}
