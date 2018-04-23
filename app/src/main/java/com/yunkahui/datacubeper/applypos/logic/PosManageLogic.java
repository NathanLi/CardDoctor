package com.yunkahui.datacubeper.applypos.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/20.
 */

public class PosManageLogic {


    /**
     * POS管理获取用户POS信息
     */
    public void loadPosManageData(Context context, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).loadPosManageData(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }


}
