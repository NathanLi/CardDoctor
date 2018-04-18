package com.yunkahui.datacubeper.common.api;

import com.google.gson.JsonObject;
import com.yunkahui.datacubeper.common.bean.BankCard;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BaseBeanList;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.Message;
import com.yunkahui.datacubeper.common.bean.MessageGroup;
import com.yunkahui.datacubeper.common.bean.SmartPlanSub;
import com.yunkahui.datacubeper.common.bean.TodayOperationSub;
import com.yunkahui.datacubeper.common.bean.PersonalInfo;
import com.yunkahui.datacubeper.common.bean.VipPackage;

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
    Observable<JsonObject> loadSharePageInfo(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/activationCodes/generate")   //生成激活码
    Observable<JsonObject> loadActivationCode(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/vip/package")     //获取VIP会员套餐数据
    Observable<BaseBeanList<VipPackage>> loadVipPackageData(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/vip/create_order")     //创建Vip付款订单，获取支付信息
    Observable<JsonObject> createOrderPayVip(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/upgrade_vip_create_order")     //获取支付开通数据
    Observable<JsonObject> updatePayInfo(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/payment/active_code_to_vip")       //使用激活码升级
    Observable<JsonObject> updateVipByActivateCode(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userUpgradec/upgradeApplyRepeat")   //查询用户是否已经申请代理商或OEM
    Observable<JsonObject> loadAgentIsApply(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/share/check_agent_nickname")        //查询代理商申请类型昵称
    Observable<JsonObject> loadAgentNickName(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userUpgradec/upgradeApply")     //提交代理商或OEM申请
    Observable<JsonObject> submitAgentApply(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/planing/getToday")     //查询今日操作
    Observable<BaseBean<TodayOperationSub>> loadTodayOperation(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/planing/getPlanning")     //查询智能规划
    Observable<BaseBeanList<SmartPlanSub>> loadSmartPlan(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/planing/getToday")     //查询今日操作
    Observable<JsonObject> loadTO(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/planing/getPlanning")     //查询智能规划
    Observable<JsonObject> loadSP(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/checkCard")    //查询信用卡所属银行
    Observable<JsonObject> checkBankCardName(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/bind_deposit_card")    //添加储蓄卡
    Observable<JsonObject> addCashCard(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/check_deposit_card")   //查询储蓄卡
    Observable<JsonObject> checkCashCard(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/dropcard")     //删除银行卡  （信用卡和储蓄卡）
    Observable<JsonObject> deleteBankCard(@FieldMap Map<String,String> params);

    @Multipart
    @POST("/app/user/uploadIdentify")     //实名认证  (上传身份证正反面)
    Observable<JsonObject> submitRealNameAuthImage(@PartMap Map<String,RequestBody> params, @Part MultipartBody.Part front,@Part MultipartBody.Part back);

    @FormUrlEncoded
    @POST("/app/user/certification_v2")
    Observable<JsonObject> submitRealNameAuthInfo(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/check_identify")       //查询用户实名认证状态
    Observable<JsonObject> checkRealNameAuthStatus(@FieldMap Map<String,String> params);

    @POST("/app/userbankcard/checkCard")     //查询银行卡号所属银行
    Observable<JsonObject> queryBankByCardId(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user_bankcard/add_userbankcard")     //添加信用卡
    Observable<JsonObject> addBankCard(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/card_detail")     //查询已添加的信用卡
    Observable<BaseBean<BillCreditCard>> queryCreditCardList(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/planning/check_fail_count")     //查询存在规划失败的卡片总数
    Observable<JsonObject> queryCardCountOfPlanFailed(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/check_user_alipay")        //检查用户是否绑定支付宝
    Observable<JsonObject> checkUserBindZFB(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/bind_user_alipay")     //绑定支付宝账号
    Observable<JsonObject> bindZFB(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/unbind_user_alipay")       //解绑支付宝账号
    Observable<JsonObject> unBindZFB(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/news/selectSysNews")        //根据时间查询新增的消息
    Observable<JsonObject> checkNewMessage(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/news/selectAysNewsAll")     //根据时间分页查询消息
    Observable<BaseBean<MessageGroup>> checkNewMessageList(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/news/selectNewsByNewId")       //根据ID查询消息
    Observable<BaseBeanList<Message>> checkNewMessageById(@FieldMap Map<String,String> params);

    @POST("/app/pos/planning/update_planning_info")       //更新规划数据
    Observable<JsonObject> updatePlanningInfo(@FieldMap Map<String,String> params);
}
