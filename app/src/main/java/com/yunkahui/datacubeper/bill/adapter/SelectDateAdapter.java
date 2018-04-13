package com.yunkahui.datacubeper.bill.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;

import java.util.List;

public class SelectDateAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SelectDateAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_num, item);
    }

}
