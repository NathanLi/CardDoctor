package com.yunkahui.datacubeper.share.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.Records;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/7.
 */

public class RecordListLogic {


    //获取积分数据
    public void loadIntegealData(Context context, int pageSize, int pageNum, String type, SimpleCallBack<BaseBean<Records>> callBack){
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("pageSize",pageSize)
                .addParams("pageNum",pageNum)
                .addParams("type",type)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadIntegralData(params)
                .compose(HttpManager.<BaseBean<Records>>applySchedulers()).subscribe(callBack);
    }

}
