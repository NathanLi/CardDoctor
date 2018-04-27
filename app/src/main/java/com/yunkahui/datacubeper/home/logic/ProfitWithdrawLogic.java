package com.yunkahui.datacubeper.home.logic;

import android.annotation.SuppressLint;
import android.content.Context;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.CardDoctorApplication;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.TradeRecordDetail;
import com.yunkahui.datacubeper.common.bean.TradeRecordSummary;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfitWithdrawLogic {

    public void getProfitWithdraw(Context context, String pdType, int pageSize, int pageNum, SimpleCallBack<BaseBean> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("pdType", pdType)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .create();
        HttpManager.getInstance().create(ApiService.class).loadProfitWithdraw(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    public List<TradeRecordDetail> parsingJSONForProfitWithdraw(BaseBean baseBean) {
        List<TradeRecordDetail> list = null;
        try {
            JSONObject object = baseBean.getJsonObject();
            JSONObject respData = object.optJSONObject("respData");
            JSONArray jsonArray = respData.optJSONArray("list");
            TradeRecordDetail item;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject j = new JSONObject(jsonArray.opt(i).toString());
                item = new TradeRecordDetail();
                item.setTimeStamp(j.optLong("create_time"));
                item.setTime(com.yunkahui.datacubeper.common.utils.TimeUtils.format("yyyy-MM-dd hh:mm:ss", j.optLong("create_time")));
                item.setTradeType(j.optString("order_state"));
                item.setMoney(j.optString("amountString"));
                item.setTitle(j.optString("withdraw_type"));
                list.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
