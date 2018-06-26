package com.yunkahui.datacubeper.mine.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/23.
 */

public class MyCreditCardLogic {

    /**
     * 获取信用卡列表
     */
    public void queryCreditCardList(Context context, SimpleCallBack<BaseBean<BillCreditCard>> callBack) {
        Map<String, String> params = RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).queryCreditCardList(params)
                .compose(HttpManager.<BaseBean<BillCreditCard>>applySchedulers()).subscribe(callBack);
    }

    /**
     * 删除信用卡
     */
    public void deleteCreditCard(Context context, int id, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("bankcard_id", id)
                .addParams("bankcard_type", "00")
                .create();
        HttpManager.getInstance().create(ApiService.class).deleteBankCard(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

}
