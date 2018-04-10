package com.yunkahui.datacubeper.login.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.PATCH;

/**
 * Created by Administrator on 2018/4/8.
 */

public class RegisterLogic {

    /**
     * 发送短信
     */
    public void sendSMS(Context context,String phone,SimpleCallBack<JsonObject> callBack){
            Map<String,String> params=RequestUtils.newParams(context)
                    .addParams("user_mobile",phone)
                    .addParams("type","0")
                    .create();
            HttpManager.getInstance().create(ApiService.class).sendSMS(params)
                    .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);
    }

    /**
     * 验证 验证码
     */
    public void checkSMSCode(Context context,String phone,String code,SimpleCallBack<JsonObject> callBack){
            Map<String,String> params=RequestUtils.newParams(context)
                    .addParams("user_mobile",phone)
                    .addParams("user_mobile_code",code)
                    .addParams("type","0")
                    .create();
            HttpManager.getInstance().create(ApiService.class).checkSMSCode(params)
                    .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);
    }


    /**
     * 注册
     * @param phone 手机号
     * @param nickName  昵称
     * @param password  密码
     * @param inviteCode 邀请码
     */
    public void register(Context context,String phone,String nickName,String password,String inviteCode,SimpleCallBack<JsonObject> callBack){

            Map<String,String> params=RequestUtils.newParams(context)
                    .addParams("user_mobile",phone)
                    .addParams("user_nickname",nickName)
                    .addParams("user_password",password)
                    .addParams("user_unique_code",inviteCode)
                    .create();

            HttpManager.getInstance().create(ApiService.class).registerUser(params)
                    .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(callBack);

    }



}
