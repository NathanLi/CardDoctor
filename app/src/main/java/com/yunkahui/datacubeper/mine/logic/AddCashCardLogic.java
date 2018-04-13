package com.yunkahui.datacubeper.mine.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/13.
 */

public class AddCashCardLogic {

    /**
     * 查询信用卡所属银行
     */
    public void checkBankCardName(Context context, String card, SimpleCallBack<JsonObject> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("bank_card_num",card)
                .create();
        HttpManager.getInstance().create(ApiService.class).checkBankCardName(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);
    }

    /**
     * 添加储蓄卡
     */
    public void addCashCard(Context context,String card,String bankName,String bankNameBin,String phone,SimpleCallBack<JsonObject> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("bankcard_num",card)
                .addParams("bankcard_name",bankName)
                .addParams("bankcard_bin",bankNameBin)
                .addParams("bankcard_tel",phone)
                .create();
        HttpManager.getInstance().create(ApiService.class).addCashCard(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);
    }



}
