package com.yunkahui.datacubeper.bill.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

public class BillLogic {

    public void queryCreditCardList(Context context, SimpleCallBack<JsonObject> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).queryCreditCardList(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);
    }

    public void queryCardCountOflanFailed(Context context, SimpleCallBack<JsonObject> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).queryCardCountOflanFailed(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);
    }
}
