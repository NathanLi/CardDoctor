package com.yunkahui.datacubeper.applypos.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.MultipartUtil;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.api.BaseUrl;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/4/19.
 */

public class UpLoadImageLogic {


    /**
     * 上传图片
     */
    public void upLoadImageFile(Context context, String type, File file,SimpleCallBack<BaseBean> callBack){
        MultipartBody.Part part= MultipartUtil.makeMultpart("img",file);
        Map<String, RequestBody> params=MultipartUtil.newInstance()
                .addParam("user_code", BaseUrl.getUSER_ID())
                .addParam("key",BaseUrl.getKEY())
                .addParam("org_number",context.getResources().getString(R.string.org_number))
                .addParam("type",type)
                .Build();
        HttpManager.getInstance().create(ApiService.class).uploadImageFile(params,part)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 提交保存图片
     */
    public void commitSaveImage(Context context,String url,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("imgUrl",url)
                .create();
        HttpManager.getInstance().create(ApiService.class).commitSaveImage(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }


}
