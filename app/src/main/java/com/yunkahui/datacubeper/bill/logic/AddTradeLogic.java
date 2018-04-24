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

    public void getMccList(Context context, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).loadMccList(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }
}
