package com.yunkahui.datacubeper.home.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.BillCreditCard;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/18.
 */

public class HomeLogic {

    /**
     * 查询POS申请状态
     */
    public void checkPosApplyStatus(Context context, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).checkPosApplyStatus(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    //******** 获取余额、分润 ********
    public void loadUserFinance(Context context, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).loadUserFinance(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 获取卡片数据
     */
    public void queryCreditCardList(Context context, SimpleCallBack<BaseBean<BillCreditCard>> callBack) {
        Map<String, String> params = RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).queryCreditCardList(params)
                .compose(HttpManager.<BaseBean<BillCreditCard>>applySchedulers()).subscribe(callBack);
    }

    /**
     * 删除卡片
     */
    public void deleteCreditCard(Context context, int id, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("bankcard_id", id)
                .addParams("bankcard_type", "00")
                .create();
        HttpManager.getInstance().create(ApiService.class).deleteBankCard(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }

    /**
     * 从本地json获取首页菜单
     */
    public List<HomeItem> parsingJSONForHomeItem(Context context) {
        List<HomeItem> homeItems = new ArrayList<>();
        try {
            String json= StringUtils.getJsonForLocation(context,"home_menu.json");
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                HomeItem homeItem=new HomeItem();
                JSONObject object=array.getJSONObject(i);
                homeItem.setId(object.optInt("id"));
                homeItem.setIcon(context.getResources().getIdentifier(object.optString("icon"), "mipmap", context.getPackageName()));
                homeItem.setTitle(object.optString("title"));
                homeItems.add(homeItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return homeItems;
    }

}
