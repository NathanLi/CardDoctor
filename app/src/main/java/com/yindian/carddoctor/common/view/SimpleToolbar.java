package com.yindian.carddoctor.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yindian.carddoctor.R;

/**
 * Created by pc1994 on 2018/3/23.
 */
public class SimpleToolbar extends LinearLayout {

    private Context context;
    private TextView title;
    private ImageView rightIcon;
    private String TAG = "simpletoolbar";


    public SimpleToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }
    
    public void init() {
        LayoutInflater.from(context).inflate(R.layout.simple_toolbar, this);
        title = findViewById(R.id.tv_toolbar_title);
        rightIcon = findViewById(R.id.iv_toolbar_right_icon);
    }

    public SimpleToolbar setTitleName(String titleName) {
        title.setText(titleName);
        return this;
    }

    public SimpleToolbar setRightIcon(int imgRes) {
        rightIcon.setImageResource(imgRes);
        return this;
    }

    public SimpleToolbar setRightIconClickListener(OnClickListener listener) {
        rightIcon.setOnClickListener(listener);
        return this;
    }
}
