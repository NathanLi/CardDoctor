package com.yunkahui.datacubeper.home.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.bean.TradeRecordSummary;

import java.util.List;

public class ExpandableTradeRecordAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public ExpandableTradeRecordAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.layout_list_header_trade_record_summary);
        addItemType(TYPE_LEVEL_1, R.layout.layout_list_item_trade_record_detail);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final TradeRecordSummary lv0 = (TradeRecordSummary) item;
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
                break;
        }
    }
}
