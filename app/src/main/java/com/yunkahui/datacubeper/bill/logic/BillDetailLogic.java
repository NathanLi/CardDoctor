package com.yunkahui.datacubeper.bill.logic;

import android.content.Context;
import android.util.Log;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillDetailItem;
import com.yunkahui.datacubeper.common.bean.BillDetailSummary;
import com.yunkahui.datacubeper.common.bean.TimeSection;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class BillDetailLogic {

    private static final String TAG = "BillDetailLogic";

    //******** 获取账单头部信息 ********
    public void getBillDetailTop(Context context, int cardId, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("user_credit_card_id", String.valueOf(cardId))
                .create();
        HttpManager.getInstance().create(ApiService.class).loadBillDetailTop(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    //******** 获取账单详情 ********
    public void getBillDetail(Context context, int cardId, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("user_credit_card_id", String.valueOf(cardId))
                .create();
        //HttpManager.getInstance().addConverterFactory(CustomConverterFactory.create()).newBuilder().baseUrl("http://192.168.1.141:8014").build()
                //.create(ApiService.class).loadBillDetail(params).compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
        HttpManager.getInstance().create(ApiService.class).loadBillDetail(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    public void signRepay(Context context, int cardId, int status, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("bank_card_id", String.valueOf(cardId))
                .addParams("repay_status", status)
                .create();
        HttpManager.getInstance().create(ApiService.class).requestSignRepay(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    public List<BillDetailSummary> handleData(BaseBean baseBean, int billDay) {
        List<TimeSection> mSectionList = new ArrayList<>();
        List<BillDetailSummary> mList = new ArrayList<>();
        JSONObject jsonObject = baseBean.getJsonObject();
        JSONObject respData = jsonObject.optJSONObject("respData");
        JSONArray billUnoutArr = respData.optJSONArray("bill_unout");
        JSONArray billOutArr = respData.optJSONArray("bill_out");
        //******** 取出最早的一条记录 ********
        BillDetailItem originalItem = CreateBean(billOutArr.optJSONObject(billOutArr.length() - 1));
        String originalItemTradeDate = TimeUtils.format("yyyy-MM-dd", originalItem.getTrade_date());
        long originalStartDateMillis = TimeUtils.getTimeStampByDate("yyyy-MM-dd", originalItemTradeDate.substring(0, 7) + "-" + TimeUtils.addZero(billDay));
        Calendar startCalendar = TimeUtils.getCalendar(originalStartDateMillis);
        if (Integer.parseInt(originalItemTradeDate.substring(8)) < billDay) {
            startCalendar.add(Calendar.MONTH, -1);
        }
        Log.e(TAG, "最早的记录的开始时间: " + startCalendar.getTimeInMillis() + ", " + TimeUtils.format(TimeUtils.DEFAULT_PATTERN_WITH_HMS, startCalendar.getTimeInMillis()));
        long startMillis = startCalendar.getTimeInMillis();
        startCalendar.add(Calendar.MONTH, 1);
        startCalendar.add(Calendar.DAY_OF_MONTH, -1);
        Log.e(TAG, "最早的记录的结束时间: " + startCalendar.getTimeInMillis() + ", " + TimeUtils.format(TimeUtils.DEFAULT_PATTERN_WITH_HMS, startCalendar.getTimeInMillis()));
        //******** 计算最早的时间戳，存入map ********
        mSectionList.add(new TimeSection(startMillis, startCalendar.getTimeInMillis()));
        //******** 取出最近的一条记录 ********
        Calendar endItemCalendar = TimeUtils.getCalendar(System.currentTimeMillis());
        int destYear = endItemCalendar.get(Calendar.YEAR);
        int destMonth = endItemCalendar.get(Calendar.MONTH) + (endItemCalendar.get(Calendar.DAY_OF_MONTH) < billDay ? 0 : 1);
        long destStartMillis = TimeUtils.getTimeStampByDate(TimeUtils.DEFAULT_PATTERN, destYear + "-" + destMonth + "-" + billDay);
        Log.e(TAG, "最近的记录的开始时间: " + destStartMillis + ", " + TimeUtils.format(TimeUtils.DEFAULT_PATTERN_WITH_HMS, destStartMillis));
        Calendar destCalendar = TimeUtils.getCalendar(destStartMillis);
        destCalendar.add(Calendar.MONTH, 1);
        destCalendar.add(Calendar.DAY_OF_MONTH, -1);
        //******** 计算最近的时间戳，存入map ********
        Log.e(TAG, "最近的记录的结束时间: " + destCalendar.getTimeInMillis() + ", " + TimeUtils.format(TimeUtils.DEFAULT_PATTERN, destCalendar.getTimeInMillis()));
        //******** 循环组成时间数组 ********
        Calendar calendar = TimeUtils.getCalendar(startCalendar.getTimeInMillis());
        while (calendar.getTimeInMillis() < destCalendar.getTimeInMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            long start = calendar.getTimeInMillis();
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            mSectionList.add(new TimeSection(start, calendar.getTimeInMillis()));
        }
        //******** 初始化一个和时间数组一样大的bean数组 ********
        for (int i = 0; i < mSectionList.size(); i++) {
            mList.add(new BillDetailSummary());
        }
        //******** 封装出账数据 ********
        for (int i = 0; i < billOutArr.length(); i++) {
            BillDetailItem billDetailItem = CreateBean(billOutArr.optJSONObject(i));
            for (int j = 0; j < mSectionList.size(); j++) {
                BillDetailSummary summary = (BillDetailSummary) mList.get(j);
                String startDate = TimeUtils.format("yyyy-MM-dd", mSectionList.get(j).getStartTimeStamp());
                String endDate = TimeUtils.format("yyyy-MM-dd", mSectionList.get(j).getEndTimeStamp());
                if (mSectionList.get(j).getStartTimeStamp() < billDetailItem.getTrade_date() &&
                        billDetailItem.getTrade_date() < mSectionList.get(j).getEndTimeStamp()) {
                    if (summary.getMess() == null) {
                        summary.setStartDate(startDate.substring(5));
                        summary.setEndDate(endDate.substring(5));
                        String[] dateArr = endDate.split("-");
                        summary.setYear(dateArr[0]);
                        summary.setMess(dateArr[1] + "月");
                    }
                    summary.addSubItem(billDetailItem);
                    break;
                }
            }
        }
        //******** 封装未出账数据 ********
        BillDetailSummary summary = mList.get(mList.size() - 1);
        String startDate = TimeUtils.format("MM-dd", mSectionList.get(mList.size() - 1).getStartTimeStamp());
        String endDate = TimeUtils.format("yyyy-MM-dd", mSectionList.get(mList.size() - 1).getEndTimeStamp());
        summary.setMess("未出账单");
        summary.setStartDate(startDate);
        summary.setEndDate(endDate.substring(5));
        summary.setYear(endDate.substring(0, 4));
        for (int i = 0; i < billUnoutArr.length(); i++) {
            summary.addSubItem(CreateBean(billUnoutArr.optJSONObject(i)));
        }
        List<BillDetailSummary> summaries = new ArrayList<>();
        for (int i = mList.size() - 1; i >= 0; i--) {
            BillDetailSummary s = mList.get(i);
            if (s.getMess() == null)
                continue;
            summaries.add(s);
        }
        return summaries;
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
}
