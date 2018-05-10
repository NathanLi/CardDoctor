package com.yunkahui.datacubeper.common.bean;

/**
 * 记录列表bean
 */

public class Records {

    private int total;
    private int lastPage;
    private int pages;
    private int pageSize;
    private int size;
    private int pageNum;



    public class RecordData{

        private String org_name;
        private String rechargeType;
        private String receive_account;
        private float channel_cost;
        private String check_status;
        private String order_state;
        private int ope_cost;
        private String user_code;
        private String receive_name;
        private String callback_msg;

    }


}
