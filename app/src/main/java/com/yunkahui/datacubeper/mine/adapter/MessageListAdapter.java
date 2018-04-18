package com.yunkahui.datacubeper.mine.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.Message;
import com.yunkahui.datacubeper.mine.ui.MessageItemView;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 */

public class MessageListAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {

    public MessageListAdapter(int layoutResId, @Nullable List<Message> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        ((MessageItemView) helper.getView(R.id.message_item_view)).setData(item);
    }
}
