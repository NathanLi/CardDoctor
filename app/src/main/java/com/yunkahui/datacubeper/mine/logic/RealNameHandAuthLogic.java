package com.yunkahui.datacubeper.mine.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/17.
 */

public class RealNameHandAuthLogic {


    /**
     * 发送短信
     */
    public void sendSMS(Context context,String phone,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params=RequestUtils.newParams(context)
                .addParams("user_mobile",phone)
                .addParams("type",4)
                .create();
        HttpManager.getInstance().create(ApiService.class).sendSMS(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 手动实名认证（上传文字信息）
     */
    public void submitRealNameHandAuthInfo(Context context, String name, String idCard, String phone, String code, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("true_name", name)
                .addParams("id_card", idCard)
                .addParams("user_mobile", phone)
                .addParams("user_mobile_code", code)
                .create();
        HttpManager.getInstance().create(ApiService.class).submitRealNameHandAuthInfo(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }


}
