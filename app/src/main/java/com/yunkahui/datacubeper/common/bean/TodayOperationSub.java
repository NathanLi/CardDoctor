package com.yunkahui.datacubeper.common.bean;

import java.util.List;

public class TodayOperationSub {

    /*"pageNum": 1, 当前页
            "pageSize": 20,
            "size": 2, 当前页数量
            "startRow": 1,
            "endRow": 2,
            "total": 2,
            "pages": 1, 总页数
            "list": [{
        "ap_id": 39,
                "user_credit_card_id": 15,
                "plan_type": "01",
                "amount": 100.00,
                "date": 1523930181000,
                "operation": "1",
                "pos_type": "10",
                "swift_number": "",
                "trade_no": "",
                "batch_sn": "",
                "remarks": "",
                "create_time": "",
                "update_time": 1523924907000,
                "service_charge": "",
                "bankCardName": "建设银行",
                "bankCardNum": "6221683210143692",
                "org_number": "",
                "user_code": "",
                "business_name": "",
                "amountString": "100.00"
    }, {
        "ap_id": 40,
                "user_credit_card_id": 15,
                "plan_type": "00",
                "amount": 100.00,
                "date": 1523930191000,
                "operation": "1",
                "pos_type": "10",
                "swift_number": "",
                "trade_no": "",
                "batch_sn": "",
                "remarks": "",
                "create_time": "",
                "update_time": 1523924907000,
                "service_charge": "",
                "bankCardName": "建设银行",
                "bankCardNum": "6221683210143692",
                "org_number": "",
                "user_code": "",
                "business_name": "",
                "amountString": "100.00"
    }],
            "prePage": 0,
            "nextPage": 0,
            "isFirstPage": true,
            "isLastPage": true,
            "hasPreviousPage": false,
            "hasNextPage": false,
            "navigatePages": 8,
            "navigatepageNums": [1],
            "navigateFirstPage": 1,
            "navigateLastPage": 1,
            "firstPage": 1,
            "lastPage": 1*/

    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private List<DesignSub> list;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private int[] navigatepageNums;
    private int navigateFirstPage;
    private int navigateLastPage;
    private int firstPage;
    private int lastPage;

    public static class DesignSub implements com.yunkahui.datacubeper.common.bean.DesignSub {

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
        private String create_time;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
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

    public List<DesignSub> getList() {
        return list;
    }

    public void setList(List<DesignSub> list) {
        this.list = list;
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

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
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

    public int[] getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
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
}
