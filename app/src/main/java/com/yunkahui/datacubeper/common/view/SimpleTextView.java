package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * Created by Administrator on 2018/4/24.
 */

public class SimpleTextView extends LinearLayout {

    private TextView mTextViewTitle;
    private TextView mTextViewMessage;


    public SimpleTextView(Context context) {
        this(context,null);
    }

    public SimpleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_simple_text_view,this);

        mTextViewTitle=findViewById(R.id.tv_title);
        mTextViewMessage=findViewById(R.id.text_view_message);

        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.SimpleTextView);
        if(ta.hasValue(R.styleable.SimpleTextView_simple_title)){
            mTextViewTitle.setText(ta.getString(R.styleable.SimpleTextView_simple_title));
        }
        if(ta.hasValue(R.styleable.SimpleTextView_simple_hint)){
            mTextViewMessage.setHint(ta.getString(R.styleable.SimpleTextView_simple_hint));
        }
        if(ta.hasValue(R.styleable.SimpleTextView_simple_text)){
            mTextViewMessage.setText(ta.getString(R.styleable.SimpleTextView_simple_text));
        }

        if(ta.hasValue(R.styleable.SimpleTextView_simple_right_icon)){
            Drawable drawable= getResources().getDrawable(ta.getResourceId(R.styleable.SimpleTextView_simple_right_icon,0));
            if(drawable!=null){
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mTextViewMessage.setCompoundDrawables(null,null,drawable,null);
            }
        }

        ta.recycle();

    }

    public void setText(String text){
        mTextViewMessage.setText(text);
    }

    public String getText(){
        return mTextViewMessage.getText().toString();
    }

    public void setEnabled(boolean enable){
        mTextViewMessage.setEnabled(enable);
    }

    public TextView getTextView(){
        return mTextViewMessage;
    }

}
