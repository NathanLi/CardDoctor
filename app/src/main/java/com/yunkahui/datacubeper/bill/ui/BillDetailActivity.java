package com.yunkahui.datacubeper.bill.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.adapter.ExpandableBillDeatailAdapter;
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
import java.util.TimeZone;

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
    private TextView mTvSign;
    private List<MultiItemEntity> mList;
    private BillDetailSummary mSummary;
    private int mCardId;
    private long mBillDate;
    private long mLastTime;
    private ExpandableBillDeatailAdapter mAdapter;
    private boolean mIsRepaid;

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
        mAdapter = new ExpandableBillDeatailAdapter(this, mList);
        mAdapter.bindToRecyclerView(mRecyclerView);
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_list_header_bill_detail, null);
        initHeaderView(headerView);
        mAdapter.setHeaderView(headerView);
        mAdapter.expandAll();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void initHeaderView(View headerView) {
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
        tvRepay.setText("还款日：" + getIntent().getStringExtra("reday_date"));
        tvAccount.setText("账单日：" + TimeUtils.format("MM-dd", mBillDate));
    }

    private void getBillDetailData(int billDay) {
        Calendar c = TimeUtils.getCalendar(System.currentTimeMillis());
        String currentBillDate = c.get(Calendar.YEAR) + "-" + TimeUtils.addZero(c.get(Calendar.MONTH) + 1) + "-" + TimeUtils.addZero(billDay);
        Log.e(TAG, "currentBillDate 1: " + currentBillDate);
        final Calendar calendar = TimeUtils.getCalendar(TimeUtils.getDateToLong("yyyy-MM-dd", currentBillDate));
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Log.e(TAG, "currentBillDate 2: " + (calendar.get(Calendar.MONTH) + 1) + ", " + calendar.get(Calendar.DAY_OF_MONTH));
        mLogic.getBillDetail(this, mCardId, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                try {
                    Log.e(TAG, "result: " + baseBean.getJsonObject().toString());
                    JSONObject jsonObject = baseBean.getJsonObject();
                    JSONObject respData = jsonObject.optJSONObject("respData");
                    JSONArray billUnoutArr = respData.optJSONArray("bill_unout");
                    JSONArray billOutArr = respData.optJSONArray("bill_out");
                    mSummary = new BillDetailSummary();
                    mSummary.setMess("未出账单");
                    mSummary.setYear(calendar.get(Calendar.YEAR));
                    mSummary.setEndMonth(calendar.get(Calendar.MONTH) + 1);
                    mSummary.setEndDay(calendar.get(Calendar.DAY_OF_MONTH));
                    Log.e(TAG, "未出账单 end date: " + (calendar.get(Calendar.MONTH) + 1) + ", " + calendar.get(Calendar.DAY_OF_MONTH));
                    calendar.add(Calendar.MONTH, -1);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    mSummary.setStartMonth(calendar.get(Calendar.MONTH) + 1);
                    mSummary.setStartDay(calendar.get(Calendar.DAY_OF_MONTH));
                    Log.e(TAG, "未出账单 start date: " + (calendar.get(Calendar.MONTH) + 1) + ", " + calendar.get(Calendar.DAY_OF_MONTH));
                    mList.add(mSummary);
                    calendar.add(Calendar.HOUR, -8);
                    mLastTime = calendar.getTimeInMillis();
                    Log.e(TAG, "last time be set1: " + calendar.getTimeInMillis() + ", " + TimeUtils.format("yyyy-MM-dd", calendar.getTimeInMillis()));
                    Log.e(TAG, "last time be set1: " + (calendar.get(Calendar.MONTH) + 1) + ", " + calendar.get(Calendar.DAY_OF_MONTH));
                    for (int i = 0; i < billUnoutArr.length(); i++) {
                        BillDetailItem item = CreateBean(billUnoutArr.getJSONObject(i));
                        Log.e(TAG, "未出账单数据: " + TimeUtils.format("yyyy-MM-dd", item.getTrade_date()));
                        mSummary.addSubItem(item);
                    }
                    for (int i = 0; i < billOutArr.length(); i++) {
                        BillDetailItem item = CreateBean(billOutArr.getJSONObject(i));
                        Log.e(TAG, "遍历出账数据ing: " + TimeUtils.format("yyyy-MM-dd", item.getTrade_date()));
                        if (item.getTrade_date() < mLastTime) {
                            mSummary = new BillDetailSummary();
                            mSummary.setYear(calendar.get(Calendar.YEAR));
                            mSummary.setMess(TimeUtils.addZero(calendar.get(Calendar.MONTH) + 1) + "月");
                            mSummary.setEndMonth(calendar.get(Calendar.MONTH) + 1);
                            calendar.add(Calendar.DAY_OF_MONTH, -1);
                            Log.e(TAG, "end date: " + (calendar.get(Calendar.MONTH) + 1) + ", " + calendar.get(Calendar.DAY_OF_MONTH));
                            mSummary.setEndDay(calendar.get(Calendar.DAY_OF_MONTH));
                            calendar.add(Calendar.MONTH, -1);
                            calendar.add(Calendar.DAY_OF_MONTH, 1);
                            Log.e(TAG, "start date: " + (calendar.get(Calendar.MONTH) + 1) + ", " + calendar.get(Calendar.DAY_OF_MONTH));
                            mSummary.setStartMonth(calendar.get(Calendar.MONTH) + 1);
                            mSummary.setStartDay(calendar.get(Calendar.DAY_OF_MONTH));
                            mList.add(mSummary);
                            Log.e(TAG, "lastTime: " + TimeUtils.format("yyyy-MM-dd", calendar.getTimeInMillis()));
                            mLastTime = calendar.getTimeInMillis();
                            Log.e(TAG, "last time be set: " + mLastTime + ", " + TimeUtils.format("yyyy-MM-dd", calendar.getTimeInMillis()));
                        }
                        mSummary.addSubItem(item);
                    }
                    mAdapter.notifyDataSetChanged();
                    for (MultiItemEntity s : mList) {
                        BillDetailSummary s1 = (BillDetailSummary) s;
                        Log.e(TAG, "onSuccess---------: " + s1.getMess());
                        for (BillDetailItem s2 : s1.getSubItems()) {
                            Log.e(TAG, "onSuccess: " + TimeUtils.format("yyyy-MM-dd", s2.getTrade_date()));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "getBillDetailData onFailure: " + throwable.getMessage());
            }
        });
    }

    private BillDetailItem CreateBean(JSONObject object) {
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
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(BaseBean baseBean) {
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    Log.e(TAG, "getBillDetailTop onSuccess: " + baseBean.getJsonObject().toString());
                    JSONObject jsonObject = baseBean.getJsonObject();
                    JSONObject respData = jsonObject.optJSONObject("respData");
                    mTvLeft.setText(String.valueOf(respData.optInt("distance_day")) + "天后应还");
                    mTvTmp.setText("临时额度：" + String.valueOf(respData.optInt("tmp_line")));
                    mTvFix.setText("固定额度：" + String.valueOf(respData.optInt("fix_line")));
                    mTvBack.setText(String.valueOf(respData.optInt("this_should_repay")));
                    mTvLess.setText("最低应还：-");
                    mIsRepaid = "1".equals(respData.optString("repay_status"));
                    mTvSign.setText(mIsRepaid ? "本期已还清" : "本期未还清");
                    getBillDetailData(Integer.parseInt(respData.optString("bill_day")));
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "getBillDetailTop onFailure: " + throwable.getMessage());
            }
        });
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mTvSign = findViewById(R.id.tv_sign);
        findViewById(R.id.ll_update).setOnClickListener(this);
        findViewById(R.id.ll_sign_repay).setOnClickListener(this);
        findViewById(R.id.btn_add_trade).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_update:
                startActivity(new Intent(this, BillSyncActivity.class));
                break;
            case R.id.ll_sign_repay:
                mLogic.signRepaid(this, mCardId, mIsRepaid ? 0 : 1, new SimpleCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                            mIsRepaid = !mIsRepaid;
                            mTvSign.setText(mIsRepaid ? "标记已还清" : "标记未还清");
                            Toast.makeText(BillDetailActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(TAG, "signRepaid onFailure: " + throwable.getMessage());
                    }
                });
                break;
            case R.id.btn_add_trade:
                startActivity(new Intent(this, AddTradeActivity.class)
                        .putExtra("user_credit_card_id", getIntent().getIntExtra("user_credit_card_id", 0))
                        .putExtra("card_num", getIntent().getStringExtra("card_num")));
                break;
        }
    }
}
