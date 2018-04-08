package com.yunkahui.datacubeper.common.api;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/4/8.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("/app/user/getMoblieCode") //发送短信 type  0：注册  1：绑定手机  2:修改密码  3:更改绑定手机  4：实名认证
    Observable<JsonObject> sendSMS(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/CheckPhoneCode")   //短信验证
    Observable<JsonObject> checkSMSCode(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/signup")   //用户注册
    Observable<JsonObject> registerUser(@FieldMap Map<String,String> params);

}
