package com.yunkahui.datacubeper.home.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.utils.CustomConverterFactory;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

public class RechargeLogic {

    public void queryCreditCardList(Context context, SimpleCallBack<BaseBean<BillCreditCard>> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).queryCreditCardList(params)
                .compose(HttpManager.<BaseBean<BillCreditCard>>applySchedulers()).subscribe(callBack);
    }

    public void rechargeMoney(Context context, String bankCardId, String rechargeMoney, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("bankcard_id", bankCardId)
                .addParams("recharge_amount", rechargeMoney)
                .create();
        HttpManager.getInstance().create(ApiService.class).rechargeMoney(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }
}
