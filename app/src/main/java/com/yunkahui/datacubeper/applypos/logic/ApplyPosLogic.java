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
 * Created by Administrator on 2018/4/18.
 */

public class ApplyPosLogic {


    /**
     * 查询用户POS申请已上传的资料
     */
    public void checkPosApplyUploadData(Context context, SimpleCallBack<BaseBean<PosApplyInfo>> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).checkPosApplyUploadData(params)
                .compose(HttpManager.<BaseBean<PosApplyInfo>>applySchedulers()).subscribe(callBack);
    }


    /**
     * 上传邮寄信息
     */
    public void upLoadMailInfo(Context context,String name,String phone,String address,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("receive_name",name)
                .addParams("receive_phone",phone)
                .addParams("receive_address",address)
                .create();
        HttpManager.getInstance().create(ApiService.class).upLoadMailInfo(params)
                .compose(HttpManager.<BaseBean>applySchedulers())
                .subscribe(callBack);
    }

    /**
     * 上传终端信息
     */
    public void upLoadTerminalInfo(Context context,String name,String idCard,String phone,String area,String address,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("faren_name",name)
                .addParams("faren_certificate_number",idCard)
                .addParams("faren_phone",phone)
                .addParams("region",area)
                .addParams("manage_addr",address)
                .create();
        HttpManager.getInstance().create(ApiService.class).upLoadTerminalInfo(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 上传结算信息
     */
    public void upLoadSettleInfo(Context context,String bankCardNumber,String bankCardName,String province,String city,String branchName,String branchNumber,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("account_number",bankCardNumber)
                .addParams("account_bank_name",bankCardName)
                .addParams("deposit_bank",branchName)
                .addParams("deposit_province",province)
                .addParams("deposit_city",city)
                .addParams("tua_cnaps",branchNumber)
                .create();
        HttpManager.getInstance().create(ApiService.class).upLoadSettleInfo(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 把审核状态由已付款修改为审核中
     */
    public void changePosApplyStatus(Context context,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).changePosApplyStatus(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }


    /**
     * 查询POS已邮寄信息
     */
    public void checkHaveMailInfo(Context context,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).checkHaveMailInfo(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }



}
