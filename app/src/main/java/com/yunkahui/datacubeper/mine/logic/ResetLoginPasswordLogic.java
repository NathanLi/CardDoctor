package com.yunkahui.datacubeper.mine.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/11.
 */

public class ResetLoginPasswordLogic {

    /**
     * 发送短信（修改密码）
     */
    public void sendSMS(Context context, String phone, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("type","2")
                .addParams("user_mobile",phone)
                .create();
        HttpManager.getInstance().create(ApiService.class).sendSMS(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 修改密码
     */
    public void editPassword(Context context,String phone,String oldPassword,String newPassword,String code,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("type","2")
                .addParams("user_mobile",phone)
                .addParams("oldpassword",oldPassword)
                .addParams("password",newPassword)
                .addParams("user_mobile_code",code)
                .create();
        HttpManager.getInstance().create(ApiService.class).editPassword(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);

    }


}
