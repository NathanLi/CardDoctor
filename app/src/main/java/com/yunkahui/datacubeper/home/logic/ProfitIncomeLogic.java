package com.yunkahui.datacubeper.home.logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.ArrayMap;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.CardDoctorApplication;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.bean.TradeRecordSummary;
import com.yunkahui.datacubeper.common.bean.WithdrawRecord;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfitIncomeLogic {

    public void getProfitIncome(Context context, int pageSize, int pageNum, String checkType, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .addParams("check_type", checkType)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadTradeDetail(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    public List<MultiItemEntity> parsingJSONForProfitIncome(BaseBean baseBean) {
        List<MultiItemEntity> list = null;
        try {
            list = new ArrayList<>();
            JSONObject object = baseBean.getJsonObject();
            JSONObject respData = object.optJSONObject("respData");
            JSONArray jsonArray = respData.optJSONArray("list");
            TradeRecordDetail item;
            TradeRecordSummary summary = new TradeRecordSummary();
            TradeRecordDetail lastItem = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject j = new JSONObject(jsonArray.opt(i).toString());
                item = new TradeRecordDetail();
                item.setTimeStamp(j.optLong("create_time"));
                item.setTradeType(j.optString("trade_type"));
                item.setTime(com.yunkahui.datacubeper.common.utils.TimeUtils.format("MM-dd hh:mm", j.optLong("create_time")));
                item.setMoney(j.optString("amountString"));
                item.setTitle(j.optString("trade_type_ios"));
                if (lastItem != null) {
                    if (item.getTime().startsWith("0") && lastItem.getTime().startsWith("0") &&
                            Integer.parseInt(lastItem.getTime().substring(1, 2)) > Integer.parseInt(item.getTime().substring(1, 2))) {
                        summaryInfo(summary);
                        list.add(summary);
                        summary = new TradeRecordSummary();
                        summary.addSubItem(item);
                    } else if (!item.getTime().startsWith("0") && lastItem.getTime().startsWith("0")) {
                        summaryInfo(summary);
                        list.add(summary);
                        summary = new TradeRecordSummary();
                        summary.addSubItem(item);
                    } else {
                        summary.addSubItem(item);
                    }
                } else {
                    summary.addSubItem(item);
                }
                if (i == jsonArray.length() - 1) {
                    summaryInfo(summary);
                    list.add(summary);
                } else {
                    lastItem = item;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //******** 统计月份信息并设置给数据体 ********
    @SuppressLint("StringFormatMatches")
    private void summaryInfo(TradeRecordSummary summary) {
        double pay = 0, back = 0;
        for (TradeRecordDetail detail : summary.getSubItems()) {
            if ("03".equals(detail.getTradeType())) {
                back += Double.parseDouble(detail.getMoney());
            } else {
                pay += Double.parseDouble(detail.getMoney());
            }
        }
        DecimalFormat df = new java.text.DecimalFormat("#.00");
        summary.setTime(com.yunkahui.datacubeper.common.utils.TimeUtils.format("yyyy年MM月", summary.getSubItem(0).getTimeStamp()));
        summary.setMessage(String.format(CardDoctorApplication.getContext().getString(R.string.pay_back_format), String.valueOf(df.format(pay)), String.valueOf(df.format(back))));
    }
}
