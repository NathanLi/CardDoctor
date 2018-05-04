package com.yunkahui.datacubeper.share.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

/**
 * Created by YD1 on 2018/4/11
 */
public class ShareLogic {

    //******** 获取分享页面数据 ********
    public void requestSharePageInfo(Context context, SimpleCallBack<BaseBean> callBack){
        HttpManager.getInstance().create(ApiService.class)
                .loadSharePageInfo(RequestUtils.newParams(context).create())
                .compose(HttpManager.<BaseBean>applySchedulers())
                .subscribe(callBack);
    }

    //******** 生产激活码 ********
    public void createActivationCode(Context context, SimpleCallBack<BaseBean> callBack){
        HttpManager.getInstance().create(ApiService.class)
                .loadActivationCode(RequestUtils.newParams(context).create())
                .compose(HttpManager.<BaseBean>applySchedulers())
                .subscribe(callBack);
    }
}
