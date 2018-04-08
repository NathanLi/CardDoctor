package com.yunkahui.datacubeper.common.utils;

import android.util.Base64;

import com.yunkahui.datacubeper.common.api.BaseUrl;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/4/8.
 */

public class StringUtils {

    /**
     * 验证手机号码
     * @param phone
     * @return
     */
    public static boolean verifyPhone(String phone){
            if (phone != null && phone.length() == 11) {
                Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
                Matcher matcher = pattern.matcher(phone);
                return matcher.matches();
            }
            return false;
    }

}
