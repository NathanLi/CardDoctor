package com.yunkahui.datacubeper.share.logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.CardDoctorApplication;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.Records;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.bean.TradeRecordSummary;
import com.yunkahui.datacubeper.common.bean.WithdrawRecord;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/7
 */

public class RecordListLogic {

    private JSONArray jsonArrayData;

    //统计收入/支出
    public void loadStatisticalMoney(Context context, String year, String month,
                                     String accountType, String statistType, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("y_month", year + "-" + month)
                .addParams("account_type", accountType)
                .addParams("static_type", statistType)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadStatisticalMoney(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    //统计总提现金额
    public void loadStatisticalWithdrawMoney(Context context, String year, String month,
                                     String accountType, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("y_month", year + "-" + month)
                .addParams("account_type", accountType)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadStatisticalWithdrawMoney(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    //获取积分数据
    public void loadIntegealData(Context context, int pageSize, int pageNum, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("pageSize", pageSize)
                .addParams("pageNum", pageNum)
                .addParams("type", "expend")
                .create();
        HttpManager.getInstance().create(ApiService.class).loadIntegralData(params)
                .compose(HttpManager.<BaseBean<Records>>applySchedulers()).subscribe(callBack);
    }

    //******** 获取全部明细（余额充值和提现） ********
    public void loadTradeDetail(Context context, String checkType, int pageSize, int pageNum,
                                long startTime, long endTime, String detailType, SimpleCallBack<BaseBean> callBack) {
        RequestUtils.InnerParam innerParam = RequestUtils.newParams(context)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .addParams("check_type", checkType)
                .addParams("detail_type", detailType);
        if (startTime > 0 && endTime > 0) {
            innerParam.addParams("begin_time", startTime);
            innerParam.addParams("end_time", endTime);
        }
        Map<String, String> params = innerParam.create();
        HttpManager.getInstance().create(ApiService.class).loadTradeDetail(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    //******** 获取提现记录 ********
    public void loadWithdrawRecord(Context context, String type, int pageSize, int pageNum, long startTime, long endTime, SimpleCallBack<BaseBean<WithdrawRecord>> callBack) {
        RequestUtils.InnerParam innerParam = RequestUtils.newParams(context)
                .addParams("with_draw_type", type)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum));
        if (startTime > 0) {
            innerParam.addParams("begin_time", startTime);
            innerParam.addParams("end_time", endTime);
        }
        Map<String, String> params = innerParam.create();
        HttpManager.getInstance().create(ApiService.class).loadWithdrawOrderRecord(params)
                .compose(HttpManager.<BaseBean<WithdrawRecord>>applySchedulers()).subscribe(callBack);

    }

    //POS分润收入
    public void loadPosFenRunData(Context context, String type, int pageSize, int pageNum,
                                  long startTime, long endTime, SimpleCallBack<BaseBean> callBack) {
        RequestUtils.InnerParam innerParam = RequestUtils.newParams(context)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .addParams("type", type);
        if (startTime > 0) {
            innerParam.addParams("begin_time", startTime);
            innerParam.addParams("end_time", endTime);
        }
        Map<String, String> params = innerParam.create();
        HttpManager.getInstance().create(ApiService.class).loadPosFenRunData(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    public void update() {
        jsonArrayData = null;
    }

    public List<MultiItemEntity> parsingJSONForAll(BaseBean baseBean) {
        List<MultiItemEntity> list = null;
        try {
            list = new ArrayList<>();
            JSONObject object = baseBean.getJsonObject();
            JSONObject respData = object.optJSONObject("respData");
            JSONArray jsonArray = respData.optJSONArray("list");

            if (jsonArrayData == null) {
                jsonArrayData = new JSONArray(jsonArray.toString());
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonArrayData.put(jsonArray.optJSONObject(i));
                }
            }

            TradeRecordDetail item;
            TradeRecordSummary summary = new TradeRecordSummary();
            TradeRecordDetail lastItem = null;
            for (int i = 0; i < jsonArrayData.length(); i++) {
                JSONObject j = new JSONObject(jsonArrayData.opt(i).toString());
                item = new TradeRecordDetail();
                item.setTimeStamp(j.optLong("create_time"));
                item.setTradeType(j.optString("trade_type"));
                item.setTime(com.yunkahui.datacubeper.common.utils.TimeUtils.format("MM-dd hh:mm", j.optLong("create_time")));
                item.setMoney(j.optString("change_amount") + "");
                item.setTitle(j.optString("trade_type_desc"));
                item.setRemark(j.optString("third_party_msg"));

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
                if (i == jsonArrayData.length() - 1) {
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
            double money = Double.parseDouble(detail.getMoney());
            if (money > 0) {
                back += Double.parseDouble(detail.getMoney());
            } else {
                pay += Double.parseDouble(detail.getMoney());
            }
        }
        DecimalFormat df = new java.text.DecimalFormat("0.00");
        summary.setTime(com.yunkahui.datacubeper.common.utils.TimeUtils.format("yyyy-MM", summary.getSubItem(0).getTimeStamp()));
        summary.setBack(df.format(back));
        summary.setPay(df.format(pay).substring(1));
        summary.setMessage(String.format(CardDoctorApplication.getContext().getString(R.string.pay_back_format), df.format(back), df.format(pay)));
        summary.setYear(TimeUtils.format("yyyy", summary.getSubItem(0).getTimeStamp()));
        summary.setMonth(TimeUtils.format("MM", summary.getSubItem(0).getTimeStamp()));
    }

    public List<MultiItemEntity> parseJsonForTradeWithdraw(List<WithdrawRecord.WithdrawDetail> withdrawDetails) {
        List<MultiItemEntity> list = new ArrayList<>();
        List<WithdrawRecord.WithdrawDetail> detailList = withdrawDetails;
        TradeRecordSummary summary = new TradeRecordSummary();
        TradeRecordDetail item;
        String date = null;
        for (int i = 0; i < detailList.size(); i++) {
            String substring = TimeUtils.format("yyyy-MM-dd HH:mm:ss", detailList.get(i).getCreate_time()).substring(0, 7);
            if (!substring.equals(date)) {
                list.add(summary);
                summary = new TradeRecordSummary();
                summary.setTime(substring.substring(0, 4) + "-" + substring.substring(5));
                summary.setYear(substring.substring(0, 4));
                summary.setMonth(substring.substring(5));
            }
            item = new TradeRecordDetail();
            item.setTitle(detailList.get(i).getDescr());
            item.setTime(TimeUtils.format("yyyy-MM-dd HH:mm:ss", detailList.get(i).getCreate_time()));
            item.setMoney(TextUtils.isEmpty(detailList.get(i).getWithdraw_amount()) ? detailList.get(i).getAmount() : detailList.get(i).getWithdraw_amount());
            item.setOrderStatus(detailList.get(i).getOrder_state());
            summary.addSubItem(item);
            date = TimeUtils.format("yyyy-MM-dd HH:mm:ss", detailList.get(i).getCreate_time()).substring(0, 7);
        }
        list.remove(0);
        list.add(summary);
        for (MultiItemEntity entity : list) {
            TradeRecordSummary s = (TradeRecordSummary) entity;
            double pay = 0;
            for (TradeRecordDetail detail : s.getSubItems()) {
                pay += Double.parseDouble(detail.getMoney());
            }
            DecimalFormat df = new java.text.DecimalFormat("0.00");
            s.setPay(df.format(pay));
        }
        return list;
    }
}
