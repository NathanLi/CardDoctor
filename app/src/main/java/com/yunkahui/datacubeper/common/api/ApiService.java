package com.yunkahui.datacubeper.common.api;

import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.Branch;
import com.yunkahui.datacubeper.common.bean.CardTestItem;
import com.yunkahui.datacubeper.common.bean.GeneratePlan;
import com.yunkahui.datacubeper.common.bean.Member;
import com.yunkahui.datacubeper.common.bean.RechargeRecord;
import com.yunkahui.datacubeper.common.bean.Message;
import com.yunkahui.datacubeper.common.bean.MessageGroup;
import com.yunkahui.datacubeper.common.bean.PosApplyInfo;
import com.yunkahui.datacubeper.common.bean.SmartPlanSub;
import com.yunkahui.datacubeper.common.bean.TodayOperationSub;
import com.yunkahui.datacubeper.common.bean.PersonalInfo;
import com.yunkahui.datacubeper.common.bean.VipPackage;
import com.yunkahui.datacubeper.common.bean.WithdrawRecord;

import java.util.List;
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
    Observable<BaseBean> sendSMS(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/CheckPhoneCode")   //短信验证
    Observable<BaseBean> checkSMSCode(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/signup")   //用户注册
    Observable<BaseBean> registerUser(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/login")    //用户登陆
    Observable<BaseBean> login(@FieldMap Map<String,String> params);


    @FormUrlEncoded
    @POST("/app/user/get_psn_info")   //获取个人中心信息
    Observable<BaseBean<PersonalInfo>> loadPersonalInformation(@FieldMap Map<String,String> params);

    @Multipart
    @POST("/app/user/uploadAvatar")     //上传头像
    Observable<BaseBean> upLoadPersonalAvatar(@PartMap Map<String,RequestBody> params, @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @POST("/app/user/setnewpsw")        //修改密码
    Observable<BaseBean> editPassword(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/share/get_myshare_infos")   //获取分享页面数据
    Observable<BaseBean> loadSharePageInfo(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/activationCodes/generate")   //生成激活码
    Observable<BaseBean> loadActivationCode(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/vip/package")     //获取VIP会员套餐数据
    Observable<BaseBean<List<VipPackage>>> loadVipPackageData(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/vip/create_order")     //创建Vip付款订单，获取支付信息
    Observable<BaseBean> createOrderPayVip(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/upgrade_vip_create_order")     //获取支付开通数据
    Observable<BaseBean> updatePayInfo(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/payment/active_code_to_vip")       //使用激活码升级
    Observable<BaseBean> updateVipByActivateCode(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userUpgradec/upgradeApplyRepeat")   //查询用户是否已经申请代理商或OEM
    Observable<BaseBean> loadAgentIsApply(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/share/check_agent_nickname")        //查询代理商申请类型昵称
    Observable<BaseBean> loadAgentNickName(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userUpgradec/upgradeApply")     //提交代理商或OEM申请
    Observable<BaseBean> submitAgentApply(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/planing/getToday")     //查询今日操作
    Observable<BaseBean<TodayOperationSub>> loadTodayOperation(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/planing/getPlanning")     //查询规划列表
    Observable<BaseBean<List<SmartPlanSub>>> loadPlanList(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/planning/do_huankuan_pos")     //标记还款交易
    Observable<BaseBean> signRepay(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/planing/getToday")     //查询今日操作
    Observable<BaseBean> loadTO(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/planing/getPlanning")     //查询智能规划
    Observable<BaseBean> loadSP(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/checkCard")    //查询信用卡所属银行
    Observable<BaseBean> checkBankCardName(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/bind_deposit_card")    //添加储蓄卡
    Observable<BaseBean> addCashCard(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/check_deposit_card")   //查询储蓄卡
    Observable<BaseBean> checkCashCard(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/dropcard")     //删除银行卡  （信用卡和储蓄卡）
    Observable<BaseBean> deleteBankCard(@FieldMap Map<String,String> params);

    @Multipart
    @POST("/app/user/uploadIdentify")     //实名认证  (上传身份证正反面)
    Observable<BaseBean> submitRealNameAuthImage(@PartMap Map<String,RequestBody> params, @Part MultipartBody.Part front,@Part MultipartBody.Part back);

    @FormUrlEncoded
    @POST("/app/user/certification_v2")
    Observable<BaseBean> submitRealNameAuthInfo(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/check_identify")       //查询用户实名认证状态
    Observable<BaseBean> checkRealNameAuthStatus(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/checkCard")     //查询银行卡号所属银行
    Observable<BaseBean> queryBankByCardId(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user_bankcard/add_userbankcard")     //添加信用卡
    Observable<BaseBean> addBankCard(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/card_detail")     //查询已添加的信用卡
    Observable<BaseBean<BillCreditCard>> queryCreditCardList(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/planning/check_fail_count")     //查询存在规划失败的卡片总数
    Observable<BaseBean> queryCardCountOfPlanFailed(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/check_user_alipay")        //检查用户是否绑定支付宝
    Observable<BaseBean> checkUserBindZFB(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/searcWithdrawFee")        //查询提现手续费
    Observable<BaseBean> queryWithdrawFee(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/bind_user_alipay")     //绑定支付宝账号
    Observable<BaseBean> bindZFB(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/unbind_user_alipay")       //解绑支付宝账号
    Observable<BaseBean> unBindZFB(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/news/selectSysNews")        //根据时间查询新增的消息
    Observable<BaseBean> checkNewMessage(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/news/selectAysNewsAll")     //根据时间分页查询消息
    Observable<BaseBean<MessageGroup>> checkNewMessageList(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/news/selectNewsByNewId")       //根据ID查询消息
    Observable<BaseBean<List<Message>>> checkNewMessageById(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/planning/update_planning_info")       //更新规划数据
    Observable<BaseBean> updatePlanningInfo(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/check_apply_status")        //查询POS开通状态
    Observable<BaseBean> checkPosApplyStatus(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/check_apply_info")      //查询POS申请用户已上传的资料
    Observable<BaseBean<PosApplyInfo>> checkPosApplyUploadData(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/apply_info_receive")    //上传POS申请邮寄信息
    Observable<BaseBean> upLoadMailInfo(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/apply_info_terminal")   //上传终端信息
    Observable<BaseBean> upLoadTerminalInfo(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/apply_info_deposit")    //上传结算信息
    Observable<BaseBean> upLoadSettleInfo(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/query_cnaps")       //查询支行
    Observable<BaseBean<List<Branch>>> checkBranchBank(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/planning/get_zhongfu_mcc_list")       //获取MCC列表
    Observable<BaseBean> loadMccList(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userUpgradec/getPdrwList")       //获取充值记录
    Observable<BaseBean<RechargeRecord>> loadRechargeRecord(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userUpgradec/getPdrwList")       //获取提现记录
    Observable<BaseBean<WithdrawRecord>> loadWithdrawRecord(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userUpgradec/getPdrwList")       //获取分润提现
    Observable<BaseBean> loadProfitWithdraw(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userUpgradec/transactionDetails")       //获取交易明细
    Observable<BaseBean> loadTradeDetail(@FieldMap Map<String,String> params);

    @Multipart
    @POST("/app/pos/upload_img")            //上传图片文件
    Observable<BaseBean> uploadImageFile(@PartMap Map<String,RequestBody> params, @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @POST("/app/pos/commit_img_url")    //提交保存图片
    Observable<BaseBean> commitSaveImage(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/get_user_finance")       //获取交易明细
    Observable<BaseBean> loadUserFinance(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/change_apply_status")       //把POS申请审核状态由已付款修改为审核中
    Observable<BaseBean> changePosApplyStatus(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/kd_info")           //查询POS申请已邮寄信息
    Observable<BaseBean> checkHaveMailInfo(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/pos_info")      //POS管理获取用户POS信息
    Observable<BaseBean<PosApplyInfo>> loadPosManageData(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/hlb_recharge")       //充值
    Observable<BaseBean> rechargeMoney(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/withdraw")       //提现
    Observable<BaseBean> withdrawMoney(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/api/get_api_price")     //卡评测-获取评测价格
    Observable<BaseBean> loadTestMoney(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/api/card_evaluation_create_order")     //卡评测-发起测评
    Observable<BaseBean> submitCardTest(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/api/card_evaluation_check_status")      //【卡测评（新）】查询订单状态
    Observable<BaseBean> checkTestResultStatus(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/api/query_record_card")     //查询用户测评过的卡片列表
    Observable<BaseBean<List<CardTestItem>>> loadTestCardList(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/planning/confirm_planning")       //pos规划-确认提交
    Observable<BaseBean> confirmPosPlan(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/planning/generate_planning")       //pos规划-生成数据
    Observable<BaseBean<GeneratePlan>> generatePosPlan(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/planning/confirm_planning")       //智能规划-确认提交
    Observable<BaseBean> confirmAutoPlan(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/planning/generate_planning")       //智能规划-生成数据
    Observable<BaseBean<GeneratePlan>> generateAutoPlan(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/change_phone")          //POS管理-修改申请人手机号
    Observable<BaseBean> updatePosApplyPhone(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/remobile_save")        //绑定新手机
    Observable<BaseBean> bindNewPhone(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/billdetail_top")       //获取交易明细顶部信息
    Observable<BaseBean> loadBillDetailTop(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/billdetail")       //获取交易明细
    Observable<BaseBean> loadBillDetail(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/api/query_record_list")     //查询用户测评过的所有记录列表
    Observable<BaseBean<List<CardTestItem>>> loadTestRecord(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/payment/create_order")      //充值预下单接口（目前支持卡测评充值）
    Observable<BaseBean> createCardTestPayOrder(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/api/query_record_detail")   //查看测评记录详情
    Observable<BaseBean<CardTestItem>> loadTestRecordDetail(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/markRepayed")       //标记已还清
    Observable<BaseBean> requestSignRepay(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/pos/planning/create_bill")       //添加交易
    Observable<BaseBean> requestCreateBill(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user/setnewpsw_msg")        //忘记密码
    Observable<BaseBean> forgerPassword(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/user_bankcard/edit_card")   //修改信用卡
    Observable<BaseBean> editCard(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/share/get_member_list")   //获取普通/VIP会员列表
    Observable<BaseBean<Member>> requestMemberList(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/payment/bind_card_msg")     //添加卡片短信下发接口  ,卡片鉴权短信
    Observable<BaseBean> bindCardSendSMS(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/userbankcard/hlb_quickpay_bindcard")    //信用卡鉴权
    Observable<BaseBean> authCreditCard(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/app/planning/check_fail_card")  //查询存在规划失败的卡片列表
    Observable<BaseBean> loadFailCardList(@FieldMap Map<String,String> params);

}
