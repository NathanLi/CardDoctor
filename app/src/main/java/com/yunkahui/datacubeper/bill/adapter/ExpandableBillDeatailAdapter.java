package com.yunkahui.datacubeper.bill.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BillDetailItem;
import com.yunkahui.datacubeper.common.bean.BillDetailSummary;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.utils.TintUtil;

import java.util.List;
import java.util.logging.Level;

public class ExpandableBillDeatailAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private Context mContext;

    public ExpandableBillDeatailAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        mContext = context;
        addItemType(TYPE_LEVEL_0, R.layout.layout_list_header_bill_detail_section);
        addItemType(TYPE_LEVEL_1, R.layout.layout_list_item_trade_record_detail);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity multiItemEntity) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final BillDetailSummary summary = (BillDetailSummary) multiItemEntity;
                helper.setText(R.id.tv_month, summary.getMess());
                if ("未出账单".equals(summary.getMess())) {
                    helper.setTextColor(R.id.tv_month, mContext.getResources().getColor(R.color.text_color_orange_ff5c03));
                } else {
                    helper.setTextColor(R.id.tv_month, Color.BLACK);
                }
                helper.setText(R.id.tv_year, summary.getYear())
                        .setText(R.id.tv_date, String.format(mContext.getResources().getString(R.string.date_format), summary.getStartDate(), summary.getEndDate()))
                        .setImageResource(R.id.iv_icon, summary.isExpanded() ? R.mipmap.ic_drop : R.mipmap.ic_pull);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = helper.getAdapterPosition();
                        if (summary.isExpanded()) {
                            collapse(position);
                        } else {
                            expand(position);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                BillDetailItem item = (BillDetailItem) multiItemEntity;
                boolean isExpense = "1".equals(item.getTrade_type());
                helper.setText(R.id.tv_mess, isExpense ? "消费" : "还款");
                helper.setText(R.id.show_time, TimeUtils.format("MM-dd hh:mm", item.getTrade_date()));
                String money = String.valueOf(item.getTrade_money());
                int colorID;
                if (isExpense) {
                    colorID = mContext.getResources().getColor(R.color.bg_color_orange_ff6633);
                } else {
                    money = "-" + item.getTrade_money();
                    colorID = mContext.getResources().getColor(R.color.text_color_green_469705);
                }
                helper.setText(R.id.show_money, money);
                helper.getView(R.id.iv_qr).setBackground(TintUtil.createColorShape(colorID, 20, 20, 20, 20));
                break;
        }
    }
}
