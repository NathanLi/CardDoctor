package com.yunkahui.datacubeper.home.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.CardDoctorApplication;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.SingleRecord;

public class SingleRecordActivity extends AppCompatActivity implements IActivityStatusBar {

    public static final String TYPE_RECHARGE = "recharge";
    public static final String TYPE_WITHDRAW = "withdraw";
    public static final String TYPE_WITHDRAW_FENRUNS = "withdraw_fenruns";


    private TextView mTvType;
    private TextView mTvMoney;
    private TextView mTvTime;
    private TextView mTvStatus;
    private TextView mTvRemarks;
    private TextView mTvFee;
    private ImageView mIvIcon;

    @Override
    public void initData() {
        SingleRecord singleRecord = getIntent().getParcelableExtra("info");
        if(singleRecord==null){
            return;
        }
        mIvIcon.setBackgroundResource(Double.parseDouble(singleRecord.getMoney()) > 0 ? R.mipmap.ic_trade_detail : R.mipmap.ic_withdraw);
        mTvType.setText(singleRecord.getAction());
        String amount = singleRecord.getMoney().startsWith("+") || singleRecord.getMoney().startsWith("-") ? singleRecord.getMoney().substring(1) : singleRecord.getMoney();
        mTvMoney.setText(String.format(CardDoctorApplication.getContext().getString(R.string.amount_format), amount));
        mTvTime.setText(String.format(getResources().getString(R.string.trade_time_format), singleRecord.getTime()));
        String status = String.format(getResources().getString(R.string.trade_status_format), singleRecord.getStatus());
        String remarks = singleRecord.getRemarks();
        String remarksSub = TextUtils.isEmpty(remarks) ? "-" : remarks.substring(remarks.lastIndexOf("：") + 1);
        mTvRemarks.setText("备        注：" + remarksSub);
        if (!TextUtils.isEmpty(remarks)) {
            if (remarks.contains("失败"))
                mTvRemarks.setTextColor(Color.RED);
        }
        if(!TextUtils.isEmpty(status)){
            mTvStatus.setText(status);
            if (status.contains("失败"))
                mTvStatus.setTextColor(Color.RED);
        }
        String fee = singleRecord.getFee();
        mTvFee.setText("手  续  费：" + (TextUtils.isEmpty(fee) ? "0.00" : fee));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_single_record);
        super.onCreate(savedInstanceState);
        setTitle("交易详情");
    }

    @Override
    public void initView() {
        mIvIcon = findViewById(R.id.iv_icon);
        mTvType = findViewById(R.id.tv_type);
        mTvMoney = findViewById(R.id.tv_money);
        mTvTime = findViewById(R.id.tv_time);
        mTvStatus = findViewById(R.id.tv_status);
        mTvRemarks = findViewById(R.id.tv_remarks);
        mTvFee = findViewById(R.id.tv_fee);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
