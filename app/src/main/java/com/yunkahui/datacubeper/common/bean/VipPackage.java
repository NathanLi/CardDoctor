package com.yunkahui.datacubeper.common.bean;

/**
 * VIP套餐
 */

public class VipPackage {

    private String menuAlias;
    private String menuName;
    private String menuPrice;
    private String menuShortTag;
    private String menuText;
    private String menuUserStatus;
    private String menuUserStatusTxt;

    public String getMenuAlias() {
        return menuAlias;
    }

    public void setMenuAlias(String menuAlias) {
        this.menuAlias = menuAlias;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }

    public String getMenuShortTag() {
        return menuShortTag;
    }

    public void setMenuShortTag(String menuShortTag) {
        this.menuShortTag = menuShortTag;
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }

    public String getMenuUserStatus() {
        return menuUserStatus;
    }

    public void setMenuUserStatus(String menuUserStatus) {
        this.menuUserStatus = menuUserStatus;
    }

    public String getMenuUserStatusTxt() {
        return menuUserStatusTxt;
    }

    public void setMenuUserStatusTxt(String menuUserStatusTxt) {
        this.menuUserStatusTxt = menuUserStatusTxt;
    }

    @Override
    public String toString() {
        return "VipPackage{" +
                "menuAlias='" + menuAlias + '\'' +
                ", menuName='" + menuName + '\'' +
                ", menuPrice='" + menuPrice + '\'' +
                ", menuShortTag='" + menuShortTag + '\'' +
                ", menuText='" + menuText + '\'' +
                ", menuUserStatus='" + menuUserStatus + '\'' +
                ", menuUserStatusTxt='" + menuUserStatusTxt + '\'' +
                '}';
    }
}
