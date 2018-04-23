package com.yunkahui.datacubeper.mine.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BankCard;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/13.
 */

public class MyCashCardListLogic {

    /**
     * 查询储蓄卡
     */
    public void checkCashCard(Context context, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).checkCashCard(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 删除储蓄卡
     */
    public void deleteCashCard(Context context, int id, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("bankcard_id",id)
                .addParams("bankcard_type","11")
                .create();
        HttpManager.getInstance().create(ApiService.class).deleteBankCard(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }



}
