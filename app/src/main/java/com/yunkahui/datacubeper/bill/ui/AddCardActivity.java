package com.yunkahui.datacubeper.bill.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.adapter.SelectDateAdapter;
import com.yunkahui.datacubeper.bill.logic.AddCardLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.ToastUtils;
import com.yunkahui.datacubeper.common.view.InfoFillView;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import org.json.JSONObject;

import java.util.ArrayList;

public class AddCardActivity extends AppCompatActivity implements IActivityStatusBar {

    public static final int TYPE_ADD = 101;
    public static final int TYPE_EDIT = 102;
    public static final int TYPE_EDIT2 = 103;

    private InfoFillView mInfoFillName;
    private InfoFillView mInfoFillCardNum;
    private InfoFillView mInfoFillBankName;
    private InfoFillView mInfoFillBill;
    private InfoFillView mInfoFillRepay;

    private AddCardLogic mLogic;
    private Button mBtnCommit;
    private String mBankNameEn;
    private int mBillDay;
    private int mRepayDay;
    private int mBankCardId;
    private String mBankCardName;
    private int mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_card);
        super.onCreate(savedInstanceState);
        mType = getIntent().getIntExtra("type", TYPE_ADD);
        switch (mType) {
            case TYPE_ADD:
                setTitle("添加卡片");
                break;
            case TYPE_EDIT:
                setTitle("修改卡片");
                break;
        }

    }

    @Override
    public void initData() {
        mLogic = new AddCardLogic();
        setOnClickListener();
        mInfoFillName.setName(DataUtils.getRealName());
        mInfoFillCardNum.setCursorVisible(false);
        mInfoFillBankName.setEnabled(false);
        mBankCardId = getIntent().getIntExtra("card_id", 0);
        String cardNum = getIntent().getStringExtra("card_number");
        if (!TextUtils.isEmpty(cardNum)) {
            mInfoFillCardNum.setText(cardNum);
        }
        mBankCardName = getIntent().getStringExtra("bank_card_name");
        if (!TextUtils.isEmpty(mBankCardName)) {
            mInfoFillBankName.setDest(mBankCardName);
        }
        mInfoFillCardNum.setOnEditTextTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    mInfoFillCardNum.setCursorVisible(true);// 再次点击显示光标
                }
                view.performClick();
                return false;
            }
        });
        mInfoFillCardNum.addTextChangeListener(new CustomTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 10) {
                    mLogic.queryBankByCardId(AddCardActivity.this, s.toString(), new SimpleCallBack<BaseBean>() {
                        @Override
                        public void onSuccess(BaseBean baseBean) {
                            LogUtils.e("卡归属->" + baseBean.getJsonObject().toString());
                            if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                                JSONObject object = baseBean.getJsonObject();
                                JSONObject respData = object.optJSONObject("respData");
                                mInfoFillBankName.setDest(respData.optString("bankName"));
                                mBankNameEn = respData.optString("bankNameEn");
                            } else {
//                                Toast.makeText(AddCardActivity.this, baseBean.getRespDesc(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
//                            Toast.makeText(AddCardActivity.this, "获取卡归属失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void initView() {
        mInfoFillName = findViewById(R.id.info_fill_name);
        mInfoFillCardNum = findViewById(R.id.info_fill_card_id);
        mInfoFillBankName = findViewById(R.id.info_fill_bank);
        mInfoFillBill = findViewById(R.id.info_fill_bill);
        mInfoFillRepay = findViewById(R.id.info_fill_repay);
        mBtnCommit = findViewById(R.id.btn_commit);
    }

    private boolean check() {
        if (TextUtils.isEmpty(mInfoFillCardNum.getEditText())) {
            ToastUtils.show(getApplicationContext(), "请输入卡号");
            return false;
        }
        if (TextUtils.isEmpty(mInfoFillBankName.getDest())) {
            ToastUtils.show(getApplicationContext(), "发卡行识别有误");
            return false;
        }
        if (mBillDay == 0) {
            ToastUtils.show(getApplicationContext(), "请选择账单日");
            return false;
        }
        if (mRepayDay == 0) {
            ToastUtils.show(getApplicationContext(), "请选择还款日");
            return false;
        }
        return true;
    }

    //添加卡片
    private void addCard() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.addBankCard(AddCardActivity.this, mInfoFillCardNum.getEditText(), mInfoFillBankName.getDest(), mBankNameEn,
                mInfoFillName.getName(), mBillDay, mRepayDay, new SimpleCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        LoadingViewDialog.getInstance().dismiss();
                        LogUtils.e("添加卡->" + baseBean.getJsonObject().toString());
                        ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                        if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                            setResult(RESULT_OK);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        LoadingViewDialog.getInstance().dismiss();
                        Toast.makeText(AddCardActivity.this, "添加卡片失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //修改卡片
    private void editCard() {
        LoadingViewDialog.getInstance().show(this);
        mLogic.editCard(this, mInfoFillCardNum.getEditText(), mInfoFillBankName.getDest(), mInfoFillName.getName(), mBillDay, mRepayDay, mBankCardId, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), baseBean.getRespDesc());
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
                ToastUtils.show(getApplicationContext(), "请求失败 " + throwable.toString());
            }
        });
    }


    private void setOnClickListener() {
        mInfoFillBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateSelector(true);
            }
        });
        mInfoFillRepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateSelector(false);
            }
        });
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check()) {
                    return;
                }
                switch (mType) {
                    case TYPE_ADD:
                        addCard();
                        break;
                    case TYPE_EDIT:
                        editCard();
                        break;
                }
            }
        });
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    private void showDateSelector(final boolean isBill) {
        RecyclerView recyclerView = new RecyclerView(this);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 31; i++) {
            list.add(String.valueOf(i));
        }
        SelectDateAdapter selectDateAdapter = new SelectDateAdapter(R.layout.layout_list_item_select_date, list);
        selectDateAdapter.bindToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        recyclerView.setAdapter(selectDateAdapter);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_logo)
                .setTitle(String.format("请选择%s日", isBill ? "账单" : "还款"))
                .setView(recyclerView)
                .show();
        selectDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (isBill) {
                    mBillDay = position + 1;
                    mInfoFillBill.setDest(String.format(getString(R.string.day_sel_format), position + 1));
                } else {
                    mRepayDay = position + 1;
                    mInfoFillRepay.setDest(String.format(getString(R.string.day_sel_format), position + 1));
                }
                dialog.dismiss();
            }
        });
    }
}
