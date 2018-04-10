package com.yunkahui.datacubeper.mine.logic;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.MineItem;
import com.yunkahui.datacubeper.common.bean.PersonalInfo;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/9.
 */

public class MineLogic {

    /**
     * 获取个人中心菜单
     *
     * @param context
     * @return
     */
    public List<MineItem> getMineItemList(Context context) {
        List<MineItem> mineItems = new ArrayList<>();
        String json = StringUtils.getJsonForLocation(context, "personal_menu.json");
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONArray array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    JSONArray arrayItem = array.getJSONArray(i);
                    for (int k = 0; k < arrayItem.length(); k++) {
                        JSONObject object = arrayItem.getJSONObject(k);
                        MineItem mineItem = new MineItem();
                        mineItem.setId(object.optInt("id"));
                        mineItem.setTitle(object.optString("title"));
                        mineItem.setIcon(context.getResources().getIdentifier(object.optString("icon"), "mipmap", context.getPackageName()));
                        mineItem.setShow(k == 0);
                        mineItems.add(mineItem);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mineItems;
    }


    public void loadPersonalInformation(Context context) {

        Map<String, String> params = RequestUtils.newParams()
                .addParams("org_number", context.getResources().getString(R.string.org_number))
                .create();

        HttpManager.getInstance().create(ApiService.class).loadPersonalInformation(params)
                .compose(HttpManager.<BaseBean<PersonalInfo>>applySchedulers()).subscribe(new SimpleCallBack<BaseBean<PersonalInfo>>() {
            @Override
            public void onSuccess(BaseBean<PersonalInfo> baseBean) {
                LogUtils.e("bean-》"+baseBean.toString());
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });

    }

}
