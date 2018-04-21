package com.yunkahui.datacubeper.common.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author WYF on 2018/4/20/020.
 */
public class TimeHeader extends SectionEntity<TimeBean> {

    public TimeHeader(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public TimeHeader(TimeBean timeBean) {
        super(timeBean);
    }
}
