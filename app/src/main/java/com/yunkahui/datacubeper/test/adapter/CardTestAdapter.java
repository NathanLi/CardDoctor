package com.yunkahui.datacubeper.test.adapter;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.CardTestItem;
import com.yunkahui.datacubeper.common.bean.MineItem;

import java.util.List;

/**
 * Created by pc1994 on 2018/3/23
 */
public class CardTestAdapter extends BaseQuickAdapter<CardTestItem, BaseViewHolder> {

    public CardTestAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CardTestItem item) {
        helper.setBackgroundRes(R.id.iv_bank_icon, item.getIcon());
        helper.setText(R.id.tv_user_name, item.getUserName());
        helper.setText(R.id.tv_bank_name, item.getBankName());
        Log.e(TAG, "CardTestAdapterTest: "+item.getBankName());
        helper.setText(R.id.tv_bank_id, item.getBankId());
        helper.setText(R.id.tv_card_nick_name, item.getNickName());
        helper.setText(R.id.tv_card_type, item.getCardType());
    }

}
