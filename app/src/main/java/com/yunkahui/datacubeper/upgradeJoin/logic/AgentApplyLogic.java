package com.yunkahui.datacubeper.upgradeJoin.logic;

import android.content.Context;
import android.text.TextUtils;

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

public class AgentApplyLogic {


    /**
     * 查询代理商申请类型昵称
     */
    public void loadAgentNickName(Context context, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).loadAgentNickName(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 提交代理商或OEM申请
     */
    public void submitAgentApply(Context context,String type,String name,String phone,String weixin,String province
            ,String city, String remark,SimpleCallBack<BaseBean> callBack){
        RequestUtils.InnerParam param= RequestUtils.newParams(context)
                .addParams("apply_type",type)
                .addParams("username",name)
                .addParams("tel",phone)
                .addParams("province",province)
                .addParams("city",city);
        if(!TextUtils.isEmpty(weixin)){
            param.addParams("wechat",weixin);
        }
        if(!TextUtils.isEmpty(remark)){
            param.addParams("desc",remark);
        }
        Map<String,String> params=param.create();
        HttpManager.getInstance().create(ApiService.class).submitAgentApply(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);

    }




}
