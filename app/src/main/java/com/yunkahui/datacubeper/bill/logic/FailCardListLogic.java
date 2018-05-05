package com.yunkahui.datacubeper.bill.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/5.
 */

public class FailCardListLogic {

    /**
     * 获取规划失败卡片列表
     */
    public void loadFailCardList(Context context, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).loadFailCardList(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }


}
