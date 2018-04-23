package com.yunkahui.datacubeper.bill.ui;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

public class PlanPickerLogic {

    public void getMccList(Context context, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).loadMccList(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);

    }

    public void confirmPlan(Context context, String cardId, String planDatas, String requestNo, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("bank_card_id",cardId)
                .addParams("planning_datas",planDatas)
                .addParams("request_no",requestNo)
                .create();
        HttpManager.getInstance().create(ApiService.class).confirmPlan(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    public void generatePlan(Context context, int cardId, String totalMoney, String repayDates, String totalCount, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("bankcard_id",String.valueOf(cardId))
                .addParams("repay_total_money",totalMoney)
                .addParams("repay_dates",repayDates)
                .addParams("repay_total_count",totalCount)
                .create();
        HttpManager.getInstance().create(ApiService.class).generatePlan(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }
}
