package com.yunkahui.datacubeper.common.utils;

import android.util.Log;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/8.
 */

public class LogUtils {

    public static void e(String text){
        Log.e("kayisheng",text);
    }

    public static void e(Map<String,String> map){
        e("请求参数");
        for (String key:map.keySet()){
            e(key+"  "+map.get(key));
        }
    }

    public static void d(String key,String value){
        Log.d(key,value);
    }

}
