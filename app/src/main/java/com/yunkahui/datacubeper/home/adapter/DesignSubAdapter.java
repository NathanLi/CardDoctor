package com.yunkahui.datacubeper.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.DesignSub;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import java.util.List;

public class DesignSubAdapter extends BaseQuickAdapter<DesignSub, BaseViewHolder> {

    private String mIsPos;
    private Context mContext;

    public DesignSubAdapter(Context context, int layoutResId, List data, String isPos) {
        super(layoutResId, data);
        mContext = context;
        mIsPos = isPos;
    }

    @Override
    protected void convert(BaseViewHolder helper, DesignSub item) {
        String type = item.getPlan_type().equals("00") ? "消费" : "还款";
        String bankCardName = item.getBankCardName();
        String bankNum=item.getBankCardNum();
        long date = item.getDate();
        String operation = item.getOperation();

        helper.setBackgroundRes(R.id.iv_icon, type.equals("消费") ? R.mipmap.ic_spending : R.mipmap.ic_repay);
        helper.setText(R.id.tv_title, type + " - " + bankCardName + "[" + bankNum + "]");

        if(TextUtils.isEmpty(item.getBusiness_name())){
            helper.getView(R.id.relative_layout_business).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.relative_layout_business).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_msg,item.getBusiness_name());
        }

        helper.setText(R.id.tv_time, TimeUtils.format("yyyy-MM-dd hh:mm:ss", date));
        if ("10".equals(mIsPos)) {
            helper.setText(R.id.tv_type, "自动");
            helper.setBackgroundRes(R.id.tv_type, R.drawable.bg_design_sub_auto);
        } else {
            helper.setText(R.id.tv_type, "pos");
            helper.setBackgroundRes(R.id.tv_type, R.drawable.bg_design_sub_pos);
        }
        helper.setText(R.id.tv_spend_amount, item.getAmountString());
        String status = "";
        int textColor = 0;
        switch (operation) {
            case "0":
                status = "11".equals(mIsPos) ? "调整 >" : "未操作";
                textColor = mContext.getResources().getColor(R.color.colorPrimary);
                break;
            case "1":
                status = "交易成功";
                textColor = mContext.getResources().getColor(R.color.text_color_green_469705);
                break;
            case "6":
                status = "交易失败";
                textColor = mContext.getResources().getColor(R.color.text_color_orange_ff5c03);
                break;
            case "7":
                status = "交易关闭";
                textColor = mContext.getResources().getColor(R.color.text_color_orange_ff5c03);
                break;
            case "8":
                status = "交易处理中";
                textColor = mContext.getResources().getColor(R.color.colorPrimary);
                break;
        }
        helper.setVisible(R.id.tv_sign, "0".equals(operation) && "还款".equals(type) && "11".equals(mIsPos));
        helper.setText(R.id.tv_status, status);
        helper.setTextColor(R.id.tv_status, textColor);
        helper.addOnClickListener(R.id.tv_sign);
        helper.addOnClickListener(R.id.tv_status);
    }

}
