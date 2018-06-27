package com.yunkahui.datacubeper.common.other;

import com.yunkahui.datacubeper.BuildConfig;

/**
 * Created by Administrator on 2018/5/19.
 */

public class OENType {

    public static final int yindian = 1;    //银典
    public static final int fengniao = 17;  //蜂鸟普惠
    public static final int xinyongdashi=18;    //信用大师
    public static final int kabuluo=19;    //卡部落

    public static int currentType() {
        return BuildConfig.ENV_TYPE;
    }
}
