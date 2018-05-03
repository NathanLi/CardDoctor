package com.yunkahui.datacubeper.common.bean;

import java.util.List;

public class BillCreditCard {

    private String trueName;
    private int planCount;
    private String avatarUrl;
    private int cardCount;
    private int payOffCount;
    private List<CreditCard> cardDetail;

    public static class CreditCard {
        private int userCreditCardId;
        private String userCode;
        private String cardHolder;
        private String bankCardName;
        private String bankCardNum;
        private String bankCardTel;
        private double fixLine;
        private double tmpLine;
        private long tmpLineDate;
        private String availableBalance;
        private long billDayDate;
        private String repayStatus;
        private long repayDayDate;
        private String cvv2;
        private long expiryDate;
        private long createTime;
        private long updateTime;
        private String bindId;
        private double thisShouldRepay;
        private double surPlusRepay;
        private int isOverRepay;
        private int distanceDay;
        private int canPlanning;
        private String billDay;
        private String repayDay;

        public int getUserCreditCardId() {
            return userCreditCardId;
        }

        public void setUserCreditCardId(int userCreditCardId) {
            this.userCreditCardId = userCreditCardId;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getCardHolder() {
            return cardHolder;
        }

        public void setCardHolder(String cardHolder) {
            this.cardHolder = cardHolder;
        }

        public String getBankCardName() {
            return bankCardName;
        }

        public void setBankCardName(String bankCardName) {
            this.bankCardName = bankCardName;
        }

        public String getBankCardNum() {
            return bankCardNum;
        }

        public void setBankCardNum(String bankCardNum) {
            this.bankCardNum = bankCardNum;
        }

        public String getBankCardTel() {
            return bankCardTel;
        }

        public void setBankCardTel(String bankCardTel) {
            this.bankCardTel = bankCardTel;
        }

        public double getFixLine() {
            return fixLine;
        }

        public void setFixLine(double fixLine) {
            this.fixLine = fixLine;
        }

        public double getTmpLine() {
            return tmpLine;
        }

        public void setTmpLine(double tmpLine) {
            this.tmpLine = tmpLine;
        }

        public long getTmpLineDate() {
            return tmpLineDate;
        }

        public void setTmpLineDate(long tmpLineDate) {
            this.tmpLineDate = tmpLineDate;
        }

        public String getAvailableBalance() {
            return availableBalance;
        }

        public void setAvailableBalance(String availableBalance) {
            this.availableBalance = availableBalance;
        }

        public long getBillDayDate() {
            return billDayDate;
        }

        public void setBillDayDate(long billDayDate) {
            this.billDayDate = billDayDate;
        }

        public String getRepayStatus() {
            return repayStatus;
        }

        public void setRepayStatus(String repayStatus) {
            this.repayStatus = repayStatus;
        }

        public long getRepayDayDate() {
            return repayDayDate;
        }

        public void setRepayDayDate(long repayDayDate) {
            this.repayDayDate = repayDayDate;
        }

        public String getCvv2() {
            return cvv2;
        }

        public void setCvv2(String cvv2) {
            this.cvv2 = cvv2;
        }

        public long getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(long expiryDate) {
            this.expiryDate = expiryDate;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getBindId() {
            return bindId;
        }

        public void setBindId(String bindId) {
            this.bindId = bindId;
        }

        public double getThisShouldRepay() {
            return thisShouldRepay;
        }

        public void setThisShouldRepay(double thisShouldRepay) {
            this.thisShouldRepay = thisShouldRepay;
        }

        public double getSurPlusRepay() {
            return surPlusRepay;
        }

        public void setSurPlusRepay(double surPlusRepay) {
            this.surPlusRepay = surPlusRepay;
        }

        public int getIsOverRepay() {
            return isOverRepay;
        }

        public void setIsOverRepay(int isOverRepay) {
            this.isOverRepay = isOverRepay;
        }

        public int getDistanceDay() {
            return distanceDay;
        }

        public void setDistanceDay(int distanceDay) {
            this.distanceDay = distanceDay;
        }

        public int getCanPlanning() {
            return canPlanning;
        }

        public void setCanPlanning(int canPlanning) {
            this.canPlanning = canPlanning;
        }

        public String getBillDay() {
            return billDay;
        }

        public void setBillDay(String billDay) {
            this.billDay = billDay;
        }

        public String getRepayDay() {
            return repayDay;
        }

        public void setRepayDay(String repayDay) {
            this.repayDay = repayDay;
        }
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public int getPlanCount() {
        return planCount;
    }

    public void setPlanCount(int planCount) {
        this.planCount = planCount;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public List<CreditCard> getCardDetail() {
        return cardDetail;
    }

    public int getPayOffCount() {
        return payOffCount;
    }

    public void setPayOffCount(int payOffCount) {
        this.payOffCount = payOffCount;
    }

    public void setCardDetail(List<CreditCard> cardDetail) {
        this.cardDetail = cardDetail;
    }
}
