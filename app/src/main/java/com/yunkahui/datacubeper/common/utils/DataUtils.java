package com.yunkahui.datacubeper.common.utils;

import com.yunkahui.datacubeper.common.bean.PersonalInfo;

/**
 * 暂时用来保存临时数据
 */

public class DataUtils {


    private static PersonalInfo mInfo;

    public static PersonalInfo getInfo() {
        return mInfo==null? new PersonalInfo():mInfo;
    }

    public static void setInfo(PersonalInfo mInfo) {
        DataUtils.mInfo = mInfo;
    }
}
