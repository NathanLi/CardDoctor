package com.yunkahui.datacubeper.bill.logic;

import android.content.Context;
import android.util.Log;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.BillDetailItem;
import com.yunkahui.datacubeper.common.bean.BillDetailSummary;
import com.yunkahui.datacubeper.common.bean.TimeSection;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BillDetailLogic {

    //******** 获取账单头部信息 ********
    public void getBillDetailTop(Context context, int cardId, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("user_credit_card_id", String.valueOf(cardId))
                .create();
        HttpManager.getInstance().create(ApiService.class).loadBillDetailTop(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    //获取单张卡片详情数据
    public void loadCardDeatailData(Context context, int cardId, SimpleCallBack<BaseBean<BillCreditCard>> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("bank_card_id", cardId)
                .create();
        HttpManager.getInstance().create(ApiService.class).queryCreditCardList(params)
                .compose(HttpManager.<BaseBean<BillCreditCard>>applySchedulers()).subscribe(callBack);
    }

    //获取交易详情
    public void loadTradeHistory(Context context, String cardNum, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("bank_card_num", cardNum)
                .create();
        HttpManager.getInstance().create(ApiService.class).queryTradeHistory(params)
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

    public List<BillDetailSummary> handleData(int billDay, String data) {
        List<BillDetailSummary> mList = null;
        try {
            mList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(data);

            //******** 设置已出账单 ********
            JSONArray settledArr = jsonObject.optJSONArray("settled");
            //LogUtils.e("已出->" + settledArr.toString());
            for (int x = 0; x < settledArr.length(); x++) {
                JSONObject settledObject = settledArr.optJSONObject(x);
                String bmMonth = settledObject.optString("bm_month");
                BillDetailSummary summary = new BillDetailSummary();
                summary.setYear(bmMonth.substring(0, 4));
                int month = Integer.parseInt(bmMonth.substring(5));
                //LogUtils.e("已出日期->" + bmMonth.substring(0, 4) + ", " + bmMonth.substring(5) + ", " + (month - 1) + "-" + billDay + ", " + month + "-" + (billDay - 1));
                summary.setMsg(month + "月");
                summary.setStartDate((month - 1) + "-" + billDay);
                summary.setEndDate(month + "-" + (billDay - 1));
                JSONArray detailArr = settledObject.optJSONArray("o_details");
                for (int y = 0; y < detailArr.length(); y++) {
                    JSONObject object = detailArr.optJSONObject(y);
                    //LogUtils.e("已出信息->" + object.optString("bd_description") + ", " + object.optString("bd_money")
                    //        + ", " + object.optString("bd_date"));
                    BillDetailItem item = new BillDetailItem();
                    item.setMsg(object.optString("bd_description"));
                    item.setMoney(object.optDouble("bd_money"));
                    item.setTime(object.optString("bd_date"));
                    summary.addSubItem(0, item);
                }
                mList.add(summary);
            }
            Collections.reverse(mList);

            //******** 设置未出账单 ********
            Calendar currentCalendar = TimeUtils.getCalendar(System.currentTimeMillis());
            boolean isUpperHalfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH) < billDay;
            BillDetailSummary unsettledSummary = new BillDetailSummary();
            unsettledSummary.setMsg("未出账单");
            //LogUtils.e("未出年份->" + currentCalendar.get(Calendar.YEAR));
            //LogUtils.e("未出起始->" + currentCalendar.get(Calendar.MONTH) + (isUpperHalfMonth ? 0 : 1) + "-" + billDay);
            //LogUtils.e("未出结束->" + currentCalendar.get(Calendar.MONTH) + (isUpperHalfMonth ? 1 : 2) + "-" + (billDay - 1));
            unsettledSummary.setYear(String.valueOf(currentCalendar.get(Calendar.YEAR)));
            unsettledSummary.setStartDate(currentCalendar.get(Calendar.MONTH) + (isUpperHalfMonth ? 0 : 1) + "-" + billDay);
            unsettledSummary.setEndDate(currentCalendar.get(Calendar.MONTH) + (isUpperHalfMonth ? 1 : 2) + "-" + (billDay - 1));
            JSONObject unsettledObject = jsonObject.optJSONArray("unsettled").optJSONObject(0);
            if (unsettledObject != null) {
                JSONArray unsettledArr = unsettledObject.optJSONArray("o_details");
                //LogUtils.e("未出账数据->" + unsettledArr.toString());
                for (int i = 0; i < unsettledArr.length(); i++) {
                    JSONObject object = unsettledArr.optJSONObject(i);
                    BillDetailItem item = new BillDetailItem();
                    //LogUtils.e("未出账详细信息->" + object.optString("bd_description") + ", " + object.optString("bd_money")
                    //        + ", " + object.optString("bd_date"));
                    item.setMsg(object.optString("bd_description"));
                    item.setMoney(object.optDouble("bd_money"));
                    item.setTime(object.optString("bd_date"));
                    unsettledSummary.addSubItem(0, item);
                }
            }
            mList.add(0, unsettledSummary);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mList;
    }
}
