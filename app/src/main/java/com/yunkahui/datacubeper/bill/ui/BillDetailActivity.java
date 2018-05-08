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
import com.yunkahui.datacubeper.common.bean.TimeSection;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
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

    private BillDetailLogic mLogic;
    private ExpandableBillDeatailAdapter mAdapter;
    private List<TimeSection> mSectionList;
    private List<MultiItemEntity> mList;
    private int mCardId;
    private boolean mIsRepaid;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void initData() {
        mCardId = getIntent().getIntExtra("user_credit_card_id", 0);
        mLogic = new BillDetailLogic();
        mList = new ArrayList<>();
        mSectionList = new ArrayList<>();
        mAdapter = new ExpandableBillDeatailAdapter(this, mList);
        mAdapter.bindToRecyclerView(mRecyclerView);
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_list_header_bill_detail, null);
        initHeaderView(headerView);
        mAdapter.setHeaderView(headerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.expandAll();
        getBillDetailTop();
    }

    @SuppressLint("SetTextI18n")
    private void initHeaderView(View headerView) {
        mTvLeft = headerView.findViewById(R.id.tv_left);
        mTvBack = headerView.findViewById(R.id.tv_back);
        mTvLess = headerView.findViewById(R.id.tv_less);
        mTvTmp = headerView.findViewById(R.id.tv_tmp);
        mTvFix = headerView.findViewById(R.id.tv_fix);
        headerView.findViewById(R.id.show_edit).setOnClickListener(this);
        String cardNum = getIntent().getStringExtra("card_num");
        String cardHolder = getIntent().getStringExtra("card_holder");
        ((TextView) headerView.findViewById(R.id.tv_mess))
                .setText("- - - - - - - - " + cardNum.substring(cardNum.length() - 4, cardNum.length()) + cardHolder);
        ((TextView) headerView.findViewById(R.id.tv_repay))
                .setText("还款日：" + getIntent().getStringExtra("reday_date"));
        ((TextView) headerView.findViewById(R.id.tv_account))
                .setText("账单日：" + TimeUtils.format("MM-dd", getIntent().getLongExtra("bill_date", 0)));
    }

    //******** 获取账单详情 ********
    private void getBillDetailData(final int billDay) {
        mLogic.getBillDetail(this, mCardId, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LogUtils.e("账单详情->" + baseBean.getJsonObject().toString());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    mList.addAll(mLogic.handleData(baseBean, billDay));
                    LoadingViewDialog.getInstance().dismiss();
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(BillDetailActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                Toast.makeText(BillDetailActivity.this, "获取账单详情失败" + throwable.toString(), Toast.LENGTH_SHORT).show();
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
                    getBillDetailData(Integer.parseInt(respData.optString("bill_day")));
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
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_edit:
                Intent intent = new Intent(this, AddCardActivity.class);
                intent.putExtra("card_id", mCardId);
                intent.putExtra("card_number", getIntent().getStringExtra("card_num"));
                intent.putExtra("bank_card_name", getIntent().getStringExtra("bank_card_name"));
                startActivityForResult(intent, RESULT_CODE_UPDATE);
                break;
            case R.id.ll_update:
                startActivity(new Intent(this, BillSyncActivity.class));
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
}
