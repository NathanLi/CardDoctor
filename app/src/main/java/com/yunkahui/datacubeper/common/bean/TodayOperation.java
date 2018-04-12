package com.yunkahui.datacubeper.common.bean;

/**
 * Created by YD1 on 2018/4/12
 */
public class TodayOperation {

    private int icon;
    private String type;
    private String bank;
    private String id;
    private String date;
    private int money;
    private String operate;

    public TodayOperation(int icon, String type, String bank, String id, String date, int money, String operate) {
        this.icon = icon;
        this.type = type;
        this.bank = bank;
        this.id = id;
        this.date = date;
        this.money = money;
        this.operate = operate;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }
}
