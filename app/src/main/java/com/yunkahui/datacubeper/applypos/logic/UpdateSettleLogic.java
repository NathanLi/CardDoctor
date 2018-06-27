package com.yunkahui.datacubeper.applypos.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/17.
 */

public class UpdateSettleLogic {

    /**
     * 修改结算信息
     */
    public void updataSettleData(Context context, String name, String cardNumber, String bankName, String branch, String province, String city, String branchNumber, String url, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("account_name", name)
                .addParams("account_number", cardNumber)
                .addParams("account_bank_name", bankName)
                .addParams("deposit_bank", branch)
                .addParams("deposit_province", province)
                .addParams("deposit_city", city)
                .addParams("tua_cnaps", branchNumber)
                .addParams("imgUrl", url)
                .create();
        HttpManager.getInstance().create(ApiService.class).updateSettleData(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);

    }

}
