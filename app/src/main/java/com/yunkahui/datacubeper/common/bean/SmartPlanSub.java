package com.yunkahui.datacubeper.common.bean;

import com.yunkahui.datacubeper.common.other.DesignSubBean;

public class SmartPlanSub implements DesignSubBean {


    /**
     * ap_id : 38
     * user_credit_card_id : 15
     * plan_type : 00
     * amount : 100.0
     * date : 1523930231000
     * operation : 1
     * pos_type : 11
     * swift_number :
     * trade_no : 201803131602005849
     * batch_sn : 510374d21d324d178788ab69f7f27045
     * remarks :
     * create_time : 1523922573000
     * update_time : 1523924907000
     * service_charge :
     * bankCardName : 建设银行
     * bankCardNum : ****2
     * org_number : Y1000000001
     * user_code : cv4ighrmoz
     * business_name :
     * amountString :
     */

    private int ap_id;
    private int user_credit_card_id;
    private String plan_type;
    private double amount;
    private long date;
    private String operation;
    private String pos_type;
    private String swift_number;
    private String trade_no;
    private String batch_sn;
    private String remarks;
    private long create_time;
    private long update_time;
    private String service_charge;
    private String bankCardName;
    private String bankCardNum;
    private String org_number;
    private String user_code;
    private String business_name;
    private String amountString;

    public int getAp_id() {
        return ap_id;
    }

    public void setAp_id(int ap_id) {
        this.ap_id = ap_id;
    }

    public int getUser_credit_card_id() {
        return user_credit_card_id;
    }

    public void setUser_credit_card_id(int user_credit_card_id) {
        this.user_credit_card_id = user_credit_card_id;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getPos_type() {
        return pos_type;
    }

    public void setPos_type(String pos_type) {
        this.pos_type = pos_type;
    }

    public String getSwift_number() {
        return swift_number;
    }

    public void setSwift_number(String swift_number) {
        this.swift_number = swift_number;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getBatch_sn() {
        return batch_sn;
    }

    public void setBatch_sn(String batch_sn) {
        this.batch_sn = batch_sn;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public String getService_charge() {
        return service_charge;
    }

    public void setService_charge(String service_charge) {
        this.service_charge = service_charge;
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

    public String getOrg_number() {
        return org_number;
    }

    public void setOrg_number(String org_number) {
        this.org_number = org_number;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getAmountString() {
        return amountString;
    }

    public void setAmountString(String amountString) {
        this.amountString = amountString;
    }
}
