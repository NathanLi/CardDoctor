package com.yunkahui.datacubeper.mine.logic;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.MultipartUtil;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.api.BaseUrl;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.view.chart.DealInterface;
import com.yunkahui.datacubeper.common.view.chart.Demo;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/4/13.
 */

public class RealNameAuthLogic {


    /**
     * 解析身份证，获取文字信息
     */
    public void parsingIdCard(Bitmap bitmap,DealInterface<String> stringDealInterface){
        Bitmap positionBitmap = bitmap;
        String mess = String.format("{\"inputs\":[{\"image\":{\"dataType\": 50,\"dataValue\":\"%s\"},\"configure\": {\"dataType\": 50,\"dataValue\": \"{\\\"side\\\":\\\"face\\\"}\"}}]}",
                bitmapToString(positionBitmap));
        Demo.identificationHttpTest(mess,stringDealInterface);
    }


    private String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
        return new String(encode);
    }

    /**
     * 实名认证(图片提交)
     */
    public void submitRealNameAuthImage(Context context, String front, String back, SimpleCallBack<BaseBean> callBack){
        Map<String, RequestBody> params= MultipartUtil.newInstance()
                .addParam("user_code", BaseUrl.getUSER_ID())
                .addParam("key",BaseUrl.getKEY())
                .addParam("org_number",context.getResources().getString(R.string.org_number))
                .Build();
        List<File> FrontFiles=new ArrayList<>();
        List<File> BackFiles=new ArrayList<>();
        File frontFile=new File(front);
        File backFile=new File(back);
        FrontFiles.add(frontFile);
        BackFiles.add(backFile);
        MultipartBody.Part frontPart=MultipartUtil.makeMultpart("card_front",FrontFiles).get(0);
        MultipartBody.Part backPart=MultipartUtil.makeMultpart("card_back",BackFiles).get(0);
        HttpManager.getInstance().create(ApiService.class).submitRealNameAuthImage(params,frontPart,backPart)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 实名认证（上传文字信息）
     */
    public void submitRealNameAuthInfo(Context context,String name,String idCardNumber,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("true_name",name)
                .addParams("id_card",idCardNumber)
                .create();
        HttpManager.getInstance().create(ApiService.class).submitRealNameAuthInfo(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }



}
