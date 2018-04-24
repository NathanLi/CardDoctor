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

import com.ct.incrementadapter.NoDoubleClickListener;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.logic.AddTradeLogic;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.view.FillSpinnerView;
import com.yunkahui.datacubeper.common.view.InfoFillView;

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
    private String mType="CASE1";
    private String mMccType;
    private List<String> mStoreList;

    @Override
    public void initData() {
        //String date = dateText.getMess().replace("-", "") + timeText.getMess().replace(":", "") + "00";
        mLogic = new AddTradeLogic();
        mStoreList = new ArrayList<>();
        mFsvType.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    mFsvStore.setVisibility(View.VISIBLE);
                    mType="CASE1";
                }else{
                    mFsvStore.setVisibility(View.GONE);
                    mType="CASE2";
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
            @Override protected void onNoDoubleClick(View v) {
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
            @Override protected void onNoDoubleClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.MINUTE, 5);
                new TimePickerDialog(AddTradeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mIfvTime.setDest(String.format(getString(R.string.hour_minute), hourOfDay, minute));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });
        mLogic.getMccList(this, new SimpleCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                Log.e(TAG, "onSuccess: "+baseBean.getJsonObject().toString());
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
