package com.yunkahui.datacubeper.applypos.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.PosApplyInfo;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/20.
 */

public class PosManageLogic {


    /**
     * POS管理获取用户POS信息
     */
    public void loadPosManageData(Context context, SimpleCallBack<BaseBean<PosApplyInfo>> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).loadPosManageData(params)
                .compose(HttpManager.<BaseBean<PosApplyInfo>>applySchedulers()).subscribe(callBack);
    }

    /**
     * POS管理，修改申请人手机号
     */
    public void updatePosApplyPhone(Context context,String phone,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("faren_phone",phone)
                .create();
        HttpManager.getInstance().create(ApiService.class).updatePosApplyPhone(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 查询POS申请状态
     */
    public void checkPosApplyStatus(Context context, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).checkPosApplyStatus(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 查询信用卡所属银行
     */
    public void checkBankCardName(Context context, String card, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("bank_card_num",card)
                .create();
        HttpManager.getInstance().create(ApiService.class).checkBankCardName(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

}
