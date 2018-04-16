package com.yunkahui.datacubeper.bill.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BaseBeanList;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

public class BillLogic {

    public void queryCreditCardList(Context context, SimpleCallBack<BaseBean<BillCreditCard>> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).queryCreditCardList(params)
                .compose(HttpManager.<BaseBean<BillCreditCard>>applySchedulers()).subscribe(callBack);
    }

    public void queryCardCountOflanFailed(Context context, SimpleCallBack<JsonObject> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).queryCardCountOfPlanFailed(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);
    }
}
