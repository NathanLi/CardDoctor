package com.yunkahui.datacubeper.common.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.share.adapter.RecordMultListAdapter;

/**
 * Created by Administrator on 2018/6/12.
 */

public class RecordHeader extends AbstractExpandableItem<Records.RecordData> implements MultiItemEntity{

    private String time;
    private String come;
    private String pay;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCome() {
        return come;
    }

    public void setCome(String come) {
        this.come = come;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return RecordMultListAdapter.LEVLE_HEADER;
    }
}
