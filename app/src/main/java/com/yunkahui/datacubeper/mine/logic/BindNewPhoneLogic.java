package com.yunkahui.datacubeper.mine.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/24.
 */

public class BindNewPhoneLogic {

    /**
     * 发送短信
     */
    public void sendSMS(Context context, String phone, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("user_mobile",phone)
                .addParams("type","3")
                .create();
        HttpManager.getInstance().create(ApiService.class).sendSMS(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 绑定新手机
     */
    public void bindNewPhone(Context context,String phone,String code,SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("new_mobile", phone)
                .addParams("user_mobile_code", code)
                .create();
        HttpManager.getInstance().create(ApiService.class).bindNewPhone(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

}
