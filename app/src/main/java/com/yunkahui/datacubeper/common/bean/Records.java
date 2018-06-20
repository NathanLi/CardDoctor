package com.yunkahui.datacubeper.common.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunkahui.datacubeper.share.adapter.AllRecordMultListAdapter;

import java.util.List;

/**
 * 记录列表bean
 */

public class Records  {

    private int total;
    private int lastPage;
    private int pages;
    private int pageSize;
    private int size;
    private int pageNum;
    private List<RecordData> list;

    public class RecordData implements MultiItemEntity{

        @Override
        public int getItemType() {
            return AllRecordMultListAdapter.LEVLE_HEADER;
        }

        String change_amount;
        String trade_type_desc;
        long update_time;
        double begin_amount;
        long create_time;
        double end_amount;
        String trade_type;

        public String getChange_amount() {
            return change_amount;
        }

        public void setChange_amount(String change_amount) {
            this.change_amount = change_amount;
        }

        public String getTrade_type_desc() {
            return trade_type_desc;
        }

        public void setTrade_type_desc(String trade_type_desc) {
            this.trade_type_desc = trade_type_desc;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public double getBegin_amount() {
            return begin_amount;
        }

        public void setBegin_amount(double begin_amount) {
            this.begin_amount = begin_amount;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public double getEnd_amount() {
            return end_amount;
        }

        public void setEnd_amount(double end_amount) {
            this.end_amount = end_amount;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }
    }

    public List<RecordData> getList() {
        return list;
    }

    public void setList(List<RecordData> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
