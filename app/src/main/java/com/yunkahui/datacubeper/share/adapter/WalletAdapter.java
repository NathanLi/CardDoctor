package com.yunkahui.datacubeper.share.adapter;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import com.yunkahui.datacubeper.common.bean.MineItem;

import java.util.List;

/**
 * Created by YD1 on 2018/4/3
 */
public class WalletAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {

    public WalletAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        Log.e("wallet test", "convert: "+item.getImgRes());
        helper.setBackgroundRes(R.id.iv_icon, item.getImgRes());
        helper.setText(R.id.tv_name, item.getTitle());
    }

}
