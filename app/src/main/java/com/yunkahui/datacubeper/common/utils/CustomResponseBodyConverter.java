package com.yunkahui.datacubeper.common.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.yunkahui.datacubeper.common.bean.BaseBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2018/4/23.
 */

public class CustomResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String json=value.string();
        try {
            JSONObject object=new JSONObject(json);
            if("0000".equals(object.optString("respCode"))){
                T result=adapter.fromJson(json);
                if(result instanceof BaseBean){
                    ((BaseBean)result).setJsonObject(object);
                }
                return result;
            }else {
                BaseBean baseBean=new BaseBean();
                baseBean.setRespCode(object.optString("respCode"));
                baseBean.setRespDesc(object.optString("respDesc"));
                baseBean.setJsonObject(object);
                return (T) baseBean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) new BaseBean();
    }
}
