package com.yunkahui.datacubeper.share.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

public class WithdrawForZFBLogic {

    //******** 提现 ********
    public void withdrawMoney(Context context, String bankCardId, String withdrawMoney, String withdrawType, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("bankcard_id", bankCardId)
                .addParams("withdrawAmount", withdrawMoney)
                .addParams("withdrawType", withdrawType)
                .create();
        HttpManager.getInstance().create(ApiService.class).withdrawMoney(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    //******** 查询提现手续费 ********
    public void queryWithdrawFee(Context context, float withdrawAmount, String withdrawType, SimpleCallBack<BaseBean> callBack) {
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("withdrawAmount", String.valueOf(withdrawAmount))
                .addParams("withdrawType", withdrawType)
                .create();
        HttpManager.getInstance().create(ApiService.class).queryWithdrawFee(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    public void requestSharePageInfo(Context context, SimpleCallBack<BaseBean> callBack){
        HttpManager.getInstance().create(ApiService.class)
                .loadSharePageInfo(RequestUtils.newParams(context).create())
                .compose(HttpManager.<BaseBean>applySchedulers())
                .subscribe(callBack);
    }
}
