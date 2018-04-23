package com.yunkahui.datacubeper.test.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.CardTestItem;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/21.
 */

public class CardTestLogic {

    /**
     * 卡评测-获取评测价格
     */
    public void loadTestMoney(Context context, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("api_name_code","apistore-a12")
                .create();
        HttpManager.getInstance().create(ApiService.class).loadTestMoney(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }


    /**
     * 卡测评-发起测评
     */
    public void submitCardTest(Context context,String name,String idCard,String bankCardNumber,String phone,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("cardholder",name)
                .addParams("identify_id",idCard)
                .addParams("bankcard_num",bankCardNumber)
                .addParams("bankcard_tel",phone)
                .create();
        HttpManager.getInstance().create(ApiService.class).submitCardTest(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 查询用户测评过的卡片列表
     */
    public void loadTestCardList(Context context,SimpleCallBack<BaseBean<List<CardTestItem>>> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("api_name_code","apistore-a12")
                .create();
        HttpManager.getInstance().create(ApiService.class).loadTestCardList(params)
                .compose(HttpManager.<BaseBean<List<CardTestItem>>>applySchedulers()).subscribe(callBack);
    }



}
