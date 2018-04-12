package com.yunkahui.datacubeper.home.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by YD1 on 2018/4/12
 */
public class DesignSubLogic {

    public void requestTodayOperation(Context context, String isPos, String num, String page, SimpleCallBack<JsonObject> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("is_pos", isPos)
                .addParams("num", num)
                .addParams("page", page)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadTodayOperation(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);

    }
}
