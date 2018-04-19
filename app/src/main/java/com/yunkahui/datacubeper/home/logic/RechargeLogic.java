package com.yunkahui.datacubeper.home.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

public class RechargeLogic {

    public void queryCreditCardList(Context context, SimpleCallBack<BaseBean<BillCreditCard>> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).queryCreditCardList(params)
                .compose(HttpManager.<BaseBean<BillCreditCard>>applySchedulers()).subscribe(callBack);
    }
}
