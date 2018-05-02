package com.yunkahui.datacubeper.common.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/4/17.
 */

@Entity
public class Message {

    private String sys_notice_id;
    private long create_time;
    private long update_time;
    private String org_number;
    private String title;
    private String content_text;
    private String content_img;
    private String notice_type;
    private String is_show;
    private boolean isSee;

    @Generated(hash = 1306491521)
    public Message(String sys_notice_id, long create_time, long update_time,
            String org_number, String title, String content_text,
            String content_img, String notice_type, String is_show, boolean isSee) {
        this.sys_notice_id = sys_notice_id;
        this.create_time = create_time;
        this.update_time = update_time;
        this.org_number = org_number;
        this.title = title;
        this.content_text = content_text;
        this.content_img = content_img;
        this.notice_type = notice_type;
        this.is_show = is_show;
        this.isSee = isSee;
    }

    @Generated(hash = 637306882)
    public Message() {
    }

    public String getSys_notice_id() {
        return sys_notice_id == null ? "" : sys_notice_id;
    }

    public void setSys_notice_id(String sys_notice_id) {
        this.sys_notice_id = sys_notice_id;
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

    public String getOrg_number() {
        return org_number == null ? "" : org_number;
    }

    public void setOrg_number(String org_number) {
        this.org_number = org_number;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent_text() {
        return content_text == null ? "" : content_text;
    }

    public void setContent_text(String content_text) {
        this.content_text = content_text;
    }

    public String getContent_img() {
        return content_img == null ? "" : content_img;
    }

    public void setContent_img(String content_img) {
        this.content_img = content_img;
    }

    public String getNotice_type() {
        return notice_type == null ? "" : notice_type;
    }

    public void setNotice_type(String notice_type) {
        this.notice_type = notice_type;
    }

    public String getIs_show() {
        return is_show == null ? "" : is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }


    public boolean isSee() {
        return isSee;
    }

    public void setSee(boolean see) {
        isSee = see;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sys_notice_id='" + sys_notice_id + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", org_number='" + org_number + '\'' +
                ", title='" + title + '\'' +
                ", content_text='" + content_text + '\'' +
                ", content_img='" + content_img + '\'' +
                ", notice_type='" + notice_type + '\'' +
                ", is_show='" + is_show + '\'' +
                ", isSee=" + isSee +
                '}';
    }

    public boolean getIsSee() {
        return this.isSee;
    }

    public void setIsSee(boolean isSee) {
        this.isSee = isSee;
    }
}
