package com.yunkahui.datacubeper.common.bean;

/**
 * 支行信息
 */

public class Branch {
    private int b_id;
    private String bank_name;
    private String bank_number;
    private String bank_cnaps;
    private String bank_region;
    private boolean isCheck;

    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public String getBank_name() {
        return bank_name == null ? "" : bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_number() {
        return bank_number == null ? "" : bank_number;
    }

    public void setBank_number(String bank_number) {
        this.bank_number = bank_number;
    }

    public String getBank_cnaps() {
        return bank_cnaps == null ? "" : bank_cnaps;
    }

    public void setBank_cnaps(String bank_cnaps) {
        this.bank_cnaps = bank_cnaps;
    }

    public String getBank_region() {
        return bank_region == null ? "" : bank_region;
    }

    public void setBank_region(String bank_region) {
        this.bank_region = bank_region;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "b_id=" + b_id +
                ", bank_name='" + bank_name + '\'' +
                ", bank_number='" + bank_number + '\'' +
                ", bank_cnaps='" + bank_cnaps + '\'' +
                ", bank_region='" + bank_region + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
