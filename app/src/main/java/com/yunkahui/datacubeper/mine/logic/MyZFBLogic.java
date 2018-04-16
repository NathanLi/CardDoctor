package com.yunkahui.datacubeper.mine.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/16.
 */

public class MyZFBLogic {

    /**
     * 检查用户是否绑定支付宝
     */
    public void checkUserZFB(Context context, SimpleCallBack<JsonObject> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).checkUserBindZFB(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);
    }

    /**
     * 绑定支付宝账号
     */
    public void bindZFB(Context context,String account,String name,SimpleCallBack<JsonObject> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("alipay_account",account)
                .addParams("aplipay_true_name",name)
                .create();
        HttpManager.getInstance().create(ApiService.class).bindZFB(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);
    }

    /**
     * 解绑支付宝账号
     */
    public void unBindZFB(Context context,SimpleCallBack<JsonObject> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).unBindZFB(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);
    }


}
