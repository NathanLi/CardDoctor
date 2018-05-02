package com.yunkahui.datacubeper.login.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/9.
 */

public class ForgetPasswordLogic {

    /**
     * 发送短信
     */
    public void sendSMS(Context context, String phone, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("user_mobile", phone)
                .addParams("type", "6")
                .create();
        HttpManager.getInstance().create(ApiService.class).sendSMS(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 验证 验证码
     */
    public void checkSMSCode(Context context, String phone, String code, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("user_mobile", phone)
                .addParams("user_mobile_code", code)
                .addParams("type", "6")
                .create();
        HttpManager.getInstance().create(ApiService.class).checkSMSCode(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 忘记密码提交
     */
    public void submitForgetPassword(Context context, String phone, String password, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("user_mobile", phone)
                .addParams("password", password)
                .create();
        HttpManager.getInstance().create(ApiService.class).forgerPassword(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }


}
