package com.yunkahui.datacubeper.bill.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/4.
 */

public class OpenAutoPlanLogic {

    //鉴权发送短信
    public void bindCardSendSMS(Context context, String bankCardNumber, String phone, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("bankcard_num", bankCardNumber)
                .addParams("planning_tel", phone)
                .create();
        HttpManager.getInstance().create(ApiService.class).bindCardSendSMS(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    //信用卡鉴权
    public void authCreditCard(Context context, int bankCardId, String cvv2, long time, String phone, String authCode, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("bank_card_id", bankCardId)
                .addParams("cvv2", cvv2)
                .addParams("expiry_date", time + "")
                .addParams("bankcard_tel", phone)
                .addParams("bind_code", authCode)
                .create();
        HttpManager.getInstance().create(ApiService.class).authCreditCard(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }


}
