package com.yunkahui.datacubeper.common.utils;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.PersonalInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 暂时用来保存临时数据
 */

public class DataUtils {

    private static PersonalInfo mInfo;
    private static Map<String, Integer> bankIconMap;

    public static PersonalInfo getInfo() {
        return mInfo==null? new PersonalInfo():mInfo;
    }

    public static void setInfo(PersonalInfo mInfo) {
        DataUtils.mInfo = mInfo;
    }

    public static void setBankIconMap(Map<String, Integer> bankIconMap) {
        DataUtils.bankIconMap = bankIconMap;
    }

    public static Map<String, Integer> getBankIconMap() {
        if (bankIconMap == null) {
            Map<String, Integer> map = new HashMap<>();

            String[] bankNames = new String[] {
                    "包商银行", "北京农商银行", "北京银行", "成都农商银行", "福建省农村信用社",
                    "广东发展银行", "广州农村商业银行", "广州银行", "汉口银行", "杭州银行",
                    "河北银行", "华夏银行", "徽商银行", "江苏银行", "锦州银行",
                    "兰州银行", "龙江银行", "宁波银行", "宁夏银行", "平安银行",
                    "青海银行", "上海农商银行", "上海浦东发展银行", "上海银行", "台州银行",
                    "温州银行", "兴业银行", "银联", "鄞州银行", "长沙银行",
                    "招商银行", "中国工商银行", "中国光大银行", "中国建设银行", "中国交通银行",
                    "中国民生银行", "中国农业银行", "中国银行", "中国邮政储蓄银行", "中信银行",
                    "重庆银行", "光大银行", "民生银行", "广发银行", "重庆银行股份有限公司",
                    "邮储银行", "邮政储蓄银行", "邮储银行河南分行", "工商银行",
                    "深圳发展银行", "深发银行",  "农业银行",
                    "中行", "招商", "光大", "建行", "民生", "平安", "浦发", "兴业", "中信", "广发",
                    "建设银行", "浦发银行", "工商", "农业", "交通银行", "交通", "工行", "农行", "华夏"
            };

            int[] bankPics = new int[] {
                    R.mipmap.ic_bank_baoshang, R.mipmap.bank_beijingnongshang, R.mipmap.ic_bank_beijing, R.mipmap.bank_chengdunongshang, R.mipmap.bank_fujian,
                    R.mipmap.bank_guangdongfazhan, R.mipmap.ic_bank_guangzhounongshang, R.mipmap.bank_guangzhou, R.mipmap.bank_hankou, R.mipmap.bank_hangzhou,
                    R.mipmap.ic_bank_hebei, R.mipmap.bank_huaxia, R.mipmap.bank_huishang, R.mipmap.bank_jiangsu, R.mipmap.bank_jingzhou,
                    R.mipmap.bank_lanzhou, R.mipmap.bank_longjiang, R.mipmap.bank_ningbo, R.mipmap.bank_ningxia, R.mipmap.bank_pingan,
                    R.mipmap.bank_qinghai, R.mipmap.bank_shanghainongshang, R.mipmap.bank_shanghaipufa, R.mipmap.bank_shanghai, R.mipmap.bank_taizhou,
                    R.mipmap.bank_wenzhou, R.mipmap.bank_xingye, R.mipmap.bank_other, R.mipmap.bank_yinzhou, R.mipmap.ic_bank_changsha,
                    R.mipmap.bank_zhaoshang, R.mipmap.bank_zhongguogongshang, R.mipmap.bank_zhongguoguangda, R.mipmap.bank_zhongguojianshe, R.mipmap.bank_zhongguojiaoton,
                    R.mipmap.bank_zhongguomingsheng, R.mipmap.bank_zhongguonongye, R.mipmap.bank_zhongguo, R.mipmap.bank_zhongguoyouzheng, R.mipmap.bank_zhongxin,
                    R.mipmap.ic_bank_chonqing, R.mipmap.bank_zhongguoguangda, R.mipmap.bank_zhongguomingsheng, R.mipmap.bank_guangdongfazhan, R.mipmap.ic_bank_chonqing,
                    R.mipmap.bank_zhongguoyouzheng,R.mipmap.bank_zhongguoyouzheng,R.mipmap.bank_zhongguoyouzheng, R.mipmap.bank_zhongguogongshang,
                    R.mipmap.bank_shenzhenfazhan, R.mipmap.bank_shenzhenfazhan, R.mipmap.bank_zhongguonongye,
                    R.mipmap.bank_zhongguo, R.mipmap.bank_zhaoshang, R.mipmap.bank_zhongguoguangda, R.mipmap.bank_zhongguojianshe, R.mipmap.bank_zhongguomingsheng,
                    R.mipmap.bank_pingan, R.mipmap.bank_shanghaipufa, R.mipmap.bank_xingye, R.mipmap.bank_zhongxin, R.mipmap.bank_guangdongfazhan,
                    R.mipmap.bank_zhongguojianshe, R.mipmap.bank_shanghaipufa, R.mipmap.bank_zhongguogongshang, R.mipmap.bank_nonye, R.mipmap.bank_zhongguojiaoton,
                    R.mipmap.bank_zhongguojiaoton, R.mipmap.bank_zhongguogongshang, R.mipmap.bank_zhongguonongye, R.mipmap.bank_huaxia
            };
            for (int index = 0; index < bankNames.length; index++) {
                map.put(bankNames[index], bankPics[index]);
            }
            DataUtils.setBankIconMap(map);
        }
        return bankIconMap;
    }

    public static String transBankCardNum(String cardNum, int num) {
        String result;
        if (cardNum.length() <= 8) {
            result = cardNum;
        }else {
            StringBuilder tmp = new StringBuilder(cardNum.substring(0, 4));
            for (int index = 0; index < num; index++) {
                tmp.append("*");
            }
            tmp.append(cardNum.substring(cardNum.length() - 4));
            result = tmp.toString();
        }
        return result;
    }



}
