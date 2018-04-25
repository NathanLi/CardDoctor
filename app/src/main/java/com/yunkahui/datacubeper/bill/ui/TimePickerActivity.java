package com.yunkahui.datacubeper.bill.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.bill.adapter.TimePickerAdapter;
import com.yunkahui.datacubeper.common.bean.TimeItem;
import com.yunkahui.datacubeper.common.bean.TimeHeader;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author WYF on 2018/4/20/020.
 */
public class TimePickerActivity extends AppCompatActivity implements IActivityStatusBar {

    private RecyclerView mRecyclerView;
    private static final String TAG = "TimePickerActivity";
    private List<TimeHeader> mList;
    private ArrayList<TimeItem> mSelectTimes;

    @Override
    public void initData() {
        initTimeList();
        TimePickerAdapter timePickerAdapter = new TimePickerAdapter(R.layout.layout_list_item_time_picker, R.layout.layout_list_header_time_picker, mList);
        timePickerAdapter.bindToRecyclerView(mRecyclerView);
        timePickerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImageView ivCircle = (ImageView) adapter.getViewByPosition(position, R.id.iv_circle);
                TextView tvDay = (TextView) adapter.getViewByPosition(position, R.id.tv_day);
                if (ivCircle != null && tvDay != null && !TextUtils.isEmpty(tvDay.getText().toString())) {
                    if (ivCircle.getVisibility() == View.VISIBLE) {
                        ivCircle.setVisibility(View.INVISIBLE);
                        tvDay.setTextColor(Color.BLACK);
                        TimeHeader timeHeader = mList.get(position);
                        if (!timeHeader.isHeader) {
                            mSelectTimes.remove(new TimeItem(timeHeader.t.getYear(), timeHeader.t.getMonth(), timeHeader.t.getDay()));
                        }
                    } else {
                        ivCircle.setVisibility(View.VISIBLE);
                        tvDay.setTextColor(Color.WHITE);
                        TimeHeader timeHeader = mList.get(position);
                        if (!timeHeader.isHeader) {
                            if (mSelectTimes == null)
                                mSelectTimes = new ArrayList<>();
                            mSelectTimes.add(new TimeItem(timeHeader.t.getYear(), timeHeader.t.getMonth(), timeHeader.t.getDay()));
                        }
                    }
                }
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(timePickerAdapter);
    }

    private void initTimeList() {
        ArrayList<TimeItem> selectedList = getIntent().getParcelableArrayListExtra("selected_time");
        mList = new ArrayList<>();
        mSelectTimes = new ArrayList<>();
        Calendar curCalendar = TimeUtils.getCalendar(System.currentTimeMillis());
        Calendar endCalendar = TimeUtils.getCalendar(getIntent().getLongExtra("time", 0));
        if (curCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR) &&
                curCalendar.get(Calendar.MONTH) + 1 == endCalendar.get(Calendar.MONTH) + 1 &&
                curCalendar.get(Calendar.DAY_OF_MONTH) + 1 == endCalendar.get(Calendar.DAY_OF_MONTH)) {
            addTimeHeader(curCalendar);
            addTimeItem(selectedList, curCalendar);
        } else {
            int lastMonth = 0;
            while (curCalendar.get(Calendar.YEAR) != endCalendar.get(Calendar.YEAR) ||
                    curCalendar.get(Calendar.MONTH) + 1 != endCalendar.get(Calendar.MONTH) + 1 ||
                    curCalendar.get(Calendar.DAY_OF_MONTH) != endCalendar.get(Calendar.DAY_OF_MONTH)) {
                if (lastMonth != curCalendar.get(Calendar.MONTH) + 1) {
                    int month = addTimeHeader(curCalendar);
                    lastMonth = month;
                }
                addTimeItem(selectedList, curCalendar);
                curCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
    }

    private void addTimeItem(ArrayList<TimeItem> selectedList, Calendar curCalendar) {
        TimeItem bean = new TimeItem(curCalendar.get(Calendar.YEAR), curCalendar.get(Calendar.MONTH) + 1, curCalendar.get(Calendar.DAY_OF_MONTH));
        boolean contain = selectedList.contains(bean);
        if (contain) {
            mSelectTimes.add(bean);
        }
        mList.add(new TimeHeader(new TimeItem(curCalendar.get(Calendar.YEAR), curCalendar.get(Calendar.MONTH) + 1, curCalendar.get(Calendar.DAY_OF_MONTH), contain)));
    }

    private int addTimeHeader(Calendar curCalendar) {
        int month = curCalendar.get(Calendar.MONTH) + 1;
        mList.add(new TimeHeader(true, curCalendar.get(Calendar.YEAR) + "年" + month + "月"));
        for (int i = 0; i < curCalendar.get(Calendar.DAY_OF_WEEK) - 1; i++) {
            mList.add(new TimeHeader(new TimeItem(0, false)));
        }
        return month;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_time_picker);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "确认").setIcon(R.mipmap.ic_sure_white).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                setResult(Activity.RESULT_FIRST_USER, new Intent().putParcelableArrayListExtra("selected_time", mSelectTimes));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
