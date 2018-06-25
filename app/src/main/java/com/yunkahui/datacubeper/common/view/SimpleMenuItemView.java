package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * Created by Administrator on 2018/4/18.
 */

public class SimpleMenuItemView extends LinearLayout {

    private ImageView mImageViewIcon;
    private TextView mTextViewTitle;
    private ImageView mImageViewRightIcon;

    public SimpleMenuItemView(Context context) {
        this(context,null);
    }

    public SimpleMenuItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleMenuItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_simple_menu_item_view,this);

        mImageViewIcon=findViewById(R.id.image_view_icon);
        mTextViewTitle=findViewById(R.id.text_view_title);
        mImageViewRightIcon=findViewById(R.id.image_view_right_icon);

        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.SimpleMenuItemView);
        if(ta.hasValue(R.styleable.SimpleMenuItemView_menu_icon)){
            mImageViewIcon.setVisibility(GONE);
            mImageViewIcon.setImageResource(ta.getResourceId(R.styleable.SimpleMenuItemView_menu_icon,0));
        }
        if(ta.hasValue(R.styleable.SimpleMenuItemView_menu_title)){
            mTextViewTitle.setText(ta.getString(R.styleable.SimpleMenuItemView_menu_title));
        }
        if(ta.hasValue(R.styleable.SimpleMenuItemView_menu_right_icon)){
            mImageViewRightIcon.setImageResource(ta.getResourceId(R.styleable.SimpleMenuItemView_menu_right_icon,0));
        }
    }

    public void setTitle(String title){
        mTextViewTitle.setText(title);
    }

    public void setLeftIcon(int res){
        mImageViewIcon.setImageResource(res);
    }

    public void setRightIcon(int res){
        mImageViewRightIcon.setImageResource(res);
    }

}
