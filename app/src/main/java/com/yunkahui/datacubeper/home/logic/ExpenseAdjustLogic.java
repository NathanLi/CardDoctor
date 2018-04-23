package com.yunkahui.datacubeper.home.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

public class ExpenseAdjustLogic {

    public void getMccList(Context context, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).loadMccList(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);

    }

    public void updatePlanningInfo(Context context, String autoPlanningId, String mccType, String amount, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("auto_planning_id", autoPlanningId)
                .addParams("mcc_type", mccType)
                .addParams("amount", amount)
                .create();
        HttpManager.getInstance().create(ApiService.class).updatePlanningInfo(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);

    }
}
