package com.yunkahui.datacubeper.share.logic;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/12.
 */

public enum RecordType implements Serializable {

    //    public static final int myWallet_come = 1;  //我的钱包-收入
    //    public static final int myWallet_withdraw = 2;  //我的钱包-提现
    //    public static final int online_come = 3;    //线上分润-收入
    //    public static final int online_withdraw = 4;    //线上分润-提现
    //    public static final int pos_come = 5;       //POS分润-收入
    //    public static final int pos_withdraw = 6;  //POS分润-提现
    //    public static final int integral_come = 7;  //积分-收入
    //    public static final int integral_withdraw = 8;   //积分-提现

    balance_all("balance", "余额"),   //余额-全部
    balance_come("balance", "余额"),  //余额充值
    balance_withdraw("02", "余额"),   //余额提现

    online_all("fenruns", "线上分润"),  //线上分润-全部
    online_come("fenruns", "线上分润"), //线上分润-收入
    online_withdraw("01", "线上分润"),  //线上分润-提现

    myWallet_all("commission", "佣金"),   //佣金-全部
    myWallet_come("commission", "佣金"),  //佣金-收入
    myWallet_withdraw("00", "佣金"),  //佣金-提现

    pos_all("gain", "POS 分润"),  //POS分润-全部
    pos_come("gain", "POS 分润"), //POS分润-收入
    pos_withdraw("withdraw", "POS 分润"), //POS分润-提现

    integral_all("points", "积分"),   //积分-全部
    integral_come("points", "积分"),  //积分-收入
    integral_withdraw("expend", "积分");    //积分-支出

    private String type;
    private String sub;

    RecordType(String type, String sub) {
        this.type = type;
        this.sub = sub;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
