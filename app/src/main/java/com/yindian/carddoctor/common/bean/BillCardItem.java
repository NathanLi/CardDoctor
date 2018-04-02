package com.yindian.carddoctor.common.bean;

/**
 * Created by Administrator on 2018/3/27
 */

public class BillCardItem {

    private String bankName;
    private String cardId;
    private int repayMoney;
    private int unrepayMoney;
    private int leaveDate;
    private String repayDate;

    public BillCardItem() {
    }

    public BillCardItem(String bankName, String cardId, int repayMoney, int unrepayMoney, int leaveDate, String repayDate) {
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

    public int getRepayMoney() {
        return repayMoney;
    }

    public void setRepayMoney(int repayMoney) {
        this.repayMoney = repayMoney;
    }

    public int getUnrepayMoney() {
        return unrepayMoney;
    }

    public void setUnrepayMoney(int unrepayMoney) {
        this.unrepayMoney = unrepayMoney;
    }

    public int getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(int leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }
}
