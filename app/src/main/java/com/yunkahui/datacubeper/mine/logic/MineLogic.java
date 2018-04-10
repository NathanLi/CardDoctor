package com.yunkahui.datacubeper.mine.logic;

import android.content.Context;
import android.text.TextUtils;

import com.yunkahui.datacubeper.common.bean.MineItem;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/9.
 */

public class MineLogic {

    public List<MineItem> getMineItemList(Context context){
        List<MineItem> mineItems=new ArrayList<>();
        String json= StringUtils.getJsonForLocation(context,"personal_menu.json");
        if(!TextUtils.isEmpty(json)){
            try {
                JSONArray array=new JSONArray(json);
                for (int i=0;i<array.length();i++){
                    JSONArray arrayItem=array.getJSONArray(i);
                    for (int k=0;k<arrayItem.length();k++){
                        JSONObject object=arrayItem.getJSONObject(k);
                        MineItem mineItem=new MineItem();
                        mineItem.setId(object.optInt("id"));
                        mineItem.setTitle(object.optString("title"));
                        mineItem.setIcon(context.getResources().getIdentifier(object.optString("icon"),"mipmap",context.getPackageName()));
                        mineItem.setShow(k==0);
                        mineItems.add(mineItem);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mineItems;
    }


}
