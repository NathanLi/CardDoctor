package com.yunkahui.datacubeper.common.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.yunkahui.datacubeper.R;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2018/4/8.
 */

public class LoadingViewDialog extends DialogFragment {

    private AVLoadingIndicatorView mAVLoadingIndicatorView;
    private TextView mTextViewTitle;

    private String mTitle;
    private static LoadingViewDialog mViewDialog;


    public static LoadingViewDialog getInstance() {
        if(mViewDialog==null){
            mViewDialog=new LoadingViewDialog();
        }
        return mViewDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window=getDialog().getWindow();
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        layoutParams.dimAmount=0.0f;
        window.setAttributes(layoutParams);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);

        View view=inflater.inflate(R.layout.layout_loading_view,null);
        mAVLoadingIndicatorView=view.findViewById(R.id.av_loading_view);
        mTextViewTitle=view.findViewById(R.id.text_view_loading_title);

        if(!TextUtils.isEmpty(mTitle)){
            mTextViewTitle.setText(mTitle);
        }
        return view;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


    public void setTitle(String title){
        mTitle=title;
    }

    public void show(Activity activity){
        if(mAVLoadingIndicatorView!=null){
            if(mAVLoadingIndicatorView.getVisibility()==View.GONE){
                mAVLoadingIndicatorView.setVisibility(View.VISIBLE);
            }
            mAVLoadingIndicatorView.show();
        }
        super.show(activity.getFragmentManager(),"LoadingViewDialog");
    }
    public void dismiss(){
        if(mAVLoadingIndicatorView!=null){
            mAVLoadingIndicatorView.hide();
        }
        super.dismiss();
    }

    @Override
    public void onDestroyView() {
        if(mAVLoadingIndicatorView!=null){
            mAVLoadingIndicatorView.hide();
        }
        super.onDestroyView();
    }
}
