package com.yunkahui.datacubeper.common.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/8.
 */

public class FailBankCardDetail {

    private int is_restart;
    private int is_replanning;
    private String question;
    private String deal;
    private List<Plan> autoplanning;


    public class Plan {

        private String pos_type;
        private String receive_name;
        private String remarks;
        private int ap_id;
        private long date;
        private float amount;
        private String operation;
        private String bankcard_name;
        private int user_credit_card_id;
        private String plan_type;
        private String bankcard_num;
        private int bankcard_id;

        public String getPos_type() {
            return pos_type == null ? "" : pos_type;
        }

        public void setPos_type(String pos_type) {
            this.pos_type = pos_type;
        }

        public String getReceive_name() {
            return receive_name == null ? "" : receive_name;
        }

        public void setReceive_name(String receive_name) {
            this.receive_name = receive_name;
        }

        public String getRemarks() {
            return remarks == null ? "" : remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getAp_id() {
            return ap_id;
        }

        public void setAp_id(int ap_id) {
            this.ap_id = ap_id;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public String getOperation() {
            return operation == null ? "" : operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getBankcard_name() {
            return bankcard_name == null ? "" : bankcard_name;
        }

        public void setBankcard_name(String bankcard_name) {
            this.bankcard_name = bankcard_name;
        }

        public int getUser_credit_card_id() {
            return user_credit_card_id;
        }

        public void setUser_credit_card_id(int user_credit_card_id) {
            this.user_credit_card_id = user_credit_card_id;
        }

        public String getPlan_type() {
            return plan_type == null ? "" : plan_type;
        }

        public void setPlan_type(String plan_type) {
            this.plan_type = plan_type;
        }

        public String getBankcard_num() {
            return bankcard_num == null ? "" : bankcard_num;
        }

        public void setBankcard_num(String bankcard_num) {
            this.bankcard_num = bankcard_num;
        }

        public int getBankcard_id() {
            return bankcard_id;
        }

        public void setBankcard_id(int bankcard_id) {
            this.bankcard_id = bankcard_id;
        }
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

    public String getQuestion() {
        return question == null ? "" : question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDeal() {
        return deal == null ? "" : deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    public List<Plan> getAutoplanning() {
        if (autoplanning == null) {
            return new ArrayList<>();
        }
        return autoplanning;
    }

    public void setAutoplanning(List<Plan> autoplanning) {
        this.autoplanning = autoplanning;
    }
}
