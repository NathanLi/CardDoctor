package com.yunkahui.datacubeper.share.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.CardDoctorApplication;
import com.yunkahui.datacubeper.common.bean.SingleRecord;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.bean.TradeRecordSummary;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.home.ui.SingleRecordActivity;
import com.yunkahui.datacubeper.share.logic.RecordType;

import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

public class IncomePayMultListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int LEVLE_HEADER = 0;
    public static final int LEVEL_ITEM = 1;

    private RecordType mRecordType;

    public IncomePayMultListAdapter(List<MultiItemEntity> data, RecordType recordType) {
        super(data);
        this.mRecordType = recordType;
        addItemType(LEVLE_HEADER, R.layout.layout_list_header_trade_record_summary);
        addItemType(LEVEL_ITEM, R.layout.layout_list_item_trade_record);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {

        switch (item.getItemType()) {
            case LEVLE_HEADER:
                final TradeRecordSummary lv0 = (TradeRecordSummary) item;
                helper.setText(R.id.tv_time, lv0.getTime());
                helper.setText(R.id.tv_pay, String.format(CardDoctorApplication.getContext().getString(R.string.pay_format), lv0.getPay()));
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = helper.getAdapterPosition();
                        if (lv0.isExpanded()) {
                            collapse(position);
                        } else {
                            expand(position);
                        }
                    }
                });
                break;
            case LEVEL_ITEM:
                final TradeRecordDetail lv1 = (TradeRecordDetail) item;
                helper.setText(R.id.tv_title, lv1.getTitle());
                helper.setText(R.id.tv_time, lv1.getTime());
                helper.setText(R.id.tv_money, lv1.getMoney());
                helper.getView(R.id.iv_indicator).setBackground(createColorShape(
                        mContext.getResources().getColor(R.color.bg_color_orange_ff6633), 20, 20, 20, 20));
                final String status;
                int color;
                switch (lv1.getOrderStatus() == null ? "" : lv1.getOrderStatus()) {
                    case "0":
                        status = Double.parseDouble(lv1.getMoney()) < 0 ? "提现处理中" : "充值处理中";
                        color = Color.parseColor("#0085FF");
                        break;
                    case "1":
                        status = Double.parseDouble(lv1.getMoney()) < 0 ? "提现成功" : "充值成功";
                        color = Color.parseColor("#8E8E93");
                        break;
                    case "2":
                        status = Double.parseDouble(lv1.getMoney()) < 0 ? "提现失败" : "充值失败";
                        color = Color.parseColor("#FF3B30");
                        break;
                    default:
                        status = Double.parseDouble(lv1.getMoney()) < 0 ? "提现处理中" : "充值处理中";
                        color = Color.parseColor("#8E8E93");
                        break;
                }
                helper.setTextColor(R.id.tv_status, color);
                helper.setText(R.id.tv_status, status);
                helper.getView(R.id.layout_trade_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SingleRecord record = new SingleRecord();
                        record.setTime(lv1.getTime());
                        record.setMoney(lv1.getMoney());
                        record.setStatus(status);
                        record.setAction(mRecordType.getSub() + (mRecordType == RecordType.integral_withdraw ? "支出" : "提现"));
                        mContext.startActivity(new Intent(mContext, SingleRecordActivity.class)
                                .putExtra("info", record));
                    }
                });
                break;
        }

    }

    private GradientDrawable createColorShape(int color, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
        drawable.setColor(color);
        return drawable;
    }
}
