package com.yunkahui.datacubeper.bill.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.TimeHeader;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author WYF on 2018/4/20/020.
 */
class TimePickerActivity extends AppCompatActivity implements IActivityStatusBar {

    private RecyclerView recyclerView;


    @Override
    public void initData() {
        Calendar calendar= Calendar.getInstance();  //获取当前时间，作为图标的名字
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int endMonth = Integer.parseInt(getIntent().getStringExtra("endTime").substring(0, 2));
        int endDay = Integer.parseInt(getIntent().getStringExtra("endTime").substring(3)) - 1;
        List<TimeHeader> list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_time_picker);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
