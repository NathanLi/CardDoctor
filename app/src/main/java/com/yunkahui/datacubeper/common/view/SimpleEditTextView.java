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
 * Created by Administrator on 2018/4/11.
 */

public class SimpleEditTextView extends LinearLayout {

    private TextView mTextViewTitle;
    private EditText mEditTextInput;

    public SimpleEditTextView(Context context) {
        this(context,null);
    }

    public SimpleEditTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleEditTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_simple_input_item_view,this);

        mTextViewTitle=findViewById(R.id.tv_title);
        mEditTextInput=findViewById(R.id.edit_text_input);

        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.SimpleEditTextView);
        if(ta.hasValue(R.styleable.SimpleEditTextView_item_edit_title)){
            mTextViewTitle.setText(ta.getString(R.styleable.SimpleEditTextView_item_edit_title));
        }
        if(ta.hasValue(R.styleable.SimpleEditTextView_item_edit_hint)){
            mEditTextInput.setHint(ta.getString(R.styleable.SimpleEditTextView_item_edit_hint));
        }
        if(ta.hasValue(R.styleable.SimpleEditTextView_item_edit_input_type)){
            if(ta.getInt(R.styleable.SimpleEditTextView_item_edit_input_type,0)==128){
                mEditTextInput.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }else{
                mEditTextInput.setInputType(ta.getInt(R.styleable.SimpleEditTextView_item_edit_input_type,InputType.TYPE_CLASS_TEXT));
            }

        }
        if(ta.hasValue(R.styleable.SimpleEditTextView_item_edit_text)){
            mEditTextInput.setText(ta.getString(R.styleable.SimpleEditTextView_item_edit_text));
        }
        if(ta.hasValue(R.styleable.SimpleEditTextView_item_edit_enable)){
            mEditTextInput.setEnabled(ta.getBoolean(R.styleable.SimpleEditTextView_item_edit_enable,true));
        }
        if(ta.hasValue(R.styleable.SimpleEditTextView_item_edit_focusable)){
            boolean focusable=ta.getBoolean(R.styleable.SimpleEditTextView_item_edit_focusable,true);
            mEditTextInput.setFocusable(focusable);
            mEditTextInput.setFocusableInTouchMode(false);
            mEditTextInput.setCursorVisible(false);
        }

        if(ta.hasValue(R.styleable.SimpleEditTextView_item_edit_right_icon)){
            Drawable drawable= getResources().getDrawable(ta.getResourceId(R.styleable.SimpleEditTextView_item_edit_right_icon,0));
            if(drawable!=null){
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mEditTextInput.setCompoundDrawables(null,null,drawable,null);
            }
        }

        ta.recycle();
    }

    public void setText(String text){
        mEditTextInput.setText(text);
    }

    public String getText(){
        return mEditTextInput.getText().toString();
    }

    public void setEnabled(boolean enable){
        mEditTextInput.setEnabled(enable);
    }

    public EditText getEditTextInput(){
        return mEditTextInput;
    }

}
