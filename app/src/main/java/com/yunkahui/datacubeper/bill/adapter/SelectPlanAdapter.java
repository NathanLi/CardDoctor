package com.yunkahui.datacubeper.bill.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.HomeItem;

import java.util.List;

/**
 * Created by pc1994 on 2018/3/23
 */
public class SelectPlanAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {

    public SelectPlanAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        helper.setImageResource(R.id.image, item.getImgRes());
        helper.setText(R.id.title, item.getTitle());
    }

}
