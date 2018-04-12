package com.yunkahui.datacubeper.bill.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.BillCardItem;
import com.yunkahui.datacubeper.common.bean.MineItem;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27
 */

public class BillCardListAdapter extends BaseQuickAdapter<BillCardItem, BaseViewHolder> {

    public BillCardListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillCardItem item) {
        //helper.setBackgroundRes(R.id.iv_card_icon, item.get());
        helper.setText(R.id.tv_bank_name, item.getBankName());
        helper.setText(R.id.tv_card_id, item.getCardId());
        helper.setText(R.id.tv_unrepay_money, item.getUnrepayMoney());
        helper.setText(R.id.tv_leave_date, item.getLeaveDate());
        helper.setText(R.id.tv_repay_date, item.getRepayDate());
    }

}

