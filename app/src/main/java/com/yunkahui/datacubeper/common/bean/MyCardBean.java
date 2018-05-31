package com.yunkahui.datacubeper.common.bean;

/**
 * Created by Administrator on 2018/5/31 0031.
 */

public class MyCardBean {

    private int icon;
    private String bankCardName;
    private String bankCardId;
    private String cardHolder;
    private String billDate;
    private String repayDate;

    public MyCardBean() {
    }

    public MyCardBean(int icon, String bankCardName, String bankCardId, String cardHolder, String billDate, String repayDate) {
        this.icon = icon;
        this.bankCardName = bankCardName;
        this.bankCardId = bankCardId;
        this.cardHolder = cardHolder;
        this.billDate = billDate;
        this.repayDate = repayDate;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }
}
