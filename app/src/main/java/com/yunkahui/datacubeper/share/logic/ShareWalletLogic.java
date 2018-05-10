package com.yunkahui.datacubeper.share.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShareWalletLogic {

    //******** 查询支付宝账户 ********
    public void checkUserZFB(Context context, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).checkUserBindZFB(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }


    /**
     * 获取分佣提现明细
     */
    public void loadCommissionRecord(Context context, int size, int page, String type, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("pageSize", String.valueOf(size))
                .addParams("pageNum", String.valueOf(page))
                .addParams("check_type", type)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadTradeDetail(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 解析分佣提现明细json列表
     */
    public List<TradeRecordDetail> parsingCommossionRecord(String json) {
        List<TradeRecordDetail> details = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                TradeRecordDetail detail = new TradeRecordDetail();
                JSONObject object = array.getJSONObject(i);
                detail.setTitle(object.optString("trade_type_desc"));
                detail.setTime(TimeUtils.format("yyyy-MM-dd HH:mm:ss", object.optLong("create_time")));
                detail.setTimeStamp(object.optLong("create_time"));
                detail.setOrderStatus(object.optString("order_state"));
                detail.setTradeType(object.optString("withdraw_type"));
                detail.setRemark(object.optString("order_state_desc"));
                detail.setMoney(object.optString("withdraw_amount"));
                details.add(detail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return details;
    }


}
