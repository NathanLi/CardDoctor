package com.yunkahui.datacubeper.bill.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * @author WYF on 2018/4/24/024.
 */
public class AddTradeLogic {

    public void getMccList(Context context, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).loadMccList(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    public void createBill(Context context, int bankCardId, String tradeType, long tradeDate, String tradeAmount
            , String mccType, String requestNo, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("user_bankcard_id", String.valueOf(bankCardId))
                .addParams("trade_type", tradeType)
                .addParams("trade_date", String.valueOf(tradeDate))
                .addParams("trade_amount", tradeAmount)
                .addParams("mcc_type", mccType)
                .addParams("request_no", "12345123451234512345")
                .create();
        HttpManager.getInstance().create(ApiService.class).requestCreateBill(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }
}
