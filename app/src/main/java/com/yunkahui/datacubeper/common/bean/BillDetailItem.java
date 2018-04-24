package com.yunkahui.datacubeper.common.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.bill.adapter.ExpandableBillDeatailAdapter;

public class BillDetailItem implements MultiItemEntity {

    /**
     * s_id : 1
     * user_code : cv4ighrmoz
     * user_credit_card_id : 23
     * trade_type : 1
     * trade_money : 1
     * trade_date : 1517465424000
     * bill_month : 2
     * bill_type : 2
     * this_repay_min :
     * this_repay_sum :
     * prior_repay :
     * prior_sum :
     * this_bill_sum :
     * this_add_point :
     * integral_sum :
     * create_time : 1524552901000
     * update_time : 1524552901000
     * summary :
     * org_number :
     */

    @Override
    public int getItemType() {
        return ExpandableBillDeatailAdapter.TYPE_LEVEL_1;
    }

    private int s_id;
    private String user_code;
    private int user_credit_card_id;
    private String trade_type;
    private int trade_money;
    private long trade_date;
    private String bill_month;
    private String bill_type;
    private String this_repay_min;
    private String this_repay_sum;
    private String prior_repay;
    private String prior_sum;
    private String this_bill_sum;
    private String this_add_point;
    private String integral_sum;
    private long create_time;
    private long update_time;
    private String summary;
    private String org_number;

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public int getUser_credit_card_id() {
        return user_credit_card_id;
    }

    public void setUser_credit_card_id(int user_credit_card_id) {
        this.user_credit_card_id = user_credit_card_id;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public int getTrade_money() {
        return trade_money;
    }

    public void setTrade_money(int trade_money) {
        this.trade_money = trade_money;
    }

    public long getTrade_date() {
        return trade_date;
    }

    public void setTrade_date(long trade_date) {
        this.trade_date = trade_date;
    }

    public String getBill_month() {
        return bill_month;
    }

    public void setBill_month(String bill_month) {
        this.bill_month = bill_month;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    public String getThis_repay_min() {
        return this_repay_min;
    }

    public void setThis_repay_min(String this_repay_min) {
        this.this_repay_min = this_repay_min;
    }

    public String getThis_repay_sum() {
        return this_repay_sum;
    }

    public void setThis_repay_sum(String this_repay_sum) {
        this.this_repay_sum = this_repay_sum;
    }

    public String getPrior_repay() {
        return prior_repay;
    }

    public void setPrior_repay(String prior_repay) {
        this.prior_repay = prior_repay;
    }

    public String getPrior_sum() {
        return prior_sum;
    }

    public void setPrior_sum(String prior_sum) {
        this.prior_sum = prior_sum;
    }

    public String getThis_bill_sum() {
        return this_bill_sum;
    }

    public void setThis_bill_sum(String this_bill_sum) {
        this.this_bill_sum = this_bill_sum;
    }

    public String getThis_add_point() {
        return this_add_point;
    }

    public void setThis_add_point(String this_add_point) {
        this.this_add_point = this_add_point;
    }

    public String getIntegral_sum() {
        return integral_sum;
    }

    public void setIntegral_sum(String integral_sum) {
        this.integral_sum = integral_sum;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getOrg_number() {
        return org_number;
    }

    public void setOrg_number(String org_number) {
        this.org_number = org_number;
    }
}
