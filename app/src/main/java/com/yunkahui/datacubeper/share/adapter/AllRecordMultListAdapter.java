package com.yunkahui.datacubeper.share.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.CardDoctorApplication;
import com.yunkahui.datacubeper.common.bean.RecordHeader;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.bean.TradeRecordSummary;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.home.ui.SingleRecordActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

public class AllRecordMultListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int LEVLE_HEADER = 0;
    public static final int LEVEL_ITEM = 1;

    private boolean mIsBalanceAll;

    public AllRecordMultListAdapter(List<MultiItemEntity> data, boolean isBalanceAll) {
        super(data);
        mIsBalanceAll = isBalanceAll;
        addItemType(LEVLE_HEADER, R.layout.layout_list_header_trade_record_summary);
        addItemType(LEVEL_ITEM, R.layout.layout_list_item_trade_record);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {

        switch (item.getItemType()) {
            case LEVLE_HEADER:
                final TradeRecordSummary lv0 = (TradeRecordSummary) item;
                helper.setText(R.id.tv_time, lv0.getTime());
                if (mIsBalanceAll) {
                    helper.setText(R.id.tv_pay, String.format(CardDoctorApplication.getContext().getString(R.string.pay_format), lv0.getPay()));
                }
                helper.setText(R.id.tv_income, String.format(CardDoctorApplication.getContext().getString(R.string.income_format), lv0.getBack()));
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
                int colorID;
                if (Double.parseDouble(lv1.getMoney()) >= 0) {
                    colorID = mContext.getResources().getColor(R.color.colorPrimary);
                } else {
                    colorID = mContext.getResources().getColor(R.color.bg_color_orange_ff6633);
                }
                helper.setText(R.id.tv_status, "成功");
                helper.getView(R.id.iv_indicator).setBackground(createColorShape(colorID, 20, 20, 20, 20));
                helper.getView(R.id.layout_trade_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, SingleRecordActivity.class)
                                .putExtra("time", TimeUtils.format("yyyy-MM-dd hh:mm:ss", lv1.getTimeStamp()))
                                .putExtra("money", lv1.getMoney())
                                .putExtra("status", "成功")
                                .putExtra("remarks",lv1.getRemark())
                                .putExtra("action", Double.parseDouble(lv1.getMoney()) > 0 ? "成功" : "提现成功"));
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
