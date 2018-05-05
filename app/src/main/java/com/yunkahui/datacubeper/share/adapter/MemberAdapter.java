package com.yunkahui.datacubeper.share.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.Member;
import com.yunkahui.datacubeper.common.utils.TimeUtils;

import java.util.List;

public class MemberAdapter extends BaseQuickAdapter<Member.MemberData, BaseViewHolder> {

    public MemberAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Member.MemberData item) {
        helper.setText(R.id.tv_nick_name, item.getNick_name());
        helper.setText(R.id.tv_phone_num, item.getTel().substring(0, 3) + "****" + item.getTel().substring(7, 11));
        helper.setText(R.id.tv_create_time, TimeUtils.format("yyyyMMdd", item.getCreate_time()));
    }

}
