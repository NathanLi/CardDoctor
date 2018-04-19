package com.yunkahui.datacubeper.applypos.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * 申请pos 进度
 */

public class ProgressApplyPosView extends RelativeLayout {

    private TextView mTextViewCircle1;
    private TextView mTextViewTitle1;
    private TextView mTextViewCircle2;
    private TextView mTextViewTitle2;
    private TextView mTextViewCircle3;
    private TextView mTextViewTitle3;
    private TextView mTextViewCircle4;
    private TextView mTextViewTitle4;
    private TextView mTextViewCircle5;
    private TextView mTextViewTitle5;

    public ProgressApplyPosView(Context context) {
        this(context,null);
    }

    public ProgressApplyPosView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressApplyPosView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_progress_bar_apply_pos,this);

        mTextViewCircle1=findViewById(R.id.text_view_circle_1);
        mTextViewCircle2=findViewById(R.id.text_view_circle_2);
        mTextViewCircle3=findViewById(R.id.text_view_circle_3);
        mTextViewCircle4=findViewById(R.id.text_view_circle_4);
        mTextViewCircle5=findViewById(R.id.text_view_circle_5);
        mTextViewTitle1=findViewById(R.id.text_view_title_1);
        mTextViewTitle2=findViewById(R.id.text_view_title_2);
        mTextViewTitle3=findViewById(R.id.text_view_title_3);
        mTextViewTitle4=findViewById(R.id.text_view_title_4);
        mTextViewTitle5=findViewById(R.id.text_view_title_5);

    }


    public void setProgress(int progress){

        switch (progress){
            case 1:
                mTextViewTitle1.setTextColor(Color.parseColor("#ff9d09"));
                break;
            case 2:
                mTextViewTitle2.setTextColor(Color.parseColor("#ff9d09"));
                break;
            case 3:
                mTextViewTitle3.setTextColor(Color.parseColor("#ff9d09"));
                break;
            case 4:
                mTextViewTitle4.setTextColor(Color.parseColor("#ff9d09"));
                break;
            case 5:
                mTextViewTitle5.setTextColor(Color.parseColor("#ff9d09"));
                break;
        }

        if(progress>4){
            mTextViewCircle5.setBackgroundResource(R.drawable.bg_circle_orange_select);
        }
        if(progress>3){
            mTextViewCircle4.setBackgroundResource(R.drawable.bg_circle_orange_select);
        }
        if(progress>2){
            mTextViewCircle3.setBackgroundResource(R.drawable.bg_circle_orange_select);
        }
        if(progress>1){
            mTextViewCircle2.setBackgroundResource(R.drawable.bg_circle_orange_select);
        }
        if(progress>0){
            mTextViewCircle1.setBackgroundResource(R.drawable.bg_circle_orange_select);
        }
    }



}
