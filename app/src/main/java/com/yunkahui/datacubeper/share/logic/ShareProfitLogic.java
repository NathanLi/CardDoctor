package com.yunkahui.datacubeper.share.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

public class ShareProfitLogic {

    //******** 获取储蓄卡 ********
    public void checkCashCard(Context context, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).checkCashCard(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }
}
