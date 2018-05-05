package com.yunkahui.datacubeper.common.bean;

import java.util.List;

public class PlanList {


    /**
     * pageNum : 1
     * pageSize : 10
     * size : 10
     * startRow : 1
     * endRow : 10
     * total : 24
     * pages : 3
     * list : [{"ap_id":276,"user_credit_card_id":19,"plan_type":"01","amount":2970,"date":1525764708954,"operation":"1","pos_type":"10","swift_number":"","trade_no":"ec06d60018de4d8c95b861f32549cfe1","batch_sn":"20180504173755909828","remarks":"","create_time":1525426691000,"update_time":1525426691000,"service_charge":"","bankCardName":"工商银行","bankCardNum":"3696","org_number":"Y1000000001","user_code":"9mg6thwi7d","business_name":"","amountString":"","fee":"","ope_cost":"","channel_cost":"","extra_ope_cost":"","third_party_trade_sn":"","third_party_res_code":"","third_party_msg":"","pay_account":"","pay_name":"","receive_account":"","receive_name":"","check_status":"","orgName":""}]
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private List<PlanListBean> list;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<PlanListBean> getList() {
        return list;
    }

    public void setList(List<PlanListBean> list) {
        this.list = list;
    }

    public static class PlanListBean implements DesignSub {
        /**
         * ap_id : 276
         * user_credit_card_id : 19
         * plan_type : 01
         * amount : 2970
         * date : 1525764708954
         * operation : 1
         * pos_type : 10
         * swift_number :
         * trade_no : ec06d60018de4d8c95b861f32549cfe1
         * batch_sn : 20180504173755909828
         * remarks :
         * create_time : 1525426691000
         * update_time : 1525426691000
         * service_charge :
         * bankCardName : 工商银行
         * bankCardNum : 3696
         * org_number : Y1000000001
         * user_code : 9mg6thwi7d
         * business_name :
         * amountString :
         * fee :
         * ope_cost :
         * channel_cost :
         * extra_ope_cost :
         * third_party_trade_sn :
         * third_party_res_code :
         * third_party_msg :
         * pay_account :
         * pay_name :
         * receive_account :
         * receive_name :
         * check_status :
         * orgName :
         */

        private int ap_id;
        private int user_credit_card_id;
        private String plan_type;
        private int amount;
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
        private String fee;
        private String ope_cost;
        private String channel_cost;
        private String extra_ope_cost;
        private String third_party_trade_sn;
        private String third_party_res_code;
        private String third_party_msg;
        private String pay_account;
        private String pay_name;
        private String receive_account;
        private String receive_name;
        private String check_status;
        private String orgName;

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

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
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

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getOpe_cost() {
            return ope_cost;
        }

        public void setOpe_cost(String ope_cost) {
            this.ope_cost = ope_cost;
        }

        public String getChannel_cost() {
            return channel_cost;
        }

        public void setChannel_cost(String channel_cost) {
            this.channel_cost = channel_cost;
        }

        public String getExtra_ope_cost() {
            return extra_ope_cost;
        }

        public void setExtra_ope_cost(String extra_ope_cost) {
            this.extra_ope_cost = extra_ope_cost;
        }

        public String getThird_party_trade_sn() {
            return third_party_trade_sn;
        }

        public void setThird_party_trade_sn(String third_party_trade_sn) {
            this.third_party_trade_sn = third_party_trade_sn;
        }

        public String getThird_party_res_code() {
            return third_party_res_code;
        }

        public void setThird_party_res_code(String third_party_res_code) {
            this.third_party_res_code = third_party_res_code;
        }

        public String getThird_party_msg() {
            return third_party_msg;
        }

        public void setThird_party_msg(String third_party_msg) {
            this.third_party_msg = third_party_msg;
        }

        public String getPay_account() {
            return pay_account;
        }

        public void setPay_account(String pay_account) {
            this.pay_account = pay_account;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
        }

        public String getReceive_account() {
            return receive_account;
        }

        public void setReceive_account(String receive_account) {
            this.receive_account = receive_account;
        }

        public String getReceive_name() {
            return receive_name;
        }

        public void setReceive_name(String receive_name) {
            this.receive_name = receive_name;
        }

        public String getCheck_status() {
            return check_status;
        }

        public void setCheck_status(String check_status) {
            this.check_status = check_status;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }
    }
}
