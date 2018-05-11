package com.yunkahui.datacubeper.common.bean;

import java.util.List;

public class WithdrawRecord {


    /**
     * pageNum : 1
     * pageSize : 20
     * size : 1
     * startRow : 1
     * endRow : 1
     * total : 1
     * pages : 1
     * list : [{"wo_id":36,"user_code":"cv4ighrmoz","yd_order_sn":"1590031717fa40a8b43268c8c6a3faed","withdraw_type":"02","order_state":"0","withdraw_amount":2,"fee":2,"channel_cost":18,"ope_cost":9,"audit_id":"","audit_state":"2","third_party_trade_sn":"","third_party_res_code":"","third_party_msg":"","create_time":1524033148000,"update_time":1523418795000,"bank_name":"广发银行","pay_account":"","pay_name":"","receive_account":"6214622121001879231","receive_name":"韦晴允","org_name":"","bank_code":"","bind_id":"505e6b77f98149aea7c8785156ad1fcd","org_number":"Y1000000001","check_status":"2","amountString":"2.00","type":"withdraw"}]
     * prePage : 0
     * nextPage : 0
     * isFirstPage : true
     * isLastPage : true
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 8
     * navigatepageNums : [1]
     * navigateFirstPage : 1
     * navigateLastPage : 1
     * firstPage : 1
     * lastPage : 1
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private int navigateFirstPage;
    private int navigateLastPage;
    private int firstPage;
    private int lastPage;
    private List<WithdrawDetail> list;
    private List<Integer> navigatepageNums;

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

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<WithdrawDetail> getList() {
        return list;
    }

    public void setList(List<WithdrawDetail> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class WithdrawDetail {
        /**
         * wo_id : 36
         * user_code : cv4ighrmoz
         * yd_order_sn : 1590031717fa40a8b43268c8c6a3faed
         * withdraw_type : 02
         * order_state : 0
         * withdraw_amount : 2
         * fee : 2
         * channel_cost : 18
         * ope_cost : 9
         * audit_id :
         * audit_state : 2
         * third_party_trade_sn :
         * third_party_res_code :
         * third_party_msg :
         * create_time : 1524033148000
         * update_time : 1523418795000
         * bank_name : 广发银行
         * pay_account :
         * pay_name :
         * receive_account : 6214622121001879231
         * receive_name : 韦晴允
         * org_name :
         * bank_code :
         * bind_id : 505e6b77f98149aea7c8785156ad1fcd
         * org_number : Y1000000001
         * check_status : 2
         * amountString : 2.00
         * type : withdraw
         */

        private int wo_id;
        private String user_code;
        private String yd_order_sn;
        private String withdraw_type;
        private String order_state;
        private float withdraw_amount;
        private int fee;
        private int channel_cost;
        private int ope_cost;
        private String audit_id;
        private String audit_state;
        private String third_party_trade_sn;
        private String third_party_res_code;
        private String third_party_msg;
        private long create_time;
        private long update_time;
        private String bank_name;
        private String pay_account;
        private String pay_name;
        private String receive_account;
        private String receive_name;
        private String org_name;
        private String bank_code;
        private String bind_id;
        private String org_number;
        private String check_status;
        private String amountString;
        private String type;

        public int getWo_id() {
            return wo_id;
        }

        public void setWo_id(int wo_id) {
            this.wo_id = wo_id;
        }

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public String getYd_order_sn() {
            return yd_order_sn;
        }

        public void setYd_order_sn(String yd_order_sn) {
            this.yd_order_sn = yd_order_sn;
        }

        public String getWithdraw_type() {
            return withdraw_type;
        }

        public void setWithdraw_type(String withdraw_type) {
            this.withdraw_type = withdraw_type;
        }

        public String getOrder_state() {
            return order_state;
        }

        public void setOrder_state(String order_state) {
            this.order_state = order_state;
        }

        public float getWithdraw_amount() {
            return withdraw_amount;
        }

        public void setWithdraw_amount(int withdraw_amount) {
            this.withdraw_amount = withdraw_amount;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public int getChannel_cost() {
            return channel_cost;
        }

        public void setChannel_cost(int channel_cost) {
            this.channel_cost = channel_cost;
        }

        public int getOpe_cost() {
            return ope_cost;
        }

        public void setOpe_cost(int ope_cost) {
            this.ope_cost = ope_cost;
        }

        public String getAudit_id() {
            return audit_id;
        }

        public void setAudit_id(String audit_id) {
            this.audit_id = audit_id;
        }

        public String getAudit_state() {
            return audit_state;
        }

        public void setAudit_state(String audit_state) {
            this.audit_state = audit_state;
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

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
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

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public String getBank_code() {
            return bank_code;
        }

        public void setBank_code(String bank_code) {
            this.bank_code = bank_code;
        }

        public String getBind_id() {
            return bind_id;
        }

        public void setBind_id(String bind_id) {
            this.bind_id = bind_id;
        }

        public String getOrg_number() {
            return org_number;
        }

        public void setOrg_number(String org_number) {
            this.org_number = org_number;
        }

        public String getCheck_status() {
            return check_status;
        }

        public void setCheck_status(String check_status) {
            this.check_status = check_status;
        }

        public String getAmountString() {
            return amountString;
        }

        public void setAmountString(String amountString) {
            this.amountString = amountString;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
