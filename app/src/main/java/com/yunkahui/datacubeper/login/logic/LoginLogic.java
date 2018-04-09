package com.yunkahui.datacubeper.login.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/3.
 */

public class LoginLogic {


    public void login(Context context, String phone, String password, SimpleCallBack<JsonObject> callBack){

        Map<String,String> params=RequestUtils.newParams()
                .addParams("user_mobile",phone)
                .addParams("user_password",password)
                .addParams("org_number",context.getResources().getString(R.string.org_number))
                .create();
        HttpManager.getInstance().create(ApiService.class).login(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);

    }


}
