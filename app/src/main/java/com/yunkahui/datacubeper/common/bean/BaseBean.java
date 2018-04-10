package com.yunkahui.datacubeper.common.bean;

/**
 * Created by Administrator on 2018/4/10.
 */

public class BaseBean<T> {

    private String respCode;

    private String respDesc;

    private T respData;


    public String getRespCode() {
        return respCode == null ? "" : respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc == null ? "" : respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public T getRespData() {
        return respData;
    }

    public void setRespData(T respData) {
        this.respData = respData;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "respCode='" + respCode + '\'' +
                ", respDesc='" + respDesc + '\'' +
                ", respData=" + respData +
                '}';
    }
}
