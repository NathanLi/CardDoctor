package com.yunkahui.datacubeper.mine.logic;

import android.content.Context;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.Message;
import com.yunkahui.datacubeper.common.bean.MessageGroup;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/17.
 */

public class MessageLogic {


    /**
     * 查询消息列表
     */
    public void checkNewMessageList(Context context,String startTime, String type, int page, SimpleCallBack<BaseBean<MessageGroup>> callBack){
        Map<String,String> params= RequestUtils.newParams(context)
                .addParams("begin_time",startTime)
                .addParams("notice_type",type)
                .addParams("pageNum",page)
                .create();
        HttpManager.getInstance().create(ApiService.class).checkNewMessageList(params)
                .compose(HttpManager.<BaseBean<MessageGroup>>applySchedulers()).subscribe(callBack);
    }

}
