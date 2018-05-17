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
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.adapter.ExpandableBillDeatailAdapter;
import com.yunkahui.datacubeper.bill.logic.BillDetailLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import org.json.JSONObject;

import java.util.ArrayList;
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
        //getBillDetailTop();
        mList.addAll(mLogic.handleData(19, data));
        mAdapter.notifyDataSetChanged();
        getBillDetailTop();
        getCardDetailData();
        loadTradeHistory();
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
        mLogic.loadTradeHistory(this, getIntent().getStringExtra("bank_card_num"), new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("交易详情->" + baseBean.getRespData().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mList.addAll(mLogic.handleData(mBillDay, baseBean.getRespData().toString()));
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(BillDetailActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LogUtils.e("获取交易详情失败->" + throwable.getMessage());
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
                    mTvRepay.setText("还款日：" + creditCard.getRepayDayDate());
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
            "\t\t\"bm_month\": \"2018-03\",\n" +
            "\t\t\"bm_usable_limit\": 34000.0,\n" +
            "\t\t\"bm_repay_sum\": 13239.73,\n" +
            "\t\t\"bm_repay_min\": 8190.1,\n" +
            "\t\t\"bm_prior_sum\": 22721.07,\n" +
            "\t\t\"bm_prior_repay\": 435.0,\n" +
            "\t\t\"bm_current_sum\": 13240.09,\n" +
            "\t\t\"bm_integral_sum\": 5.0,\n" +
            "\t\t\"bm_integral_increment\": 210.0,\n" +
            "\t\t\"o_details\": [{\n" +
            "\t\t\t\"bd_date\": \"2018-02-07\",\n" +
            "\t\t\t\"bd_description\": \"支付宝网络还款\",\n" +
            "\t\t\t\"bd_money\": 435.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-07\",\n" +
            "\t\t\t\"bd_description\": \"账单分期 DIY - 22286.43\",\n" +
            "\t\t\t\"bd_money\": 22286.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-08\",\n" +
            "\t\t\t\"bd_description\": \"广州市花都区新华益龙汽车维修厂\",\n" +
            "\t\t\t\"bd_money\": 4000.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-09\",\n" +
            "\t\t\t\"bd_description\": \"账单分期 ( 账单 ) 001-003\",\n" +
            "\t\t\t\"bd_money\": 7429.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-09\",\n" +
            "\t\t\t\"bd_description\": \"账单分期 ( 账单 ) 分期手续费 001-003\",\n" +
            "\t\t\t\"bd_money\": 201.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-12\",\n" +
            "\t\t\t\"bd_description\": \"贵港市覃塘区东龙镇浩业百货店\",\n" +
            "\t\t\t\"bd_money\": 168.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-15\",\n" +
            "\t\t\t\"bd_description\": \"贵港市覃塘区东龙达强日杂批发店\",\n" +
            "\t\t\t\"bd_money\": 102.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-26\",\n" +
            "\t\t\t\"bd_description\": \"广州市荔湾区张龙烟酒行\",\n" +
            "\t\t\t\"bd_money\": 100.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-28\",\n" +
            "\t\t\t\"bd_description\": \"广州市天沔湘鄂风味餐厅\",\n" +
            "\t\t\t\"bd_money\": 200.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-28\",\n" +
            "\t\t\t\"bd_description\": \"广州石牌佳兴胜电子经营部\",\n" +
            "\t\t\t\"bd_money\": 200.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-01\",\n" +
            "\t\t\t\"bd_description\": \"Transit-ticket 广州地铁 ( 电话 96891)\",\n" +
            "\t\t\t\"bd_money\": 5.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-02\",\n" +
            "\t\t\t\"bd_description\": \"Transit-ticket 广州地铁 ( 电话 96891)\",\n" +
            "\t\t\t\"bd_money\": 5.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-02\",\n" +
            "\t\t\t\"bd_description\": \"Transit-ticket 广州地铁 ( 电话 96891)\",\n" +
            "\t\t\t\"bd_money\": 5.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-02\",\n" +
            "\t\t\t\"bd_description\": \"广州市神山恒顺电器店\",\n" +
            "\t\t\t\"bd_money\": 300.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-03\",\n" +
            "\t\t\t\"bd_description\": \"广州市成骐鞋业有限公司\",\n" +
            "\t\t\t\"bd_money\": 100.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-03\",\n" +
            "\t\t\t\"bd_description\": \"Transit-ticket 广州地铁 ( 电话 96891)\",\n" +
            "\t\t\t\"bd_money\": 5.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-04\",\n" +
            "\t\t\t\"bd_description\": \"Transit-ticket 广州地铁 ( 电话 96891)\",\n" +
            "\t\t\t\"bd_money\": 5.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-05\",\n" +
            "\t\t\t\"bd_description\": \"广州市越秀区东方加油站\",\n" +
            "\t\t\t\"bd_money\": 100.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-05\",\n" +
            "\t\t\t\"bd_description\": \"广州市番禺区石楼启航电器店\",\n" +
            "\t\t\t\"bd_money\": 106.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-05\",\n" +
            "\t\t\t\"bd_description\": \"广州市萝岗区花之美花店\",\n" +
            "\t\t\t\"bd_money\": 200.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-06\",\n" +
            "\t\t\t\"bd_description\": \"Transit-ticket 广州地铁 ( 电话 96891)\",\n" +
            "\t\t\t\"bd_money\": 5.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-06\",\n" +
            "\t\t\t\"bd_description\": \"Transit-ticket 广州地铁 ( 电话 96891)\",\n" +
            "\t\t\t\"bd_money\": 5.0\n" +
            "\t\t}]\n" +
            "\t}, {\n" +
            "\t\t\"bm_month\": \"2018-04\",\n" +
            "\t\t\"bm_usable_limit\": 34000.0,\n" +
            "\t\t\"bm_repay_sum\": 29075.12,\n" +
            "\t\t\"bm_repay_min\": 9773.72,\n" +
            "\t\t\"bm_prior_sum\": 13239.73,\n" +
            "\t\t\"bm_prior_repay\": 13240.0,\n" +
            "\t\t\"bm_current_sum\": 29075.39,\n" +
            "\t\t\"bm_integral_sum\": 6.0,\n" +
            "\t\t\"bm_integral_increment\": 1.0,\n" +
            "\t\t\"o_details\": [{\n" +
            "\t\t\t\"bd_date\": \"2018-02-09\",\n" +
            "\t\t\t\"bd_description\": \"支付宝还款\",\n" +
            "\t\t\t\"bd_money\": 13240.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-09\",\n" +
            "\t\t\t\"bd_description\": \"账单分期 ( 账单 ) 002-003\",\n" +
            "\t\t\t\"bd_money\": 7429.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-09\",\n" +
            "\t\t\t\"bd_description\": \"账单分期 ( 账单 ) 分期手续费 002-003\",\n" +
            "\t\t\t\"bd_money\": 201.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-07\",\n" +
            "\t\t\t\"bd_description\": \"Transit-ticket 广州地铁 ( 电话 96891)\",\n" +
            "\t\t\t\"bd_money\": 5.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-07\",\n" +
            "\t\t\t\"bd_description\": \"Transit-ticket 广州地铁 ( 电话 96891)\",\n" +
            "\t\t\t\"bd_money\": 5.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-08\",\n" +
            "\t\t\t\"bd_description\": \"广州市一纯服饰有限公司\",\n" +
            "\t\t\t\"bd_money\": 327.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-08\",\n" +
            "\t\t\t\"bd_description\": \"Transit-ticket 广州地铁 ( 电话 96891)\",\n" +
            "\t\t\t\"bd_money\": 5.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-09\",\n" +
            "\t\t\t\"bd_description\": \"广州市永新酒店\",\n" +
            "\t\t\t\"bd_money\": 499.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-21\",\n" +
            "\t\t\t\"bd_description\": \"广州市曾生名表店\",\n" +
            "\t\t\t\"bd_money\": 2999.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-22\",\n" +
            "\t\t\t\"bd_description\": \"广州市伟欣电脑\",\n" +
            "\t\t\t\"bd_money\": 7600.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-27\",\n" +
            "\t\t\t\"bd_description\": \"广州市桥东城进利服装\",\n" +
            "\t\t\t\"bd_money\": 2347.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-28\",\n" +
            "\t\t\t\"bd_description\": \"广州市雪龙商务酒店\",\n" +
            "\t\t\t\"bd_money\": 6380.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-31\",\n" +
            "\t\t\t\"bd_description\": \"年费 300.00 元\",\n" +
            "\t\t\t\"bd_money\": 0.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-03-31\",\n" +
            "\t\t\t\"bd_description\": \"刷卡 6 次免年费 300.00 元\",\n" +
            "\t\t\t\"bd_money\": 0.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-04-04\",\n" +
            "\t\t\t\"bd_description\": \"广州艾博特电子有限公司\",\n" +
            "\t\t\t\"bd_money\": 1200.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-04-05\",\n" +
            "\t\t\t\"bd_description\": \"广州市白云区太和彰兰服装店\",\n" +
            "\t\t\t\"bd_money\": 79.0\n" +
            "\t\t}]\n" +
            "\t}, {\n" +
            "\t\t\"bm_month\": \"2018-05\",\n" +
            "\t\t\"bm_usable_limit\": 34000.0,\n" +
            "\t\t\"bm_repay_sum\": 19238.26,\n" +
            "\t\t\"bm_repay_min\": 14364.76,\n" +
            "\t\t\"bm_prior_sum\": 29075.12,\n" +
            "\t\t\"bm_prior_repay\": 9059.0,\n" +
            "\t\t\"bm_current_sum\": 20239.22,\n" +
            "\t\t\"bm_integral_sum\": 7.0,\n" +
            "\t\t\"bm_integral_increment\": 333.0,\n" +
            "\t\t\"o_details\": [{\n" +
            "\t\t\t\"bd_date\": \"2018-02-09\",\n" +
            "\t\t\t\"bd_description\": \"支付宝还款\",\n" +
            "\t\t\t\"bd_money\": 8059.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-09\",\n" +
            "\t\t\t\"bd_description\": \"支付宝还款\",\n" +
            "\t\t\t\"bd_money\": 1000.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-09\",\n" +
            "\t\t\t\"bd_description\": \"账单分期 ( 账单 ) 003-003\",\n" +
            "\t\t\t\"bd_money\": 7429.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-02-09\",\n" +
            "\t\t\t\"bd_description\": \"账单分期 ( 账单 ) 分期手续费 003-003\",\n" +
            "\t\t\t\"bd_money\": 201.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-04-07\",\n" +
            "\t\t\t\"bd_description\": \"账单分期 DIY - 21017.08\",\n" +
            "\t\t\t\"bd_money\": 21017.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-04-15\",\n" +
            "\t\t\t\"bd_description\": \"账单分期 ( 账单 ) 001-003\",\n" +
            "\t\t\t\"bd_money\": 7006.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-04-15\",\n" +
            "\t\t\t\"bd_description\": \"账单分期 ( 账单 ) 分期手续费 001-003\",\n" +
            "\t\t\t\"bd_money\": 189.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-04-17\",\n" +
            "\t\t\t\"bd_description\": \"广州市同和花果山水果店\",\n" +
            "\t\t\t\"bd_money\": 100.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-04-17\",\n" +
            "\t\t\t\"bd_description\": \"广州市星旅酒店\",\n" +
            "\t\t\t\"bd_money\": 3000.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"bd_date\": \"2018-04-23\",\n" +
            "\t\t\t\"bd_description\": \"广州市石牌鑫泽电脑\",\n" +
            "\t\t\t\"bd_money\": 2315.0\n" +
            "\t\t}]\n" +
            "\t}],\n" +
            "\t\"unsettled\": [],\n" +
            "\t\"_req_id\": \"18a5d76b53294a4f8f66e506f7841011\"\n" +
            "}";
}
