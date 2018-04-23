package com.yunkahui.datacubeper.bill.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.GeneratePlan;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

public class AutoPlanLogic {

    public void confirmSmartPlan(Context context, int cardId, String planDatas, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("bankcard_id",String.valueOf(cardId))
                .addParams("planning_datas",planDatas)
                .addParams("batch_sn","1")
                .addParams("version","v1.1")
                .create();
        HttpManager.getInstance().create(ApiService.class).confirmAutoPlan(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    public void generateSmartPlan(Context context, int cardId, String totalMoney, String repayDates, String totalCount, SimpleCallBack<BaseBean<GeneratePlan>> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("bank_card_id",String.valueOf(cardId))
                .addParams("repay_total_money",totalMoney)
                .addParams("repay_dates",repayDates)
                .addParams("repay_total_count",totalCount)
                .addParams("version","v1.1")
                .create();
        HttpManager.getInstance().create(ApiService.class).generateAutoPlan(params)
                .compose(HttpManager.<BaseBean<GeneratePlan>>applySchedulers()).subscribe(callBack);
    }
}
