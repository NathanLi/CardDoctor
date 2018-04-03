package com.yunkahui.datacubeper.common.bean;

/**
 * Created by pc1994 on 2018/3/23.
 */

public class HomeFeature {

    private int imgRes;
    private String title;

    public HomeFeature(int imgRes, String title) {
        this.imgRes = imgRes;
        this.title = title;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
