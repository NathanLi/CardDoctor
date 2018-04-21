package com.yunkahui.datacubeper.bill.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.TimeItem;
import com.yunkahui.datacubeper.common.bean.TimeHeader;

import java.util.List;

public class TimePickerAdapter extends BaseSectionQuickAdapter<TimeHeader, BaseViewHolder> {

    public TimePickerAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final TimeHeader item) {
        helper.setText(R.id.tv_title, item.header);
    }


    @Override
    protected void convert(BaseViewHolder helper, TimeHeader item) {
        TimeItem timeItem = item.t;
        if (timeItem.getDay() != 0)
            helper.setText(R.id.tv_day, String.valueOf(timeItem.getDay()));
        if (timeItem.isSelected()) {
            helper.setVisible(R.id.iv_circle, true);
            helper.setTextColor(R.id.tv_day, Color.WHITE);
        } else {
            helper.setTextColor(R.id.tv_day, Color.BLACK);
        }
    }
}
