package com.yunkahui.datacubeper.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/4/11.
 */

public class SharedPreferencesUtils {

    public static final String USER_NAME="USER_NAME";
    public static final String PASSWORD="PASSWORD";


    public static void save(Context context,String key, Long value){
        SharedPreferences preferences=context.getSharedPreferences("yunkahui",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putLong(key,value);
        editor.apply();
    }

    public static Long getLong(Context context,String key){
        SharedPreferences preferences=context.getSharedPreferences("yunkahui",Context.MODE_PRIVATE);
        return preferences.getLong(key,0);
    }

    public static void save(Context context,String key, float value){
        SharedPreferences preferences=context.getSharedPreferences("yunkahui",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putFloat(key,value);
        editor.apply();
    }

    public static float getFloat(Context context,String key){
        SharedPreferences preferences=context.getSharedPreferences("yunkahui",Context.MODE_PRIVATE);
        return preferences.getFloat(key,0);
    }

    public static void save(Context context,String key, boolean value){
        SharedPreferences preferences=context.getSharedPreferences("yunkahui",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public static boolean getBoolean(Context context,String key){
        SharedPreferences preferences=context.getSharedPreferences("yunkahui",Context.MODE_PRIVATE);
        return preferences.getBoolean(key,false);
    }

    public static void save(Context context,String key, int value){
        SharedPreferences preferences=context.getSharedPreferences("yunkahui",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public static int getInt(Context context, String key){
        SharedPreferences preferences=context.getSharedPreferences("yunkahui",Context.MODE_PRIVATE);
        return preferences.getInt(key,0);
    }

    public static void save(Context context,String key, String value){
        SharedPreferences preferences=context.getSharedPreferences("yunkahui",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String getString(Context context,String key){
        SharedPreferences preferences=context.getSharedPreferences("yunkahui",Context.MODE_PRIVATE);
        return preferences.getString(key,"");
    }

}
