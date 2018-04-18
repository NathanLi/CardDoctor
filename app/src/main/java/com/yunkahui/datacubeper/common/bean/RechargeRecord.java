package com.yunkahui.datacubeper.common.bean;

import java.util.List;

public class RechargeRecord {


    /**
     * pageNum : 1
     * pageSize : 20
     * size : 4
     * startRow : 1
     * endRow : 4
     * total : 4
     * pages : 1
     * list : [{"recharge_order_id":23,"create_time":1523425930000,"update_time":1523425930000,"order_type":"00","yd_order_sn":"acd915ef37ac4f0b8ce40d3754d9a963","third_party_trade_sn":"2804C552623842379C6CAD4206AF0C0B","payment_channel":"00","callback_status":"0000","callback_msg":"成功","amount":8,"fee":0.07,"ope_cost":0,"channel_cost":1.76,"pay_account":"6221683210143692","pay_name":"韦晴允","receive_account":"32424343432@qq.com","receive_name":"罗银梓","order_state":"1","org_name":"","check_status":"2","org_number":"Y1000000001","user_code":"cv4ighrmoz","rechargeType":"","amountString":"8.00","type":"recharge"},{"recharge_order_id":22,"create_time":1523352271000,"update_time":1523352271000,"order_type":"00","yd_order_sn":"c3390aa0654446e18009cfa14d51c08e","third_party_trade_sn":"9A5CFC7F743B424DB63A0E6E3364A93B","payment_channel":"00","callback_status":"0000","callback_msg":"成功","amount":10,"fee":0.09,"ope_cost":0,"channel_cost":2.2,"pay_account":"6221683210143692","pay_name":"韦晴允","receive_account":"32424343432@qq.com","receive_name":"罗银梓","order_state":"1","org_name":"","check_status":"2","org_number":"Y1000000001","user_code":"cv4ighrmoz","rechargeType":"","amountString":"10.00","type":"recharge"},{"recharge_order_id":21,"create_time":1523344045000,"update_time":1523344045000,"order_type":"00","yd_order_sn":"d484516f4ee34af0924185c193749133","third_party_trade_sn":"628AEE5F3F8641E8A0353D01332F7D7A","payment_channel":"00","callback_status":"0000","callback_msg":"成功","amount":2,"fee":0.02,"ope_cost":0,"channel_cost":0.44,"pay_account":"6221683210143692","pay_name":"韦晴允","receive_account":"32424343432@qq.com","receive_name":"罗银梓","order_state":"1","org_name":"","check_status":"2","org_number":"Y1000000001","user_code":"cv4ighrmoz","rechargeType":"","amountString":"2.00","type":"recharge"},{"recharge_order_id":18,"create_time":1523342152000,"update_time":1523342152000,"order_type":"00","yd_order_sn":"373657b011de4064b4523198dd48020a","third_party_trade_sn":"","payment_channel":"","callback_status":"","callback_msg":"","amount":3,"fee":0.03,"ope_cost":0,"channel_cost":0.66,"pay_account":"6221683210143692","pay_name":"韦晴允","receive_account":"32424343432@qq.com","receive_name":"罗银梓","order_state":"0","org_name":"","check_status":"2","org_number":"Y1000000001","user_code":"cv4ighrmoz","rechargeType":"","amountString":"3.00","type":"recharge"}]
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
    private List<RechargeDetail> list;
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

    public List<RechargeDetail> getList() {
        return list;
    }

    public void setList(List<RechargeDetail> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class RechargeDetail {
        /**
         * recharge_order_id : 23
         * create_time : 1523425930000
         * update_time : 1523425930000
         * order_type : 00
         * yd_order_sn : acd915ef37ac4f0b8ce40d3754d9a963
         * third_party_trade_sn : 2804C552623842379C6CAD4206AF0C0B
         * payment_channel : 00
         * callback_status : 0000
         * callback_msg : 成功
         * amount : 8
         * fee : 0.07
         * ope_cost : 0
         * channel_cost : 1.76
         * pay_account : 6221683210143692
         * pay_name : 韦晴允
         * receive_account : 32424343432@qq.com
         * receive_name : 罗银梓
         * order_state : 1
         * org_name :
         * check_status : 2
         * org_number : Y1000000001
         * user_code : cv4ighrmoz
         * rechargeType :
         * amountString : 8.00
         * type : recharge
         */

        private int recharge_order_id;
        private long create_time;
        private long update_time;
        private String order_type;
        private String yd_order_sn;
        private String third_party_trade_sn;
        private String payment_channel;
        private String callback_status;
        private String callback_msg;
        private int amount;
        private double fee;
        private int ope_cost;
        private double channel_cost;
        private String pay_account;
        private String pay_name;
        private String receive_account;
        private String receive_name;
        private String order_state;
        private String org_name;
        private String check_status;
        private String org_number;
        private String user_code;
        private String rechargeType;
        private String amountString;
        private String type;

        public int getRecharge_order_id() {
            return recharge_order_id;
        }

        public void setRecharge_order_id(int recharge_order_id) {
            this.recharge_order_id = recharge_order_id;
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

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getYd_order_sn() {
            return yd_order_sn;
        }

        public void setYd_order_sn(String yd_order_sn) {
            this.yd_order_sn = yd_order_sn;
        }

        public String getThird_party_trade_sn() {
            return third_party_trade_sn;
        }

        public void setThird_party_trade_sn(String third_party_trade_sn) {
            this.third_party_trade_sn = third_party_trade_sn;
        }

        public String getPayment_channel() {
            return payment_channel;
        }

        public void setPayment_channel(String payment_channel) {
            this.payment_channel = payment_channel;
        }

        public String getCallback_status() {
            return callback_status;
        }

        public void setCallback_status(String callback_status) {
            this.callback_status = callback_status;
        }

        public String getCallback_msg() {
            return callback_msg;
        }

        public void setCallback_msg(String callback_msg) {
            this.callback_msg = callback_msg;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public int getOpe_cost() {
            return ope_cost;
        }

        public void setOpe_cost(int ope_cost) {
            this.ope_cost = ope_cost;
        }

        public double getChannel_cost() {
            return channel_cost;
        }

        public void setChannel_cost(double channel_cost) {
            this.channel_cost = channel_cost;
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

        public String getOrder_state() {
            return order_state;
        }

        public void setOrder_state(String order_state) {
            this.order_state = order_state;
        }

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public String getCheck_status() {
            return check_status;
        }

        public void setCheck_status(String check_status) {
            this.check_status = check_status;
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

        public String getRechargeType() {
            return rechargeType;
        }

        public void setRechargeType(String rechargeType) {
            this.rechargeType = rechargeType;
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
