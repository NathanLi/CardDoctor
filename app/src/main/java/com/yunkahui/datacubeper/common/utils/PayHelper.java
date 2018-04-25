package com.yunkahui.datacubeper.common.utils;

import android.app.Activity;
import android.graphics.Interpolator;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.sdk.app.PayTask;
import java.util.Map;
/**
 * Created by Administrator on 2018/4/25.
 */

public class PayHelper {


    public static PayEvent newPayEvnet() {
        return new PayEvent();
    }


    public static class PayEvent {

        private OnPayListener mOnPayListener;

        private static final int SDK_PAY_FLAG = 1;


        public void setOnPayListener(OnPayListener listener){
            mOnPayListener=listener;
        }

        private Handler mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                switch (msg.what) {
                    case SDK_PAY_FLAG:
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        if (TextUtils.equals(resultStatus, "9000")) {
                            if(mOnPayListener!=null){
                                mOnPayListener.onSuccess(resultInfo);
                            }
                        } else {
                            if(mOnPayListener!=null){
                                if (TextUtils.equals(resultStatus, "6001")) {
                                    mOnPayListener.onFill("已取消支付");
                                } else {
                                    mOnPayListener.onFill(resultStatus + " " + payResult.getMemo());
                                }
                            }
                        }
                        break;
                }
                return true;
            }
        });


        public void pay(final Activity activity, final String orderInfo) {
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    PayTask alipay = new PayTask(activity);
                    Map<String, String> result = alipay.payV2(orderInfo, true);

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    }


    public interface OnPayListener {
        void onSuccess(String data);
        void onFill(String error);
    }


}
