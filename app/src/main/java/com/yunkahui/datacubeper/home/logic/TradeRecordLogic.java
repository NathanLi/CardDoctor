package com.yunkahui.datacubeper.home.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.RechargeRecord;
import com.yunkahui.datacubeper.common.bean.WithdrawRecord;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

public class TradeRecordLogic {

    public void getRechargeRecord(Context context, String pdType, int pageSize, int pageNum, SimpleCallBack<BaseBean<RechargeRecord>> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("pdType", pdType)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .create();
        HttpManager.getInstance().create(ApiService.class).loadRechargeRecord(params)
                .compose(HttpManager.<BaseBean<RechargeRecord>>applySchedulers()).subscribe(callBack);

    }

    public void getWithdrawRecord(Context context, String pdType, int pageSize, int pageNum, SimpleCallBack<BaseBean<WithdrawRecord>> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("pdType", pdType)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .create();
        HttpManager.getInstance().create(ApiService.class).loadWithdrawRecord(params)
                .compose(HttpManager.<BaseBean<WithdrawRecord>>applySchedulers()).subscribe(callBack);

    }

    public void getTradeDetail(Context context, int pageSize, int pageNum, String checkType, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .addParams("check_type", checkType)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadTradeDetail(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);

    }
}
