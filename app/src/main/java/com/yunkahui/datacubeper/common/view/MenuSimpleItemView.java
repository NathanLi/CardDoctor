package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * Created by Administrator on 2018/4/11.
 */

public class MenuSimpleItemView extends RelativeLayout {

    private TextView mTextViewTitle;
    private TextView mTextViewSubTitle;


    public MenuSimpleItemView(Context context) {
        this(context,null);
    }

    public MenuSimpleItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MenuSimpleItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_menu_simple_item_view,this);
        mTextViewTitle=findViewById(R.id.text_view_title);
        mTextViewSubTitle=findViewById(R.id.text_view_subtitle);

        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.MenuSimpleItemView);

        if(ta.hasValue(R.styleable.MenuSimpleItemView_item_title)){
            mTextViewTitle.setText(ta.getString(R.styleable.MenuSimpleItemView_item_title));
        }
        if(ta.hasValue(R.styleable.MenuSimpleItemView_item_subtitle)){
            mTextViewSubTitle.setText(ta.getString(R.styleable.MenuSimpleItemView_item_subtitle));
        }

        ta.recycle();
    }


    public void setSubTitle(String text){
        mTextViewSubTitle.setText(text);
    }

}
