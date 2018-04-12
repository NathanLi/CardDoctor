package com.yunkahui.datacubeper.common.bean;

/**
 * 申请代理商昵称信息
 */

public class AgentType {

    private String id;
    private String name;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AgentType{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
