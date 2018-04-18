package com.yunkahui.datacubeper.home.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

public class ExpenseAdjustLogic {

    public void updatePlanningInfo(Context context, String autoPlanningId, String amount, SimpleCallBack<JsonObject> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("auto_planning_id", autoPlanningId)
                .addParams("amount", amount)
                .create();
        HttpManager.getInstance().create(ApiService.class).updatePlanningInfo(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);

    }
}
