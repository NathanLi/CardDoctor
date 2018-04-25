package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;

import java.util.List;

/**
 * Created by clint on 2017/9/18.
 */

public class FillSpinnerView extends CoordinatorLayout {
    private Context mContext;
    private TextView titleText;
    private AppCompatSpinner spinner;
    private ImageView imageView;
    private View lineView;

    public FillSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        try {
            LayoutInflater.from(context).inflate(R.layout.layout_fill_spinner, this, true);
            titleText = (TextView)findViewById(R.id.show_title);
            spinner = (AppCompatSpinner) findViewById(R.id.show_spinner);
            imageView = (ImageView)findViewById(R.id.show_img);
            lineView = findViewById(R.id.show_line);

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FillSpinnerView);
            String title = typedArray.getString(R.styleable.FillSpinnerView_title);
            if (title != null) {
                titleText.setText(title);
            }
            Drawable image = typedArray.getDrawable(R.styleable.FillSpinnerView_src);
            if (image != null) {
                imageView.setImageDrawable(image);
            }
            boolean isLine = typedArray.getBoolean(R.styleable.FillSpinnerView_isLine, true);
            lineView.setVisibility(isLine ? VISIBLE : GONE);
            CharSequence[] mess = typedArray.getTextArray(R.styleable.FillSpinnerView_entries);
            if (mess != null) {
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(mContext, R.layout.layout_spinner_text, mess);
                adapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void setSpinnerData(List<String> dataList){
        if(dataList!=null&&dataList.size()>0){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.layout_spinner_text, dataList);
            adapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }

    public int selectPosition() {
        return spinner.getSelectedItemPosition();
    }

    public TextView getTitleText() {
        return titleText;
    }

    public AppCompatSpinner getSpinner() {
        return spinner;
    }
}
