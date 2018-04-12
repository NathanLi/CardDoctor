package com.yunkahui.datacubeper.common.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class BaseBeanList<T> {

    private String respCode;

    private String respDesc;

    private List<T> respData;

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

    public List<T> getRespData() {
        if (respData == null) {
            return new ArrayList<>();
        }
        return respData;
    }

    public void setRespData(List<T> respData) {
        this.respData = respData;
    }

    @Override
    public String toString() {
        return "BaseBeanList{" +
                "respCode='" + respCode + '\'' +
                ", respDesc='" + respDesc + '\'' +
                ", respData=" + respData +
                '}';
    }
}
