package com.yunkahui.datacubeper.common.other;

import android.app.NotificationManager;
import android.content.Context;

import com.yunkahui.datacubeper.base.CardDoctorApplication;
import com.yunkahui.datacubeper.common.utils.SharedPreferencesUtils;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * JPush配置回调类
 */

public class JPushMessageCallBackReceiver extends JPushMessageReceiver {
    private static final String TAG = "JPushMessageCallBackReceiver";

    private NotificationManager nm;

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        if(jPushMessage.getErrorCode()==0){
            SharedPreferencesUtils.save(CardDoctorApplication.getContext(),SharedPreferencesUtils.JPush_Alias,jPushMessage.getAlias());
        }

    }
}
