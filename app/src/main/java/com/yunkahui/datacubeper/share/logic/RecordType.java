package com.yunkahui.datacubeper.share.logic;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/12.
 */

public enum RecordType implements Serializable {

    //    public static final int MyWallet_come = 1;  //我的钱包-收入
    //    public static final int MyWallet_withdraw = 2;  //我的钱包-提现
    //    public static final int online_come = 3;    //线上分润-收入
    //    public static final int online_withdraw = 4;    //线上分润-提现
    //    public static final int pos_come = 5;       //POS分润-收入
    //    public static final int pos_withdraw = 6;  //POS分润-提现
    //    public static final int integral_come = 7;  //积分-收入
    //    public static final int integral_withdraw = 8;   //积分-提现

    MyWallet_come("commission", 1),     //我的钱包-收入
    MyWallet_withdraw("00", 2),       //我的钱包-提现
    online_come("fenruns", 3),     //线上分润-收入
    online_withdraw("01", 4),      //线上分润-提现
    pos_come("gain", 5),        //POS分润-收入
    pos_withdraw("withdraw", 6),        //POS分润-提现
    integral_come("", 7),       //积分-收入
    integral_withdraw("", 8),   //积分-提现
    balance_all("balance", 9),   //余额全部
    balance_come("00", 10),       //余额充值
    balance_withdraw("02", 11);   //余额提现


    private String type;
    private int index;

    private RecordType(String type, int index) {
        this.type = type;
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
