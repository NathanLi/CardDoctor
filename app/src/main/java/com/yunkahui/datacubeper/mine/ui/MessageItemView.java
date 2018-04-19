package com.yunkahui.datacubeper.mine.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.Message;
import com.yunkahui.datacubeper.common.utils.SimpleDateFormatUtils;

import java.text.SimpleDateFormat;

/**
 * 消息列表Item
 */

public class MessageItemView extends LinearLayout {

    private TextView mTextViewTime;
    private TextView mTextViewTitle;
    private TextView mTextViewMessage;
    private TextView mTextViewStatus;

    public MessageItemView(Context context) {
        this(context,null);
    }

    public MessageItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MessageItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context,R.layout.layout_message_item_view,this);

        mTextViewTime=findViewById(R.id.text_view_time);
        mTextViewTitle=findViewById(R.id.text_view_title);
        mTextViewMessage=findViewById(R.id.text_view_message);
        mTextViewStatus=findViewById(R.id.text_view_message_status);
    }

    public void setData(Message message){
        mTextViewTime.setText(SimpleDateFormatUtils.formatYMDHS(message.getCreate_time()));
        mTextViewTitle.setText(message.getTitle());
        mTextViewMessage.setText(message.getContent_text());
        if(message.isSee()){
            mTextViewStatus.setVisibility(GONE);
        }else{
            mTextViewStatus.setVisibility(VISIBLE);
            mTextViewStatus.setText("未读");
        }

    }

}
