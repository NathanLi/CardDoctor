package com.yunkahui.datacubeper.common.api;

import com.google.gson.JsonObject;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.PersonalInfo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

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

    @FormUrlEncoded
    @POST("/app/user/login")    //用户登陆
    Observable<JsonObject> login(@FieldMap Map<String,String> params);


    @FormUrlEncoded
    @POST("/app/user/get_psn_info")   //获取个人中心信息
    Observable<BaseBean<PersonalInfo>> loadPersonalInformation(@FieldMap Map<String,String> params);

    @Multipart
    @POST("/app/user/uploadAvatar")     //上传头像
    Observable<JsonObject> upLoadPersonalAvatar(@PartMap Map<String,RequestBody> params, @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @POST("/app/user/setnewpsw")        //修改密码
    Observable<JsonObject> editPassword(@FieldMap Map<String,String> params);


    @FormUrlEncoded
    @POST("/app/share/get_myshare_infos")   //获取分享页面数据
    Observable<JsonObject> requestSharePageInfo(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/activationCodes/generate")   //生成激活码
    Observable<JsonObject> produceActivationCode(@FieldMap Map<String,String> params);
}
