package com.yunkahui.datacubeper.upgradeJoin.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBeanList;
import com.yunkahui.datacubeper.common.bean.VipPackage;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/11.
 */

public class UpgradeVipLogic {

    /**
     * 获取VIP会员套餐数据
     */
    public void loadData(Context context, SimpleCallBack<BaseBeanList<VipPackage>> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).loadVipPackageData(params)
                .compose(HttpManager.<BaseBeanList<VipPackage>>applySchedulers()).subscribe(callBack);
    }

}
