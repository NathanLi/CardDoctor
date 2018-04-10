package com.yunkahui.datacubeper.common.bean;

/**
 * Created by YD1 on 2018/4/9
 */
public class CardTestItem {

    private int icon;
    private String userName;
    private String bankName;
    private String bankId;
    private String nickName;
    private String cardType;

    public CardTestItem() {
    }

    public CardTestItem(int icon, String userName, String bankName, String bankId, String nickName, String cardType) {
        this.icon = icon;
        this.userName = userName;
        this.bankName = bankName;
        this.bankId = bankId;
        this.nickName = nickName;
        this.cardType = cardType;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
