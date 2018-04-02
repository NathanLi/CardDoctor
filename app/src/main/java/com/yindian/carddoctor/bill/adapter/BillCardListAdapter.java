package com.yindian.carddoctor.bill.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yindian.carddoctor.R;
import com.yindian.carddoctor.common.bean.BillCardItem;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27
 */

public class BillCardListAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_FOOTER = 2;
    private Context mContext;
    private List<BillCardItem> mCardItemList;

    public BillCardListAdapter(Context context, List<BillCardItem> featureList) {
        this.mContext = context;
        this.mCardItemList = featureList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            return new HeadViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_list_item_bill_head, parent, false));
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_list_item_bill_footer, parent, false));
        } else {
            return new CardItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_list_item_bill_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            HeadViewHolder headViewHolder = (HeadViewHolder) holder;
            headViewHolder.operationToday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "today operation", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.addCreditCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "add credit card shortly", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            BillCardItem item = mCardItemList.get(position - 1);

            CardItemViewHolder cardItemViewHolder = (CardItemViewHolder) holder;
            cardItemViewHolder.bankName.setText(item.getBankName());
            cardItemViewHolder.cardId.setText(item.getCardId());
            cardItemViewHolder.billSync.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "账单同步", Toast.LENGTH_SHORT).show();
                }
            });
            cardItemViewHolder.unrepayMoney.setText(String.valueOf(item.getUnrepayMoney()));
            cardItemViewHolder.leaveDate.setText(String.valueOf(item.getLeaveDate()));
            cardItemViewHolder.repayDate.setText(item.getRepayDate());
            cardItemViewHolder.smartPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "智能规划", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position == mCardItemList.size() + 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return mCardItemList.size() + 2;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        private Button addCreditCard;

        public FooterViewHolder(View itemView) {
            super(itemView);
            addCreditCard = itemView.findViewById(R.id.btn_add_credit_card);
        }
    }

    class CardItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView cardIcon;
        private TextView bankName;
        private TextView cardId;
        private Button billSync;
        private TextView unrepayMoney;
        private TextView leaveDate;
        private TextView repayDate;
        private TextView smartPlan;

        public CardItemViewHolder(View itemView) {
            super(itemView);
            cardIcon = itemView.findViewById(R.id.iv_card_icon);
            bankName = itemView.findViewById(R.id.tv_bank_name);
            cardId = itemView.findViewById(R.id.tv_card_id);
            billSync = itemView.findViewById(R.id.btn_bill_sync);
            unrepayMoney = itemView.findViewById(R.id.tv_unrepay_money);
            leaveDate = itemView.findViewById(R.id.tv_leave_date);
            repayDate = itemView.findViewById(R.id.tv_repay_date);
            smartPlan = itemView.findViewById(R.id.tv_smart_plan);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {

        private TextView operationToday;
        private TextView cardCount;
        private TextView repayCount;
        private TextView unrepayCount;

        public HeadViewHolder(View itemView) {
            super(itemView);
            operationToday = itemView.findViewById(R.id.tv_operation_today);
            cardCount = itemView.findViewById(R.id.tv_card_count);
            repayCount = itemView.findViewById(R.id.tv_repay_count);
            unrepayCount = itemView.findViewById(R.id.tv_unrepay_count);
        }
    }
}

