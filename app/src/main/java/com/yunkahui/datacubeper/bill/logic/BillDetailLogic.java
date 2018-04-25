package com.yunkahui.datacubeper.bill.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.CustomConverterFactory;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

public class BillDetailLogic {

    public void getBillDetailTop(Context context, int cardId, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("user_credit_card_id", String.valueOf(cardId))
                .create();
        HttpManager.getInstance().create(ApiService.class).loadBillDetailTop(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    public void getBillDetail(Context context, int cardId, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("user_credit_card_id", String.valueOf(cardId))
                .create();
        //HttpManager.getInstance().addConverterFactory(CustomConverterFactory.create()).newBuilder().baseUrl("http://192.168.1.141:8014").build()
                //.create(ApiService.class).loadBillDetail(params).compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
        HttpManager.getInstance().create(ApiService.class).loadBillDetail(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    public void signRepaid(Context context, int cardId, int status, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("bank_card_id", String.valueOf(cardId))
                .addParams("repay_status", status)
                .create();
        HttpManager.getInstance().create(ApiService.class).requestSignRepaid(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }
}
