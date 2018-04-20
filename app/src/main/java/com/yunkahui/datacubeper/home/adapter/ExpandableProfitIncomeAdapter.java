package com.yunkahui.datacubeper.home.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.bean.TradeRecordSummary;

import java.util.List;

public class ExpandableProfitIncomeAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private Context mContext;

    public ExpandableProfitIncomeAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        mContext = context;
        addItemType(TYPE_LEVEL_0, R.layout.layout_list_header_trade_record_summary);
        addItemType(TYPE_LEVEL_1, R.layout.layout_list_item_trade_record_detail);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final TradeRecordSummary lv0 = (TradeRecordSummary) item;
                helper.setText(R.id.tv_time, lv0.getTime());
                helper.setText(R.id.tv_mess, lv0.getMessage());
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
            case TYPE_LEVEL_1:
                TradeRecordDetail lv1 = (TradeRecordDetail) item;
                helper.setText(R.id.show_mess, lv1.getTitle());
                helper.setText(R.id.show_time, lv1.getTime());
                helper.setText(R.id.show_money, lv1.getMoney());
                int colorID;
                if ("03".equals(lv1.getTradeType())) {
                    colorID = mContext.getResources().getColor(R.color.colorPrimary);
                } else {
                    colorID = mContext.getResources().getColor(R.color.bg_color_orange_ff6633);
                }
                helper.getView(R.id.iv_qr).setBackground(createColorShape(colorID, 20, 20, 20, 20));
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
