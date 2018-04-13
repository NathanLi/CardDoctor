package com.yunkahui.datacubeper.common.bean;

/**
 * Created by Administrator on 2018/3/27
 */

public class BillCard {

    private int icon;
    private String bankName;
    private String cardId;
    private String shouldRepayAmount;
    private String leaveDate;
    private String repayDate;
    private String billAmount;
    private String unrepayAmount;
    private String fixedAmount;
    private String billCycle;

    public BillCard(int icon, String bankName, String cardId, String shouldRepayAmount, String leaveDate,
                    String repayDate, String billAmount, String unrepayAmount, String fixedAmount, String billCycle) {
        this.icon = icon;
        this.bankName = bankName;
        this.cardId = cardId;
        this.shouldRepayAmount = shouldRepayAmount;
        this.leaveDate = leaveDate;
        this.repayDate = repayDate;
        this.billAmount = billAmount;
        this.unrepayAmount = unrepayAmount;
        this.fixedAmount = fixedAmount;
        this.billCycle = billCycle;
    }

    public int getIcon() {

        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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

    public String getShouldRepayAmount() {
        return shouldRepayAmount;
    }

    public void setShouldRepayAmount(String shouldRepayAmount) {
        this.shouldRepayAmount = shouldRepayAmount;
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

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getUnrepayAmount() {
        return unrepayAmount;
    }

    public void setUnrepayAmount(String unrepayAmount) {
        this.unrepayAmount = unrepayAmount;
    }

    public String getFixedAmount() {
        return fixedAmount;
    }

    public void setFixedAmount(String fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public String getBillCycle() {
        return billCycle;
    }

    public void setBillCycle(String billCycle) {
        this.billCycle = billCycle;
    }
}
