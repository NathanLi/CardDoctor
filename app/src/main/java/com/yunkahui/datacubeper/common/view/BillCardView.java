package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

public class BillCardView extends LinearLayout implements View.OnClickListener {

    private ImageView mIvSample;
    private TextView mTvShouldRepay;
    private ImageView mIvCardIcon;
    private TextView mTvLimit;
    private TextView mTvBankName;
    private TextView mTvCardId;
    private TextView mTvShouldRepayAmount;
    private TextView mTvLeaveDate;
    private TextView mTvRepayDate;
    private TextView mTvBillAmount;
    private TextView mTvUnRepayAmount;
    private TextView mTvFixedAmount;
    private TextView mTvBillCycle;
    private ImageView mIvArrowCover;
    private ImageView mIvArrowExtend;
    private LinearLayout mLayoutExtend;
    private OnClickSmartPlanListener onClickSmartPlanListener;
    private TextView mTvSmartPlan;
    private OnClickBillCardListener l;

    public BillCardView(Context context) {
        this(context, null);
    }

    public BillCardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BillCardView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_bill_card, this);
        mIvCardIcon = view.findViewById(R.id.iv_bank_icon);
        mTvBankName = view.findViewById(R.id.tv_bank_name);
        mTvCardId = view.findViewById(R.id.tv_bank_card_id);
        TextView btnBillSync = view.findViewById(R.id.btn_bill_sync);
        mTvShouldRepayAmount = view.findViewById(R.id.tv_should_repay_amount);
        mTvLeaveDate = view.findViewById(R.id.tv_leave_date);
        mTvRepayDate = view.findViewById(R.id.tv_repay_date);
        mTvSmartPlan = view.findViewById(R.id.tv_smart_plan);
        mIvArrowExtend = view.findViewById(R.id.iv_arrow_extend);
        mTvBillAmount = view.findViewById(R.id.tv_bill_amount);
        mTvUnRepayAmount = view.findViewById(R.id.tv_unrepay_amount);
        mTvFixedAmount = view.findViewById(R.id.tv_fixed_amount);
        mTvBillCycle = view.findViewById(R.id.tv_bill_cycle);
        mIvArrowCover = view.findViewById(R.id.iv_arrow_cover);
        mLayoutExtend = view.findViewById(R.id.layout_extend);
        mTvLimit = view.findViewById(R.id.tv_limit);
        mTvShouldRepay = view.findViewById(R.id.tv_should_repay);
        mIvSample = view.findViewById(R.id.iv_sample);

        findViewById(R.id.layout_card_item).setOnClickListener(this);
        btnBillSync.setOnClickListener(this);
        mTvSmartPlan.setOnClickListener(this);
        mIvArrowExtend.setOnClickListener(this);
        mIvArrowCover.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_card_item:
                l.onClickBillCard();
            case R.id.btn_bill_sync:
                l.onClickBillSync();
                break;
            case R.id.tv_smart_plan:
                onClickSmartPlanListener.onClickSmartPlan();
                break;
            case R.id.iv_arrow_extend:
                mIvArrowExtend.setVisibility(INVISIBLE);
                mLayoutExtend.setVisibility(VISIBLE);
                break;
            case R.id.iv_arrow_cover:
                mLayoutExtend.setVisibility(GONE);
                mIvArrowExtend.setVisibility(VISIBLE);
                break;
        }
    }

    public boolean isSmartPlanVisible() {
        return mTvSmartPlan.getVisibility() == VISIBLE;
    }

    public void setSmartPlanVisibility(int visibility) {
        mTvSmartPlan.setVisibility(visibility);
    }

    public boolean isSampleVisible() {
        return mIvSample.getVisibility() == VISIBLE;
    }

    public void setSampleVisibility(int visibility) {
        mIvSample.setVisibility(visibility);
    }

    public void setShouldRepay(String s) {
        mTvShouldRepay.setText(s);
    }

    public void setLimit(String limit) {
        mTvLimit.setText(limit);
    }

    public void setIcon(int res) {
        mIvCardIcon.setBackgroundResource(res);
    }

    public void setBankName(String name) {
        if (!TextUtils.isEmpty(name)) {
            mTvBankName.setText(name);
        }
    }

    public void setCardId(String id) {
        if (!TextUtils.isEmpty(id)) {
            mTvCardId.setText(id);
        }
    }

    public void setShouldRepayAmount(String amount) {
        if (!TextUtils.isEmpty(amount)) {
            mTvShouldRepayAmount.setText(amount);
        }
    }

    public void setLeaveDate(String date) {
        if (!TextUtils.isEmpty(date)) {
            mTvLeaveDate.setText(date);
        }
    }

    public void setRepayDate(String date) {
        if (!TextUtils.isEmpty(date)) {
            mTvRepayDate.setText(date);
        }
    }

    public void setBillAmount(String amount) {
        if (!TextUtils.isEmpty(amount)) {
            mTvBillAmount.setText(amount);
        }
    }

    public void setUnrepayAmount(String amount) {
        if (!TextUtils.isEmpty(amount)) {
            mTvUnRepayAmount.setText(amount);
        }
    }

    public void setFixedAmount(String amount) {
        if (!TextUtils.isEmpty(amount)) {
            mTvFixedAmount.setText(amount);
        }
    }

    public void setBillCycle(String cycle) {
        if (!TextUtils.isEmpty(cycle)) {
            mTvBillCycle.setText(cycle);
        }
    }

    public void setOnClickSmartPlanListener(OnClickSmartPlanListener l) {
        this.onClickSmartPlanListener = l;
    }

    public interface OnClickSmartPlanListener {
        void onClickSmartPlan();
    }

    public interface OnClickBillCardListener {
        void onClickBillSync();
        void onClickBillCard();
    }

    public void setOnClickBillCardListener(OnClickBillCardListener l) {
        this.l = l;
    }

    public abstract static class CustomBillCardListenr implements OnClickBillCardListener {
        @Override
        public void onClickBillCard() {

        }

        @Override
        public void onClickBillSync() {

        }
    }
}
