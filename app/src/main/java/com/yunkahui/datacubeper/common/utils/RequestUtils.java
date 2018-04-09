package com.yunkahui.datacubeper.common.utils;

import android.util.Base64;

import com.yunkahui.datacubeper.common.api.BaseUrl;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/4/8.
 */

public class RequestUtils {

    public static final String SUCCESS="0000";


    public static InnerParam newParams(){
        return new InnerParam();
    }


    /**
     * 拼接参数 加上user_id,key
     * @param map
     * @return
     */
    public static Map resetRequestParams(Map map){
        map.put("user_id",BaseUrl.USER_ID);
        map.put("key",BaseUrl.KEY);
        return map;
    }

    /**
     * 网络请求加签函数
     * 加签逻辑为base64(md5(key1=urlEncoder(value1)&key2=urlEncoder(value2)...SIGNATURE_KEY))
     * @param param 加签函数字典
     * @return 加签后的结果
     */
    public static String encryptParam(Map<String, String> param) {
        String encrypt = "";
        try {
            Set<String> set = param.keySet();
            String[] keys = new String[set.size()];
            set.toArray(keys);
            Arrays.sort(keys);
            StringBuilder builder = new StringBuilder();
            for (int numIndex = 0; numIndex < keys.length; numIndex++) {
                String key = keys[numIndex];
                builder.append(key + "=" + param.get(key)  + "&");
            }
            builder.deleteCharAt(builder.length() - 1);

            String transUrlEncode = URLEncoder.encode(builder.toString(), "UTF-8");

            String appendUrlEncode = transUrlEncode + BaseUrl.RSA_KEY;

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(appendUrlEncode.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                }else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            encrypt = Base64.encodeToString(strBuf.toString().getBytes(), Base64.DEFAULT);

            if(encrypt.indexOf("\n")!=-1){
                encrypt =  encrypt.replace("\n","");
            }
            if(encrypt.indexOf("\n")!=-1){
                LogUtils.e("还有换行符");
            }

        }catch (Exception e){e.printStackTrace();}

        return encrypt;
    }


    public static class InnerParam{

        Map<String,String> mMap;

        public InnerParam() {
            this(false);
        }
        public InnerParam(boolean isUser) {
            mMap=new HashMap<>();
            if(!isUser){
                mMap.put("user_code",BaseUrl.USER_ID);
                mMap.put("key",BaseUrl.KEY);
            }

        }

        public InnerParam addParams(String key,String value){
            mMap.put(key,value);
            return this;
        }

        public InnerParam addParams(String key,int value){
            mMap.put(key,value+"");
            return this;
        }

        public Map<String,String> create(){
            String signature=encryptParam(mMap);
            mMap.put("signature",signature);
            LogUtils.e(mMap);
            return mMap;
        }

    }


}
