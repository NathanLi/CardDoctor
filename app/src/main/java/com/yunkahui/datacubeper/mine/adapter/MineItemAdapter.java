package com.yunkahui.datacubeper.mine.adapter;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.MineItem;

import java.util.List;

/**
 * Created by YD1 on 2018/4/3
 */
public class MineItemAdapter extends BaseQuickAdapter<MineItem, BaseViewHolder> {

    public MineItemAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    String TAG = "mineitmtest";
    @Override
    protected void convert(BaseViewHolder helper, MineItem item) {
        Log.e(TAG, "convert: "+item.getTitle());

        if(item.isShow()){
            helper.getView(R.id.head_view).setVisibility(View.VISIBLE);
            helper.getView(R.id.view_line).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.head_view).setVisibility(View.GONE);
            helper.getView(R.id.view_line).setVisibility(View.VISIBLE);
        }
        helper.setBackgroundRes(R.id.iv_icon, item.getIcon());
        helper.setText(R.id.tv_title, item.getTitle());
        if (item.getDetail() != null)
            helper.setText(R.id.tv_detail, item.getDetail());
    }

}
