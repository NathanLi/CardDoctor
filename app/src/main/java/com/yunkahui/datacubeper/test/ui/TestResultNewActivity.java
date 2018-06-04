package com.yunkahui.datacubeper.test.ui;

import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.test.ui.cardResult.ExpenseAnalyzeView;
import com.yunkahui.datacubeper.test.ui.cardResult.ExpenseDistributeView;
import com.yunkahui.datacubeper.test.ui.cardResult.ExpenseFeatureView;
import com.yunkahui.datacubeper.test.ui.cardResult.RiskTestView;
import com.yunkahui.datacubeper.test.ui.cardResult.TestConsumerItemView;
import com.yunkahui.datacubeper.test.ui.cardResult.TestConsumerTrendsView;
import com.yunkahui.datacubeper.test.ui.cardResult.TestInterestView;
import com.yunkahui.datacubeper.test.ui.cardResult.TestRefundCountView;

import java.util.ArrayList;
import java.util.List;

//卡·测评报告新页面
public class TestResultNewActivity extends AppCompatActivity implements IActivityStatusBar {

    private RiskTestView mRiskTestViewCashOut;
    private RiskTestView mRiskTestViewOverdue;
    private RiskTestView mRiskTestViewContinuous;
    private TestRefundCountView mTestRefundCountView;
    private TestInterestView mTestInterestView;
    private TestConsumerTrendsView mTestConsumerTrendsView;
    private RiskTestView mRiskTestViewMoneyUse;
    private ExpenseFeatureView mExpenseFeatureView;
    private ExpenseDistributeView mExpenseDistributeView;
    private ExpenseAnalyzeView mExpenseAnalyzeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test_result_new);
        super.onCreate(savedInstanceState);
        this.setTitle("卡·测评报告");

    }

    @Override
    public void initData() {
        //高套现风险
        mRiskTestViewCashOut.setCircleColor(Color.parseColor("#ff2c55"));
        mRiskTestViewCashOut.setCircleRadio(0.25f);
        mRiskTestViewCashOut.setCenterText(Html.fromHtml("<font><big>6次</big><br/><small>疑似套现</small></font>"));
        mRiskTestViewCashOut.setResultTitle("高套现风险");
        mRiskTestViewCashOut.setResultContent("消费时模拟真实交易，大小金额要相同");

        //逾期风险评估
        mRiskTestViewOverdue.setCircleColor(Color.parseColor("#ff6633"));
        mRiskTestViewOverdue.setCircleRadio(0.25f);
        mRiskTestViewOverdue.setCenterText(Html.fromHtml("<font><big>4次</big><br/><small>逾期记录</small></font>"));
        mRiskTestViewOverdue.setResultTitle("逾期严重");
        mRiskTestViewOverdue.setResultContent("请养成准时还款的习惯，保持良好信用");

        //连续逾期风险
        mRiskTestViewContinuous.setCircleColor(Color.parseColor("#8aca40"));
        mRiskTestViewContinuous.setCircleRadio(0.25f);
        mRiskTestViewContinuous.setCenterText(Html.fromHtml("<font><big>3次</big><br/><small>连续逾期</small></font>"));
        mRiskTestViewContinuous.setResultTitle("逾期风险高");
        mRiskTestViewContinuous.setResultContent("请养成准时还款的习惯，保持良好信用");

        //最低还款
        mTestRefundCountView.setSelect(4);
        mTestRefundCountView.setStatus("极差");
        mTestRefundCountView.setMessage("近6个月最低还款\n次数>=6次");

        //产生利息
        mTestInterestView.setStatus("高风险", Color.parseColor("#ff2c55"));
        mTestInterestView.setInterest("2000.00");

        //消费趋势
        mTestConsumerTrendsView.setTextViewMonthMoney("2000.00");
        mTestConsumerTrendsView.setAverageMoney("120");
        mTestConsumerTrendsView.setCount(16);
        mTestConsumerTrendsView.setConsumerData(getConsumer());

        //资金使用率
        mRiskTestViewMoneyUse.setCircleColor(Color.parseColor("#71c0af"));
        mRiskTestViewMoneyUse.setCircleRadio(0.25f);
        mRiskTestViewMoneyUse.setCenterText(Html.fromHtml("<font><big>30%</big></font>"));
        mRiskTestViewMoneyUse.setResultTitle("使用率低");
        mRiskTestViewMoneyUse.setResultContent("可适当提高使用信用卡消费频次，提高资金的使用率");

        //消费特征
        mExpenseFeatureView.setResult1("高");
        mExpenseFeatureView.setResult2("3笔");

        //周末时间消费发布
        mExpenseDistributeView.setData();

        //节假日消费分析
        //




    }

    //获取消费趋势的数据
    public List<TestConsumerItemView.Consumer> getConsumer() {
        List<TestConsumerItemView.Consumer> consumerList = new ArrayList<>();

        TestConsumerItemView.Consumer consumer1 = new TestConsumerItemView.Consumer();
        consumer1.setCount(5);
        consumer1.setLightColor(Color.parseColor("#498fe1"));
        consumer1.setDarkColor(Color.parseColor("#33498fe1"));
        consumer1.setMonth("5月");
        consumer1.setLightRatio(0.3f);
        consumer1.setMoney("3000.00");

        TestConsumerItemView.Consumer consumer2 = new TestConsumerItemView.Consumer();
        consumer2.setCount(5);
        consumer2.setLightColor(Color.parseColor("#f5a623"));
        consumer2.setDarkColor(Color.parseColor("#33f5a623"));
        consumer2.setMonth("4月");
        consumer2.setLightRatio(0.6f);
        consumer2.setMoney("3000.00");

        TestConsumerItemView.Consumer consumer3 = new TestConsumerItemView.Consumer();
        consumer3.setCount(15);
        consumer3.setLightColor(Color.parseColor("#7ed221"));
        consumer3.setDarkColor(Color.parseColor("#337ed221"));
        consumer3.setMonth("3月");
        consumer3.setLightRatio(0.45f);
        consumer3.setMoney("3000.00");

        TestConsumerItemView.Consumer consumer4 = new TestConsumerItemView.Consumer();
        consumer4.setCount(10);
        consumer4.setLightColor(Color.parseColor("#498fe1"));
        consumer4.setDarkColor(Color.parseColor("#33498fe1"));
        consumer4.setMonth("2月");
        consumer4.setLightRatio(0.3f);
        consumer4.setMoney("3000.00");

        TestConsumerItemView.Consumer consumer5 = new TestConsumerItemView.Consumer();
        consumer5.setCount(20);
        consumer5.setLightColor(Color.parseColor("#f5a623"));
        consumer5.setDarkColor(Color.parseColor("#33f5a623"));
        consumer5.setMonth("4月");
        consumer5.setLightRatio(0.6f);
        consumer5.setMoney("3000.00");

        TestConsumerItemView.Consumer consumer6 = new TestConsumerItemView.Consumer();
        consumer6.setCount(15);
        consumer6.setLightColor(Color.parseColor("#7ed221"));
        consumer6.setDarkColor(Color.parseColor("#337ed221"));
        consumer6.setMonth("3月");
        consumer6.setLightRatio(0.45f);
        consumer6.setMoney("3000.00");

        consumerList.add(consumer1);
        consumerList.add(consumer2);
        consumerList.add(consumer3);
        consumerList.add(consumer4);
        consumerList.add(consumer5);
        consumerList.add(consumer6);

        return consumerList;
    }

    @Override
    public void initView() {
        mRiskTestViewCashOut = findViewById(R.id.risk_test_view_cash_out);
        mRiskTestViewOverdue = findViewById(R.id.risk_test_view_overdue);
        mRiskTestViewContinuous = findViewById(R.id.risk_test_view_continuous_overdue);
        mTestRefundCountView = findViewById(R.id.text_refund_count_view);
        mTestInterestView = findViewById(R.id.test_interest_view);
        mTestConsumerTrendsView = findViewById(R.id.test_consumer_trends_view);
        mRiskTestViewMoneyUse = findViewById(R.id.risk_test_view_money_use);
        mExpenseFeatureView = findViewById(R.id.expense_feature_view);
        mExpenseDistributeView = findViewById(R.id.expense_distribute_view);
        mExpenseAnalyzeView = findViewById(R.id.expense_analyze_view);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
