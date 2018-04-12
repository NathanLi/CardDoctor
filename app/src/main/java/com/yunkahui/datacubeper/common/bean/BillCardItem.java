package com.yunkahui.datacubeper.common.bean;

/**
 * Created by Administrator on 2018/3/27
 */

public class BillCardItem {

    private String bankName;
    private String cardId;
    private String repayMoney;
    private String unrepayMoney;
    private String leaveDate;
    private String repayDate;

    public BillCardItem(String bankName, String cardId, String repayMoney, String unrepayMoney, String leaveDate, String repayDate) {
        this.bankName = bankName;
        this.cardId = cardId;
        this.repayMoney = repayMoney;
        this.unrepayMoney = unrepayMoney;
        this.leaveDate = leaveDate;
        this.repayDate = repayDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getRepayMoney() {
        return repayMoney;
    }

    public void setRepayMoney(String repayMoney) {
        this.repayMoney = repayMoney;
    }

    public String getUnrepayMoney() {
        return unrepayMoney;
    }

    public void setUnrepayMoney(String unrepayMoney) {
        this.unrepayMoney = unrepayMoney;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }
}
