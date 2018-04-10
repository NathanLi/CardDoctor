package com.yunkahui.datacubeper.common.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Base64;

import com.yunkahui.datacubeper.common.api.BaseUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    /**
     * 读取本地JSON文件
     * @param context
     * @param fileName
     * @return
     */
    public static String getJsonForLocation(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        AssetManager assetManager =  context.getAssets();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"UTF-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

}
