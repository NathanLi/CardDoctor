package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * Created by pc1994 on 2018/3/23.
 */
public class SimpleToolbar extends LinearLayout {

    private Context mContext;
    private TextView mTitle;
    private ImageView mRightIcon;

    public SimpleToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }
    
    public void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_simple_toolbar, this);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRightIcon = findViewById(R.id.iv_toolbar_right_icon);
    }

    public SimpleToolbar setTitleName(String titleName) {
        mTitle.setText(titleName);
        return this;
    }

    public SimpleToolbar setRightIcon(int imgRes) {
        mRightIcon.setImageResource(imgRes);
        return this;
    }

    public SimpleToolbar setRightIconClickListener(OnClickListener listener) {
        mRightIcon.setOnClickListener(listener);
        return this;
    }
}
