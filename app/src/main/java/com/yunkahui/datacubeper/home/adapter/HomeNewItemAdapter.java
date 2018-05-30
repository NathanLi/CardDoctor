package com.yunkahui.datacubeper.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.HomeItem;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class HomeNewItemAdapter extends BaseQuickAdapter<HomeItem,BaseViewHolder>{


    public HomeNewItemAdapter(int layoutResId, @Nullable List<HomeItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        helper.setImageResource(R.id.image_view_icon,item.getIcon());
        helper.setText(R.id.text_view_title,item.getTitle());
    }
}
