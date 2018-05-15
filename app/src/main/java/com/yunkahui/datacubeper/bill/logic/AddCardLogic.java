package com.yunkahui.datacubeper.bill.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

public class AddCardLogic {

    public void queryBankByCardId(Context context, String bankCardNum, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("bank_card_num", bankCardNum)
                .create();
        HttpManager.getInstance().create(ApiService.class).queryBankByCardId(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    public void addBankCard(Context context, String cardNum, String cardName, String cardBin, String cardHolder,
                            int billDay, int repayDay, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("bankcard_num", cardNum)
                .addParams("bankcard_name", cardName)
                .addParams("bankcard_bin", cardBin)
                .addParams("cardholder", cardHolder)
                .addParams("bill_day", String.valueOf(billDay))
                .addParams("repay_day_date", String.valueOf(repayDay))
                .create();
        HttpManager.getInstance().create(ApiService.class).addBankCard(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    //修改信用卡
    public void editCard(Context context, String cardNum, String bankCardName, String name, int billDay, int repayDay, int cardId, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("bankcard_num", cardNum)
                .addParams("bankcard_name", bankCardName)
                .addParams("cardholder", name)
                .addParams("bill_day", billDay)
                .addParams("repay_day_date", repayDay)
                .addParams("userbankcard_id", cardId)
                .create();
        HttpManager.getInstance().create(ApiService.class).editCard(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    //修改信用卡（只修改账单日还款日）
    public void editCard2(Context context, int billDay, int repayDay, int cardId, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("bill_day", billDay)
                .addParams("repay_day_date", repayDay)
                .addParams("userbankcard_id", cardId)
                .create();
        HttpManager.getInstance().create(ApiService.class).editCard2(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

}
