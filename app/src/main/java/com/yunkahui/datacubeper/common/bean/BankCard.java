package com.yunkahui.datacubeper.common.bean;

/**
 * Created by Administrator on 2018/4/17.
 */

public class BankCard {

    private int Id;
    private String bankcard_tel;
    private String bankcard_num;
    private String cardholder;
    private String bankcard_name;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getBankcard_tel() {
        return bankcard_tel == null ? "" : bankcard_tel;
    }

    public void setBankcard_tel(String bankcard_tel) {
        this.bankcard_tel = bankcard_tel;
    }

    public String getBankcard_num() {
        return bankcard_num == null ? "" : bankcard_num;
    }

    public void setBankcard_num(String bankcard_num) {
        this.bankcard_num = bankcard_num;
    }

    public String getCardholder() {
        return cardholder == null ? "" : cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public String getBankcard_name() {
        return bankcard_name == null ? "" : bankcard_name;
    }

    public void setBankcard_name(String bankcard_name) {
        this.bankcard_name = bankcard_name;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "id=" + Id +
                ", bankcard_tel='" + bankcard_tel + '\'' +
                ", bankcard_num='" + bankcard_num + '\'' +
                ", cardholder='" + cardholder + '\'' +
                ", bankcard_name='" + bankcard_name + '\'' +
                '}';
    }
}
