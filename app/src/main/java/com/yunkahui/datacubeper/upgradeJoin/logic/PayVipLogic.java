package com.yunkahui.datacubeper.upgradeJoin.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/12.
 */

public class PayVipLogic {

    /**
     * 获取可支持的支付信息
     */
    public void updatePayInfo(Context context,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .create();
        HttpManager.getInstance().create(ApiService.class).updatePayInfo(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 获取支付宝支付信息
     */
    public void payVip(Context context, String vipId,String type, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("vp_id",vipId)
                .addParams("payment_code",type)
                .create();
        HttpManager.getInstance().create(ApiService.class).createOrderPayVip(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 使用激活码升级
     */
    public void updateVipByActivateCode(Context context,String code,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("activation_code",code)
                .create();
        HttpManager.getInstance().create(ApiService.class).updateVipByActivateCode(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

}
