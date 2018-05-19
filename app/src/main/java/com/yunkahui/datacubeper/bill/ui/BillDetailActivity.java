package com.yunkahui.datacubeper.bill.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yanzhenjie.loading.LoadingView;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.adapter.ExpandableBillDeatailAdapter;
import com.yunkahui.datacubeper.bill.logic.BillDetailLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.BillDetailSummary;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author WYF on 2018/4/23/023.
 */
public class BillDetailActivity extends AppCompatActivity implements IActivityStatusBar, View.OnClickListener {

    private final int RESULT_CODE_UPDATE = 1001;

    private RecyclerView mRecyclerView;
    private TextView mTvLeft;
    private TextView mTvBack;
    private TextView mTvLess;
    private TextView mTvTmp;
    private TextView mTvFix;
    private TextView mTvSign;
    private TextView mTvRepay;
    private TextView mTvAccount;

    private BillDetailLogic mLogic;
    private ExpandableBillDeatailAdapter mAdapter;
    private List<MultiItemEntity> mList;
    private int mCardId;
    private boolean mIsRepaid;
    private int mBillDay;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void initData() {
        mCardId = getIntent().getIntExtra("user_credit_card_id", 0);
        mLogic = new BillDetailLogic();
        mList = new ArrayList<>();
        mAdapter = new ExpandableBillDeatailAdapter(this, mList);
        mAdapter.bindToRecyclerView(mRecyclerView);
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_list_header_bill_detail, null);
        initHeaderView(headerView);
        mAdapter.setHeaderView(headerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.expandAll();
        getBillDetailTop();
        getCardDetailData();
    }

    //******** 获取爬虫数据失败，显示默认item ********
    private void addEmptyItem() {
        Calendar currentCalendar = TimeUtils.getCalendar(System.currentTimeMillis());
        boolean isUpperHalfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH) < mBillDay;
        BillDetailSummary unsettledSummary = new BillDetailSummary();
        unsettledSummary.setMsg("未出账单");
        unsettledSummary.setYear(String.valueOf(currentCalendar.get(Calendar.YEAR)));
        unsettledSummary.setStartDate(currentCalendar.get(Calendar.MONTH) + (isUpperHalfMonth ? 0 : 1) + "-" + mBillDay);
        unsettledSummary.setEndDate(currentCalendar.get(Calendar.MONTH) + (isUpperHalfMonth ? 1 : 2) + "-" + (mBillDay - 1));
        mList.add(0, unsettledSummary);
    }

    @SuppressLint("SetTextI18n")
    private void initHeaderView(View headerView) {
        mTvLeft = headerView.findViewById(R.id.tv_left);
        mTvBack = headerView.findViewById(R.id.tv_back);
        mTvLess = headerView.findViewById(R.id.tv_less);
        mTvTmp = headerView.findViewById(R.id.tv_tmp);
        mTvFix = headerView.findViewById(R.id.tv_fix);
        headerView.findViewById(R.id.show_edit).setOnClickListener(this);
        String cardNum = getIntent().getStringExtra("bank_card_num");
        String cardHolder = getIntent().getStringExtra("card_holder");
        ((TextView) headerView.findViewById(R.id.tv_mess))
                .setText("- - - - - - - - " + cardNum.substring(cardNum.length() - 4, cardNum.length()) + cardHolder);
        mTvRepay = headerView.findViewById(R.id.tv_repay);
        mTvRepay.setText("还款日：" + getIntent().getStringExtra("reday_date"));
        mTvAccount = headerView.findViewById(R.id.tv_account);
        mTvAccount.setText("账单日：" + TimeUtils.format("MM-dd", getIntent().getLongExtra("bill_date", 0)));
    }

    //******** 获取交易详情 ********
    private void loadTradeHistory() {
        LoadingViewDialog.getInstance().show(BillDetailActivity.this);
        mLogic.loadTradeHistory(this, getIntent().getStringExtra("bank_card_num"), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("交易详情->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mList.clear();
                    mList.addAll(mLogic.handleData(mBillDay, baseBean));
                } else {
                    Toast.makeText(BillDetailActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                    addEmptyItem();
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("获取交易详情失败->" + throwable.getMessage());
                Toast.makeText(BillDetailActivity.this, "获取交易详情失败->" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                addEmptyItem();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    //获取当前卡片详情数据
    private void getCardDetailData() {
        mLogic.loadCardDeatailData(this, mCardId, new SimpleCallBack<BaseBean<BillCreditCard>>() {
            @Override
            public void onSuccess(BaseBean<BillCreditCard> billCreditCardBaseBean) {
                LogUtils.e("卡片详情数据->" + billCreditCardBaseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(billCreditCardBaseBean.getRespCode())) {
                    BillCreditCard.CreditCard creditCard = billCreditCardBaseBean.getRespData().getCardDetail().get(0);
                    mTvRepay.setText("还款日：" + TimeUtils.format("MM-dd",  creditCard.getRepayDayDate()));
                    mTvAccount.setText("账单日：" + TimeUtils.format("MM-dd", creditCard.getBillDayDate()));
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                ToastUtils.show(getApplicationContext(), throwable.toString());
            }
        });
    }

    //******** 获取账单头部信息 ********
    private void getBillDetailTop() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.getBillDetailTop(this, mCardId, new SimpleCallBack<BaseBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("账单头部->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    JSONObject jsonObject = baseBean.getJsonObject();
                    JSONObject respData = jsonObject.optJSONObject("respData");
                    mTvLeft.setText(String.valueOf(respData.optInt("distance_day")) + "天后应还");
                    mTvTmp.setText("临时额度：" + String.valueOf(respData.optInt("tmp_line")));
                    mTvFix.setText("固定额度：" + String.valueOf(respData.optInt("fix_line")));
                    mTvBack.setText(String.valueOf(respData.optInt("this_should_repay")));
                    mTvLess.setText("最低应还：-");
                    mIsRepaid = "1".equals(respData.optString("repay_status"));
                    mTvSign.setText(mIsRepaid ? "本期已还清" : "本期未还清");
                    mBillDay = Integer.parseInt(respData.optString("bill_day"));
                    loadTradeHistory();
                } else {
                    Toast.makeText(BillDetailActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                    addEmptyItem();
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Toast.makeText(BillDetailActivity.this, "获取账单头部信息失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.e("我知道你回来了 "+resultCode);
        if (resultCode == RESULT_OK && requestCode == RESULT_CODE_UPDATE) {
            getBillDetailTop();
            getCardDetailData();
        } else if (resultCode == BillSynchronousActivity.TYPE_SPIDER_COMPLETE) {
            LogUtils.e("返回银行数据->");
            loadTradeHistory();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_edit:
                Intent intent = new Intent(this, AddCardActivity.class);
                intent.putExtra("type", AddCardActivity.TYPE_EDIT2);
                intent.putExtra("card_id", mCardId);
                intent.putExtra("card_number", getIntent().getStringExtra("bank_card_num"));
                intent.putExtra("bank_card_name", getIntent().getStringExtra("bank_card_name"));
                startActivityForResult(intent, RESULT_CODE_UPDATE);
                break;
            case R.id.ll_update:
                ArrayList<String> tabs = new ArrayList<>();
                tabs.add("用户名");
                tabs.add("卡号");
                Intent intent1 = new Intent(this, BillSynchronousActivity.class)
                        .putExtra("title", getIntent().getStringExtra("title"))
                        .putExtra("bank_card_num", getIntent().getStringExtra("bank_card_num"))
                        .putExtra("bank_card_name", getIntent().getStringExtra("bank_card_name"))
                        .putStringArrayListExtra("tabs", tabs);
                startActivityForResult(intent1, 1);
                break;
            case R.id.ll_sign_repay:
                signRepay();
                break;
            case R.id.btn_add_trade:
                startActivity(new Intent(this, AddTradeActivity.class)
                        .putExtra("user_credit_card_id", getIntent().getIntExtra("user_credit_card_id", 0))
                        .putExtra("card_num", getIntent().getStringExtra("card_num")));
                break;
        }
    }

    //******** 标记还清 ********
    private void signRepay() {
        mLogic.signRepay(this, mCardId, mIsRepaid ? 0 : 1, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("标记->" + baseBean.getJsonObject().toString());
                Toast.makeText(BillDetailActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mIsRepaid = !mIsRepaid;
                    mTvSign.setText(mIsRepaid ? "标记已还清" : "标记未还清");
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(BillDetailActivity.this, "标记失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bill_detail);
        super.onCreate(savedInstanceState);
        setTitle("交易管理");
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
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    private String data = "{\n" +
            "\t\"result\": \"ok\",\n" +
            "\t\"settled\": [{\n" +
            "\t\t\"bm_month\": \"2017-11\",\n" +
            "\t\t\"bm_usable_limit\": 0.0,\n" +
            "\t\t\"bm_repay_sum\": 0.0,\n" +
            "\t\t\"bm_repay_min\": 0.0,\n" +
            "\t\t\"bm_prior_sum\": 0.0,\n" +
            "\t\t\"bm_prior_repay\": 0.0,\n" +
            "\t\t\"bm_current_sum\": 0.0,\n" +
            "\t\t\"bm_integral_sum\": 0.0,\n" +
            "\t\t\"bm_integral_increment\": 0.0,\n" +
            "\t\t\"o_details\": [{\n" +
            "\t\t\t\"bd_date\": \"2017-10-12\",\n" +
            "\t\t\t\"bd_description\": \"已收妥您的款项\",\n" +
            "\t\t\t\"bd_money\": -1999.0\n" +
            "\t\t}]\n" +
            "\t}, {\n" +
            "\t\t\"bm_month\": \"2017-12\",\n" +
            "\t\t\"bm_usable_limit\": 3000.0,\n" +
            "\t\t\"bm_repay_sum\": 1513.9,\n" +
            "\t\t\"bm_repay_min\": 75.7,\n" +
            "\t\t\"bm_prior_sum\": 815.36,\n" +
            "\t\t\"bm_prior_repay\": 816.0,\n" +
            "\t\t\"bm_current_sum\": 1514.54,\n" +
            "\t\t\"bm_integral_sum\": 5801.0,\n" +
            "\t\t\"bm_integral_increment\": 0.0,\n" +
            "\t\t\"o_details\": [{\n" +
            "\t\t\t\"bd_date\": \"2017-11-11\",\n" +
            "\t\t\t\"bd_description\": \"(特约)北京金色世纪广东省\",\n" +
            "\t\t\t\"bd_money\": 128.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2017-11-11\",\n" +
            "\t\t\t\"bd_description\": \"(特约)北京金色世纪广东省\",\n" +
            "\t\t\t\"bd_money\": 509.0\n" +
            "\t\t}]\n" +
            "\t}, {\n" +
            "\t\t\"bm_month\": \"2018-01\",\n" +
            "\t\t\"bm_usable_limit\": 3000.0,\n" +
            "\t\t\"bm_repay_sum\": 578.5,\n" +
            "\t\t\"bm_repay_min\": 28.93,\n" +
            "\t\t\"bm_prior_sum\": 1513.9,\n" +
            "\t\t\"bm_prior_repay\": 2513.9,\n" +
            "\t\t\"bm_current_sum\": 1578.5,\n" +
            "\t\t\"bm_integral_sum\": 5801.0,\n" +
            "\t\t\"bm_integral_increment\": 0.0,\n" +
            "\t\t\"o_details\": [{\n" +
            "\t\t\t\"bd_date\": \"2017-12-19\",\n" +
            "\t\t\t\"bd_description\": \"(特约)腾邦国际深圳市\",\n" +
            "\t\t\t\"bd_money\": 508.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2017-12-20\",\n" +
            "\t\t\t\"bd_description\": \"已收妥您的款项\",\n" +
            "\t\t\t\"bd_money\": -1514.0\n" +
            "\t\t}]\n" +
            "\t}, {\n" +
            "\t\t\"bm_month\": \"2018-02\",\n" +
            "\t\t\"bm_usable_limit\": 3000.0,\n" +
            "\t\t\"bm_repay_sum\": 2485.5,\n" +
            "\t\t\"bm_repay_min\": 124.28,\n" +
            "\t\t\"bm_prior_sum\": 578.5,\n" +
            "\t\t\"bm_prior_repay\": 579.0,\n" +
            "\t\t\"bm_current_sum\": 2486.0,\n" +
            "\t\t\"bm_integral_sum\": 6801.0,\n" +
            "\t\t\"bm_integral_increment\": 1000.0,\n" +
            "\t\t\"o_details\": [{\n" +
            "\t\t\t\"bd_date\": \"2018-01-04\",\n" +
            "\t\t\t\"bd_description\": \"(特约)腾邦国际深圳市\",\n" +
            "\t\t\t\"bd_money\": 98.0\n" +
            "\t\t}]\n" +
            "\t}, {\n" +
            "\t\t\"bm_month\": \"2018-03\",\n" +
            "\t\t\"bm_usable_limit\": 3000.0,\n" +
            "\t\t\"bm_repay_sum\": 2557.58,\n" +
            "\t\t\"bm_repay_min\": 831.6,\n" +
            "\t\t\"bm_prior_sum\": 2485.5,\n" +
            "\t\t\"bm_prior_repay\": 0.0,\n" +
            "\t\t\"bm_current_sum\": 72.08,\n" +
            "\t\t\"bm_integral_sum\": 6801.0,\n" +
            "\t\t\"bm_integral_increment\": 0.0,\n" +
            "\t\t\"o_details\": [{\n" +
            "\t\t\t\"bd_date\": \"2018-02-09\",\n" +
            "\t\t\t\"bd_description\": \"账单分期(分期)2,485.50\",\n" +
            "\t\t\t\"bd_money\": 0.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-09\",\n" +
            "\t\t\t\"bd_description\": \"分期付款手续费\",\n" +
            "\t\t\t\"bd_money\": 72.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-01\",\n" +
            "\t\t\t\"bd_description\": \"3月份账单账单分期3期:本期应还款828.00,余额1,657.50,余期为02期\",\n" +
            "\t\t\t\"bd_money\": 0.0\n" +
            "\t\t}]\n" +
            "\t}, {\n" +
            "\t\t\"bm_month\": \"2018-04\",\n" +
            "\t\t\"bm_usable_limit\": 3000.0,\n" +
            "\t\t\"bm_repay_sum\": 2357.5,\n" +
            "\t\t\"bm_repay_min\": 863.0,\n" +
            "\t\t\"bm_prior_sum\": 2557.58,\n" +
            "\t\t\"bm_prior_repay\": 900.08,\n" +
            "\t\t\"bm_current_sum\": 700.0,\n" +
            "\t\t\"bm_integral_sum\": 7501.0,\n" +
            "\t\t\"bm_integral_increment\": 700.0,\n" +
            "\t\t\"o_details\": [{\n" +
            "\t\t\t\"bd_date\": \"2018-03-09\",\n" +
            "\t\t\t\"bd_description\": \"广东云返票务有限公广州市\",\n" +
            "\t\t\t\"bd_money\": 200.0\n" +
            "\t\t}]\n" +
            "\t}, {\n" +
            "\t\t\"bm_month\": \"2018-05\",\n" +
            "\t\t\"bm_usable_limit\": 3000.0,\n" +
            "\t\t\"bm_repay_sum\": 2509.5,\n" +
            "\t\t\"bm_repay_min\": 913.5,\n" +
            "\t\t\"bm_prior_sum\": 2357.5,\n" +
            "\t\t\"bm_prior_repay\": 1528.0,\n" +
            "\t\t\"bm_current_sum\": 1680.0,\n" +
            "\t\t\"bm_integral_sum\": 9181.0,\n" +
            "\t\t\"bm_integral_increment\": 1680.0,\n" +
            "\t\t\"o_details\": [{\n" +
            "\t\t\t\"bd_date\": \"2018-04-13\",\n" +
            "\t\t\t\"bd_description\": \"光大互助会\",\n" +
            "\t\t\t\"bd_money\": 180.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-04-18\",\n" +
            "\t\t\t\"bd_description\": \"已收妥您的款项\",\n" +
            "\t\t\t\"bd_money\": -1528.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-04-27\",\n" +
            "\t\t\t\"bd_description\": \"广州市玳盟德珠宝店广州市\",\n" +
            "\t\t\t\"bd_money\": 1500.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-05-01\",\n" +
            "\t\t\t\"bd_description\": \"3月份账单账单分期3期:本期应还款829.50,余额0.00,余期为00期\",\n" +
            "\t\t\t\"bd_money\": 0.0\n" +
            "\t\t}]\n" +
            "\t}],\n" +
            "\t\"unsettled\": [],\n" +
            "\t\"_req_id\": \"2713375e1aa348b3ba89e71d67336a57\"\n" +
            "}";
}
