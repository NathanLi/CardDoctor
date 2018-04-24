package com.yunkahui.datacubeper.bill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.logic.BillDetailLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillDetailItem;
import com.yunkahui.datacubeper.common.bean.BillDetailSummary;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author WYF on 2018/4/23/023.
 */
public class BillDetailActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private static final String TAG = "BillDetailActivity";
    private RecyclerView mRecyclerView;
    private BillDetailLogic mLogic;
    private TextView mTvLeft;
    private TextView mTvBack;
    private TextView mTvLess;
    private TextView mTvTmp;
    private TextView mTvFix;
    private List<BillDetailSummary> mList;
    private int mCardId;
    private long mBillDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bill_detail);
        super.onCreate(savedInstanceState);
        setTitle("交易管理");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        mCardId = getIntent().getIntExtra("user_credit_card_id", 0);
        mBillDate = getIntent().getLongExtra("bill_date", 0);
        mLogic = new BillDetailLogic();
        getBillDetailTop();
        final Calendar calendar = TimeUtils.getCalendar(mBillDate);
        mLogic.getBillDetail(this, mCardId, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                Log.e(TAG, "onSuccess: " + baseBean.getJsonObject().toString());
                try {
                    JSONObject jsonObject = baseBean.getJsonObject();
                    JSONObject respData = jsonObject.optJSONObject("respData");
                    JSONArray billUnoutArr = respData.optJSONArray("bill_unout");
                    JSONArray billOutArr = respData.optJSONArray("bill_out");
                    Log.e(TAG, "onSuccess: " + billUnoutArr.length() + ", " + billOutArr.length());
                    BillDetailSummary summary = new BillDetailSummary();
                    summary.setMess("未出账单");
                    summary.setEndDate(TimeUtils.format("MM-dd", mBillDate));
                    calendar.add(Calendar.MONTH, -1);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    summary.setStartDate(transMonth(calendar.get(Calendar.MONTH)) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
                    mList.add(summary);
                    for (int i = 0; i < billUnoutArr.length(); i++) {
                        summary.addSubItem(CreateBean(billUnoutArr.getJSONObject(i)));
                    }
                    for (int i = 0; i < billOutArr.length(); i++) {
                        CreateBean(billOutArr.getJSONObject(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_list_header_bill_detail, null);
        TextView tvMess = headerView.findViewById(R.id.tv_mess);
        mTvLeft = headerView.findViewById(R.id.tv_left);
        mTvBack = headerView.findViewById(R.id.tv_back);
        mTvLess = headerView.findViewById(R.id.tv_less);
        TextView tvRepay = headerView.findViewById(R.id.tv_repay);
        TextView tvAccount = headerView.findViewById(R.id.tv_account);
        mTvTmp = headerView.findViewById(R.id.tv_tmp);
        mTvFix = headerView.findViewById(R.id.tv_fix);
        String cardNum = getIntent().getStringExtra("card_num");
        tvMess.setText("- - - - - - - - " + cardNum.substring(cardNum.length() - 4, cardNum.length()) + getIntent().getStringExtra("card_holder"));
        tvRepay.setText(getIntent().getStringExtra("reday_date"));
        tvAccount.setText(TimeUtils.format("MM-dd", mBillDate));
    }

    private String transMonth(int month) {
        String s = String.valueOf(month + 1);
        return s.length() == 1 ? "0" + s : s;
    }

    private BillDetailItem CreateBean(JSONObject object) throws JSONException {
        BillDetailItem bean = new BillDetailItem();
        bean.setS_id(object.optInt("s_id"));
        bean.setUser_code(object.optString("user_code"));
        bean.setUser_credit_card_id(object.optInt("user_credit_card_id"));
        bean.setTrade_type(object.optString("trade_type"));
        bean.setTrade_money(object.optInt("trade_money"));
        bean.setTrade_date(object.optLong("trade_date"));
        bean.setBill_month(object.optString("bill_month"));
        bean.setBill_type(object.optString("bill_type"));
        bean.setThis_repay_min(object.optString("this_repay_min"));
        bean.setThis_repay_sum(object.optString("this_repay_sum"));
        bean.setPrior_repay(object.optString("prior_repay"));
        bean.setPrior_sum(object.optString("prior_sum"));
        bean.setThis_bill_sum(object.optString("this_bill_sum"));
        bean.setThis_add_point(object.optString("this_add_point"));
        bean.setOrg_number(object.optString("integral_sum"));
        bean.setCreate_time(object.optLong("create_time"));
        bean.setUpdate_time(object.optLong("update_time"));
        bean.setSummary(object.optString("summary"));
        bean.setOrg_number(object.optString("org_number"));
        return bean;
    }

    private void getBillDetailTop() {
        mLogic.getBillDetailTop(this, mCardId, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    JSONObject jsonObject = baseBean.getJsonObject();
                    JSONObject respData = jsonObject.optJSONObject("respData");
                    mTvLeft.setText(String.valueOf(respData.optInt("distance_day")));
                    mTvTmp.setText(String.valueOf(respData.optInt("tmp_line")));
                    mTvFix.setText(String.valueOf(respData.optInt("fix_line")));
                    mTvBack.setText(String.valueOf(respData.optInt("this_should_repay")));
                    mTvLess.setText("最低应还：-");
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.ll_update).setOnClickListener(this);
        findViewById(R.id.ll_sign_un_repay).setOnClickListener(this);
        findViewById(R.id.btn_add_trade).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_update:
                startActivity(new Intent(this, BillSyncActivity.class));
                break;
            case R.id.ll_sign_un_repay:
                break;
            case R.id.btn_add_trade:
                startActivity(new Intent(this, AddTradeActivity.class));
                break;
        }
    }
}
