package com.yindian.carddoctor.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/4/2.
 */

public class ToastUtils {

    private static Toast sToast=null;
    private static final boolean isShow=true;

    public static void show(Context context,CharSequence message){
        if(isShow){
            if(sToast==null){
                sToast=Toast.makeText(context,message,Toast.LENGTH_SHORT);
            }else{
                sToast.setText(message);
            }
            sToast.show();
        }
    }

    public static void show(Context context,int resid){
        if(isShow){
            if(sToast==null){
                sToast=Toast.makeText(context,resid,Toast.LENGTH_SHORT);
            }else{
                sToast.setText(resid);
            }
            sToast.show();
        }
    }

    public static void show(Context context,CharSequence message,int gravity){
        if(isShow){
            if(sToast==null){
                sToast=Toast.makeText(context,message,Toast.LENGTH_SHORT);
            }else{
                sToast.setText(message);
            }
            sToast.setGravity(gravity,0,0);
            sToast.show();
        }
    }

}
