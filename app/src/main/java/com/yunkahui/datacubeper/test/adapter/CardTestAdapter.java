package com.yunkahui.datacubeper.test.adapter;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.CardTestItem;
import com.yunkahui.datacubeper.common.bean.MineItem;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;

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

        helper.setBackgroundRes(R.id.iv_bank_icon, DataUtils.getBankIconForName(item.getCard().getBank_name()));
        helper.setText(R.id.tv_user_name, item.getCard().getCardholder());
        helper.setText(R.id.tv_bank_name, item.getCard().getBank_name());
        helper.setText(R.id.tv_bank_id, item.getCard().getBankcard_num());
        helper.setText(R.id.tv_card_nick_name, item.getCard().getCard_brand());
        helper.setText(R.id.tv_card_type, item.getCard().getCard_name());

        helper.addOnClickListener(R.id.text_view_report)
                .addOnClickListener(R.id.btn_run_test);
    }

}
