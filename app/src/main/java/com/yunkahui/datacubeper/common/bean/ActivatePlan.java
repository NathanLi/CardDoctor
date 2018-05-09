package com.yunkahui.datacubeper.common.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 激活规划bean
 */

public class ActivatePlan {

    private String execute_period_begin;
    private String execute_period_end;
    private int is_restart;
    private int is_replanning;
    private int is_warming;
    private String repay_total_money;
    private List<Plan> new_plannings;

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

    public String getExecute_period_begin() {
        return execute_period_begin == null ? "" : execute_period_begin;
    }

    public void setExecute_period_begin(String execute_period_begin) {
        this.execute_period_begin = execute_period_begin;
    }

    public String getExecute_period_end() {
        return execute_period_end == null ? "" : execute_period_end;
    }

    public void setExecute_period_end(String execute_period_end) {
        this.execute_period_end = execute_period_end;
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

    public int getIs_warming() {
        return is_warming;
    }

    public void setIs_warming(int is_warming) {
        this.is_warming = is_warming;
    }

    public String getRepay_total_money() {
        return repay_total_money == null ? "" : repay_total_money;
    }

    public void setRepay_total_money(String repay_total_money) {
        this.repay_total_money = repay_total_money;
    }

    public List<Plan> getNew_plannings() {
        if (new_plannings == null) {
            return new ArrayList<>();
        }
        return new_plannings;
    }

    public void setNew_plannings(List<Plan> new_plannings) {
        this.new_plannings = new_plannings;
    }
}
