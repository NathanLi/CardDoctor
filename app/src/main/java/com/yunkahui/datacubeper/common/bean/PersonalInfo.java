package com.yunkahui.datacubeper.common.bean;

/**
 * 个人信息
 */

public class PersonalInfo {

    private String user_unique_code;
    private String parent_name;
    private String user_role;
    private String VIP_count;
    private String user_mobile;
    private String user_qrcode_img;
    private String nickname;
    private String profit_count;
    private String avatar;
    private String user_role_text;
    private String identify_status;
    private String VIP_status;

    public String getUser_unique_code() {
        return user_unique_code == null ? "" : user_unique_code;
    }

    public void setUser_unique_code(String user_unique_code) {
        this.user_unique_code = user_unique_code;
    }

    public String getParent_name() {
        return parent_name == null ? "" : parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getUser_role() {
        return user_role == null ? "" : user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getVIP_count() {
        return VIP_count == null ? "" : VIP_count;
    }

    public void setVIP_count(String VIP_count) {
        this.VIP_count = VIP_count;
    }

    public String getUser_mobile() {
        return user_mobile == null ? "" : user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_qrcode_img() {
        return user_qrcode_img == null ? "" : user_qrcode_img;
    }

    public void setUser_qrcode_img(String user_qrcode_img) {
        this.user_qrcode_img = user_qrcode_img;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfit_count() {
        return profit_count == null ? "" : profit_count;
    }

    public void setProfit_count(String profit_count) {
        this.profit_count = profit_count;
    }

    public String getAvatar() {
        return avatar == null ? "" : avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_role_text() {
        return user_role_text == null ? "" : user_role_text;
    }

    public void setUser_role_text(String user_role_text) {
        this.user_role_text = user_role_text;
    }

    public String getIdentify_status() {
        return identify_status == null ? "" : identify_status;
    }

    public void setIdentify_status(String identify_status) {
        this.identify_status = identify_status;
    }

    public String getVIP_status() {
        return VIP_status == null ? "" : VIP_status;
    }

    public void setVIP_status(String VIP_status) {
        this.VIP_status = VIP_status;
    }

    @Override
    public String toString() {
        return "PersonalInfo{" +
                "user_unique_code='" + user_unique_code + '\'' +
                ", parent_name='" + parent_name + '\'' +
                ", user_role='" + user_role + '\'' +
                ", VIP_count='" + VIP_count + '\'' +
                ", user_mobile='" + user_mobile + '\'' +
                ", user_qrcode_img='" + user_qrcode_img + '\'' +
                ", nickname='" + nickname + '\'' +
                ", profit_count='" + profit_count + '\'' +
                ", avatar='" + avatar + '\'' +
                ", user_role_text='" + user_role_text + '\'' +
                ", identify_status='" + identify_status + '\'' +
                ", VIP_status='" + VIP_status + '\'' +
                '}';
    }
}
