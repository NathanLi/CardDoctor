package com.yunkahui.datacubeper.common.api;

import com.yunkahui.datacubeper.common.utils.LogUtils;

/**
 * Created by Administrator on 2018/4/8.
 */

public class BaseUrl {


    private static String USER_ID;
    private static String KEY;

    public static String HOME = "http://192.168.5.115:8014";

    //验签密钥
    public static String RSA_KEY = "YgAqeZq1eM#6#xTWkjtEGO%Ol4oxzBIlYI#k75HJml4bCr!F8YTqySDueKRY%1GB";


    public static String getUSER_ID() {
        return USER_ID == null ? "" : USER_ID;
    }

    public static void setUSER_ID(String user_id) {
        BaseUrl.USER_ID = user_id;
    }

    public static String getKEY() {
        return KEY == null ? "" : KEY;
    }

    public static void setKEY(String key) {
        BaseUrl.KEY = key;
    }
}
