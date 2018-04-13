package com.yunkahui.datacubeper.upgradeJoin.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/12.
 */

public class UpgradeJoinLogic {

    /**
     * 查询用户是否已申请代理商或OEM
     */
    public void loadAgentIsApply(Context context, SimpleCallBack<JsonObject> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).loadAgentIsApply(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);

    }

}
