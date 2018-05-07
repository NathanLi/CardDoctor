package com.yunkahui.datacubeper.common.bean;

/**
 * 规划失败卡片
 */

public class FailBankCard {

    private int bankcard_id;
    private String bankcard_name;
    private int is_restart;
    private int is_replanning;
    private String bankcard_num;
    private long fail_money_count;

    public int getBankcard_id() {
        return bankcard_id;
    }

    public void setBankcard_id(int bankcard_id) {
        this.bankcard_id = bankcard_id;
    }

    public String getBankcard_name() {
        return bankcard_name == null ? "" : bankcard_name;
    }

    public void setBankcard_name(String bankcard_name) {
        this.bankcard_name = bankcard_name;
    }

    public int getIs_restart() {
        return is_restart;
    }

    public void setIs_restart(int is_restart) {
        this.is_restart = is_restart;
    }

    public int getIs_replanning() {
        return is_replanning;
    }

    public void setIs_replanning(int is_replanning) {
        this.is_replanning = is_replanning;
    }

    public String getBankcard_num() {
        return bankcard_num == null ? "" : bankcard_num;
    }

    public void setBankcard_num(String bankcard_num) {
        this.bankcard_num = bankcard_num;
    }

    public long getFail_money_count() {
        return fail_money_count;
    }

    public void setFail_money_count(long fail_money_count) {
        this.fail_money_count = fail_money_count;
    }
}
