package com.yunkahui.datacubeper.mine.logic;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.Message;
import com.yunkahui.datacubeper.common.bean.MessageGroup;
import com.yunkahui.datacubeper.common.utils.DataBaseUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.greendao.MessageDao;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/17.
 */

public class MessageLogic {


    /**
     * 查询消息列表
     */
    public void checkNewMessageList(Context context, String startTime, String type, int page, SimpleCallBack<BaseBean<MessageGroup>> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("begin_time", startTime)
                .addParams("notice_type", type)
                .addParams("pageNum", page)
                .create();
        HttpManager.getInstance().create(ApiService.class).checkNewMessageList(params)
                .compose(HttpManager.<BaseBean<MessageGroup>>applySchedulers()).subscribe(callBack);
    }

    //数据库中不存在的情况下，插入一条数据
    public void insert(Context context, Message message) {
        if (DataBaseUtils.getDaoSession(context).getMessageDao().queryBuilder().where(MessageDao.Properties.Sys_notice_id.eq(message.getSys_notice_id())).list().size() <= 0) {
            DataBaseUtils.getDaoSession(context).getMessageDao().insert(message);
        }
    }

    //查询所有保存的消息
    public List<Message> queryAllMessage(Context context) {
        return DataBaseUtils.getDaoSession(context).loadAll(Message.class);
    }

    //判断信息是否已读
    public boolean isUnReadMessage(Context context, Message message) {
        List<Message> messages = queryAllMessage(context);
        for (int i = 0; i < messages.size(); i++) {
            if (message.getSys_notice_id().equals(messages.get(i).getSys_notice_id())) {
                return true;
            }
        }
        return false;
    }

    //计算没读消息的数量
    public int getUnReadMessageNumber(Context context, List<String> messageIds) {
        List<Message> messages = queryAllMessage(context);
        Log.e("2018", "所有message->" + messages.size());
        int num = messageIds.size();
        for (int i = 0; i < messageIds.size(); i++) {
            for (int k = 0; k < messages.size(); k++) {
                if (messageIds.get(i).equals(messages.get(k).getSys_notice_id())) {
                    num--;
                    break;
                }
            }

        }
        return num;
    }

    public void clear(Context context) {
        DataBaseUtils.getDaoSession(context).getMessageDao().deleteAll();
    }

}
