package com.yunkahui.datacubeper.applyreceipt.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

/**
 * 申请pos 进度
 */

public class ProgressApplyReceiptView extends RelativeLayout {

    private TextView mTextViewCircle1;
    private TextView mTextViewTitle1;
    private TextView mTextViewCircle2;
    private TextView mTextViewTitle2;
    private TextView mTextViewCircle3;
    private TextView mTextViewTitle3;
    private TextView mTextViewCircle4;
    private TextView mTextViewTitle4;

    public ProgressApplyReceiptView(Context context) {
        this(context,null);
    }

    public ProgressApplyReceiptView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressApplyReceiptView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_progress_bar_apply_receipt,this);

        mTextViewCircle1=findViewById(R.id.text_view_circle_1);
        mTextViewCircle2=findViewById(R.id.text_view_circle_2);
        mTextViewCircle3=findViewById(R.id.text_view_circle_3);
        mTextViewCircle4=findViewById(R.id.text_view_circle_4);
        mTextViewTitle1=findViewById(R.id.text_view_title_1);
        mTextViewTitle2=findViewById(R.id.text_view_title_2);
        mTextViewTitle3=findViewById(R.id.text_view_title_3);
        mTextViewTitle4=findViewById(R.id.text_view_title_4);

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
