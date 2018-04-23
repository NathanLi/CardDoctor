package com.yunkahui.datacubeper.mine.logic;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.MultipartUtil;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.api.BaseUrl;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.MineItem;
import com.yunkahui.datacubeper.common.bean.PersonalInfo;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/4/9.
 */

public class MineLogic {

    /**
     * 获取个人中心菜单
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

    /**
     * 获取个人信息
     */
    public void loadPersonalInformation(Context context,SimpleCallBack<BaseBean<PersonalInfo>> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .create();
        HttpManager.getInstance().create(ApiService.class).loadPersonalInformation(params)
                .compose(HttpManager.<BaseBean<PersonalInfo>>applySchedulers()).subscribe(callBack);
    }

    /**
     * 上传头像
     */
    public void upLoadAvatar(Context context,String path,SimpleCallBack<BaseBean> callBack){
        Map<String, RequestBody> textBody= MultipartUtil.newInstance()
                .addParam("user_code", BaseUrl.getUSER_ID())
                .addParam("key",BaseUrl.getKEY())
                .addParam("org_number",context.getResources().getString(R.string.org_number))
                .Build();
        File file=new File(path);
        if(file.exists()){
            List<File> files=new ArrayList<>();
            files.add(file);
            List<MultipartBody.Part> parts=MultipartUtil.makeMultpart("avatar",files);
            HttpManager.getInstance().create(ApiService.class).upLoadPersonalAvatar(textBody,parts.get(0))
                    .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
        }
    }

    /**
     * 查询用户实名认证状态
     */
    public void checkRealNameAuthStatus(Context context,SimpleCallBack<BaseBean> callBack){
        Map<String,String> params=RequestUtils.newParams(context).create();
        HttpManager.getInstance().create(ApiService.class).checkRealNameAuthStatus(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }


    /**
     * 根据时间查询新增的消息
     */
    public void checkNewMessage(Context context,String time,SimpleCallBack<BaseBean> callBack){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MONTH,-2);
        Map<String,String> params=RequestUtils.newParams(context)
                .addParams("update_time", calendar.getTimeInMillis()+"")
                .create();
        HttpManager.getInstance().create(ApiService.class).checkNewMessage(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }


}
