package com.yunkahui.datacubeper.common.bean;

import java.io.Serializable;

/**
 * Created by YD1 on 2018/4/9
 */
public class CardTestItem {

    private String user_code;
    private String other_data;
    private String apr_return_datas;        //测评结果数据
    private long create_time;
    private String apr_poundage_status;
    private String apr_query_result;
    private String apr_tiepai_id;
    private double apr_once_request_price;
    private String apr_api_name_code;
    private int apr_api_id;
    private String apr_api_name;
    private int apr_id;

    private String apr_send_datas;

    private Card card;

    public class Card implements Serializable{

        String bankcard_tel;
        String card_level;
        String card_crowd_sign;
        String card_active;
        String card_high_end;
        String bank_name;
        String card_name;
        String card_brand;
        String bankcard_num;
        String identify_ID;
        String cardholder;

        public String getBankcard_tel() {
            return bankcard_tel == null ? "" : bankcard_tel;
        }

        public void setBankcard_tel(String bankcard_tel) {
            this.bankcard_tel = bankcard_tel;
        }

        public String getCard_level() {
            return card_level == null ? "" : card_level;
        }

        public void setCard_level(String card_level) {
            this.card_level = card_level;
        }

        public String getCard_crowd_sign() {
            return card_crowd_sign == null ? "" : card_crowd_sign;
        }

        public void setCard_crowd_sign(String card_crowd_sign) {
            this.card_crowd_sign = card_crowd_sign;
        }

        public String getCard_active() {
            return card_active == null ? "" : card_active;
        }

        public void setCard_active(String card_active) {
            this.card_active = card_active;
        }

        public String getCard_high_end() {
            return card_high_end == null ? "" : card_high_end;
        }

        public void setCard_high_end(String card_high_end) {
            this.card_high_end = card_high_end;
        }

        public String getBank_name() {
            return bank_name == null ? "" : bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getCard_name() {
            return card_name == null ? "" : card_name;
        }

        public void setCard_name(String card_name) {
            this.card_name = card_name;
        }

        public String getCard_brand() {
            return card_brand == null ? "" : card_brand;
        }

        public void setCard_brand(String card_brand) {
            this.card_brand = card_brand;
        }

        public String getBankcard_num() {
            return bankcard_num == null ? "" : bankcard_num;
        }

        public void setBankcard_num(String bankcard_num) {
            this.bankcard_num = bankcard_num;
        }

        public String getIdentify_ID() {
            return identify_ID == null ? "" : identify_ID;
        }

        public void setIdentify_ID(String identify_ID) {
            this.identify_ID = identify_ID;
        }

        public String getCardholder() {
            return cardholder == null ? "" : cardholder;
        }

        public void setCardholder(String cardholder) {
            this.cardholder = cardholder;
        }
    }

    public String getUser_code() {
        return user_code == null ? "" : user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getOther_data() {
        return other_data == null ? "" : other_data;
    }

    public void setOther_data(String other_data) {
        this.other_data = other_data;
    }

    public String getApr_return_datas() {
        return apr_return_datas == null ? "" : apr_return_datas;
    }

    public void setApr_return_datas(String apr_return_datas) {
        this.apr_return_datas = apr_return_datas;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getApr_poundage_status() {
        return apr_poundage_status == null ? "" : apr_poundage_status;
    }

    public void setApr_poundage_status(String apr_poundage_status) {
        this.apr_poundage_status = apr_poundage_status;
    }

    public String getApr_query_result() {
        return apr_query_result == null ? "" : apr_query_result;
    }

    public void setApr_query_result(String apr_query_result) {
        this.apr_query_result = apr_query_result;
    }

    public String getApr_tiepai_id() {
        return apr_tiepai_id == null ? "" : apr_tiepai_id;
    }

    public void setApr_tiepai_id(String apr_tiepai_id) {
        this.apr_tiepai_id = apr_tiepai_id;
    }

    public double getApr_once_request_price() {
        return apr_once_request_price;
    }

    public void setApr_once_request_price(double apr_once_request_price) {
        this.apr_once_request_price = apr_once_request_price;
    }

    public String getApr_api_name_code() {
        return apr_api_name_code == null ? "" : apr_api_name_code;
    }

    public void setApr_api_name_code(String apr_api_name_code) {
        this.apr_api_name_code = apr_api_name_code;
    }

    public int getApr_api_id() {
        return apr_api_id;
    }

    public void setApr_api_id(int apr_api_id) {
        this.apr_api_id = apr_api_id;
    }

    public String getApr_api_name() {
        return apr_api_name == null ? "" : apr_api_name;
    }

    public void setApr_api_name(String apr_api_name) {
        this.apr_api_name = apr_api_name;
    }

    public int getApr_id() {
        return apr_id;
    }

    public void setApr_id(int apr_id) {
        this.apr_id = apr_id;
    }

    public String getApr_send_datas() {
        return apr_send_datas == null ? "" : apr_send_datas;
    }

    public void setApr_send_datas(String apr_send_datas) {
        this.apr_send_datas = apr_send_datas;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
