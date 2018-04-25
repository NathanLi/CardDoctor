package com.yunkahui.datacubeper.test.logic;

import android.content.Context;
import android.text.TextUtils;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.CardTestItem;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.List;
import java.util.Map;

import retrofit2.http.PATCH;

/**
 * Created by Administrator on 2018/4/24.
 */

public class TestHistoryLogic {

    /**
     * 查询用户测评过的所有记录列表
     */
    public void loadTestHistoryRecord(Context context, String bankCardNumber, SimpleCallBack<BaseBean<List<CardTestItem>>> callBack) {
        RequestUtils.InnerParam innerParam = RequestUtils.newParams(context)
                .addParams("api_name_code", "apistore-a12");
        if (!TextUtils.isEmpty(bankCardNumber)) {
            innerParam.addParams("bankcard_num", bankCardNumber);
        }
        Map<String, String> params = innerParam.create();
        HttpManager.getInstance().create(ApiService.class).loadTestRecord(params)
                .compose(HttpManager.<BaseBean<List<CardTestItem>>>applySchedulers()).subscribe(callBack);
    }

    /**
     * 查看测评记录详情
     */
    public void loadTestRecordDetail(Context context, int id, SimpleCallBack<BaseBean<CardTestItem>> callBack) {
        Map<String,String> params=RequestUtils.newParams(context)
                .addParams("apr_id",id)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadTestRecordDetail(params)
                .compose(HttpManager.<BaseBean<CardTestItem>>applySchedulers()).subscribe(callBack);
    }


}
