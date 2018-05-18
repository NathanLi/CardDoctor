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
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.utils.TintUtils;

import java.text.DecimalFormat;
import java.util.List;

public class ExpandableBillDeatailAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private List<MultiItemEntity> data;

    private Context mContext;

    private DecimalFormat df = new DecimalFormat("#.00");

    public ExpandableBillDeatailAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        this.data = data;
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
                helper.setText(R.id.tv_month, summary.getMsg());
                if ("未出账单".equals(summary.getMsg())) {
                    helper.setTextColor(R.id.tv_month, mContext.getResources().getColor(R.color.text_color_orange_ff5c03));
                } else {
                    helper.setTextColor(R.id.tv_month, Color.BLACK);
                }
                helper.setText(R.id.tv_year, summary.getYear())
                        .setText(R.id.tv_date, String.format(mContext.getResources().getString(R.string.date_format), summary.getStartDate(), summary.getEndDate()));
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (summary.isExpanded()) {
                            if (data.size() > 1)
                                collapse(helper.getAdapterPosition());
                        } else {
                            if (data.size() > 1)
                                expand(helper.getAdapterPosition());
                        }
                        summary.setExpanded(!summary.isExpanded());
                        helper.setImageResource(R.id.iv_icon, summary.isExpanded() ? R.mipmap.ic_drop : R.mipmap.ic_pull);
                    }
                });
                break;
            case TYPE_LEVEL_1:
                BillDetailItem item = (BillDetailItem) multiItemEntity;
                boolean isRepay = item.getMsg().contains("还款");
                LogUtils.e("test: "+item.getMsg()+"-"+isRepay);
                helper.setText(R.id.tv_mess, (isRepay ? "还款 - " : "消费 - ") + item.getMsg());
                helper.setText(R.id.show_time, item.getTime().substring(5)+" 00:00");
                String money = df.format(item.getMoney());
                int colorID;
                if (isRepay) {
                    money = "-" + df.format(item.getMoney());
                    colorID = mContext.getResources().getColor(R.color.text_color_green_469705);
                } else {
                    colorID = mContext.getResources().getColor(R.color.bg_color_orange_ff6633);
                }
                helper.setText(R.id.show_money, money);
                helper.getView(R.id.iv_qr).setBackground(TintUtils.createColorShape(colorID, 20, 20, 20, 20));
                break;
        }
    }
}
