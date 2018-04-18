package com.yunkahui.datacubeper.home.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BaseBeanList;
import com.yunkahui.datacubeper.common.bean.SmartPlanSub;
import com.yunkahui.datacubeper.common.bean.TodayOperationSub;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by YD1 on 2018/4/12
 */
public class DesignSubLogic {

    public void requestTodayOperation(Context context, String isPos, String num, String page, SimpleCallBack<BaseBean<TodayOperationSub>> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("is_pos", isPos)
                .addParams("num", num)
                .addParams("page", page)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadTodayOperation(params)
                .compose(HttpManager.<BaseBean<TodayOperationSub>>applySchedulers()).subscribe(callBack);

    }

    public void requestSmartPlan(Context context, String isPos, String num, String page, SimpleCallBack<BaseBeanList<SmartPlanSub>> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("is_pos", isPos)
                .addParams("pageNum", num)
                .addParams("pageSize", page)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadSmartPlan(params)
                .compose(HttpManager.<BaseBeanList<SmartPlanSub>>applySchedulers()).subscribe(callBack);

    }

    public void requestSP(Context context, String isPos, String num, String page, SimpleCallBack<JsonObject> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("is_pos", isPos)
                .addParams("pageNum", num)
                .addParams("pageSize", page)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadSP(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);

    }

    public void requestTO(Context context, String isPos, String num, String page, SimpleCallBack<JsonObject> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("is_pos", isPos)
                .addParams("pageNum", num)
                .addParams("pageSize", page)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadTO(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);

    }
}
