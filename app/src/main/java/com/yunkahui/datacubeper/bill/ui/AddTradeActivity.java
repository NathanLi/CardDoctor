package com.yunkahui.datacubeper.bill.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ct.incrementadapter.NoDoubleClickListener;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.logic.AddTradeLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;
import com.yunkahui.datacubeper.common.view.FillSpinnerView;
import com.yunkahui.datacubeper.common.view.InfoFillView;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTradeActivity extends AppCompatActivity implements IActivityStatusBar {

    private String TAG = "AddTradeActivity";
    private AddTradeLogic mLogic;
    private InfoFillView mIfvCardId;
    private InfoFillView mIfvDate;
    private InfoFillView mIfvTime;
    private InfoFillView mIfvMoney;
    private FillSpinnerView mFsvType;
    private FillSpinnerView mFsvStore;
    private String mType="CONSUMPTION";
    private String mMccType;
    private List<String> mStoreList;

    @Override
    public void initData() {
        mLogic = new AddTradeLogic();
        mStoreList = new ArrayList<>();
        mIfvCardId.setDest(getIntent().getStringExtra("card_num"));
        mIfvCardId.setInputVisibility(false);
        mIfvDate.setInputVisibility(false);
        mIfvTime.setInputVisibility(false);
        mIfvCardId.setDestColor(getResources().getColor(R.color.text_color_gray_626262));
        initListener();
        getMccList();
    }

    private void getMccList() {
        mLogic.getMccList(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                    JSONArray array = baseBean.getJsonObject().optJSONArray("respData");
                    mStoreList.clear();
                    for (int i = 0; i < array.length(); i++) {
                        mStoreList.add(array.optString(i));
                    }
                    mFsvStore.setSpinnerData(mStoreList);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }

    private void initListener() {
        mFsvType.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    mFsvStore.setVisibility(View.VISIBLE);
                    mType="CONSUMPTION";
                }else{
                    mFsvStore.setVisibility(View.GONE);
                    mType="REPAY";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mFsvStore.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mStoreList.size()>0){
                    mMccType=mStoreList.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mIfvDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                new DatePickerDialog(AddTradeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mIfvDate.setDest(String.format(getString(R.string.year_month_day), year, month+1, dayOfMonth));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mIfvTime.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                c.add(Calendar.MINUTE, 5);
                new TimePickerDialog(AddTradeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mIfvTime.setDest(String.format(getString(R.string.hour_minute), hourOfDay, minute));
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
            }
        });
        findViewById(R.id.btn_sure).setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                LoadingViewDialog.getInstance().show(AddTradeActivity.this);
                long date = TimeUtils.getTimeStampByDate(TimeUtils.DEFAULT_PATTERN_WITH_HM, mIfvDate.getDest() +" "+mIfvTime.getDest());
                mLogic.createBill(AddTradeActivity.this, getIntent().getIntExtra("user_credit_card_id", 0), mType, date, mIfvMoney.getEditText(), mMccType, "", new SimpleCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        LoadingViewDialog.getInstance().dismiss();
                        if (RequestUtils.SUCCESS.equals(baseBean.getRespCode())) {
                            Toast.makeText(AddTradeActivity.this, "添加完毕", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        LoadingViewDialog.getInstance().dismiss();
                        Log.e(TAG, "create bill onFailure: "+throwable.getMessage());
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_trade);
        super.onCreate(savedInstanceState);
        setTitle("添加交易");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void initView() {
        mIfvCardId = findViewById(R.id.ifv_card_id);
        mIfvDate = findViewById(R.id.ifv_date);
        mIfvTime = findViewById(R.id.ifv_time);
        mIfvMoney = findViewById(R.id.ifv_money);
        mFsvType = findViewById(R.id.fsv_type);
        mFsvStore = findViewById(R.id.fsv_store);
    }
}
