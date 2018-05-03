package com.yunkahui.datacubeper.home.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.SmartPlanSub;
import com.yunkahui.datacubeper.common.bean.TodayOperationSub;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by YD1 on 2018/4/12
 */
public class DesignSubLogic {

    //******** 获取今日操作 ********
    public void requestTodayOperation(Context context, String isPos, int pageSize, int pageNum, SimpleCallBack<BaseBean<TodayOperationSub>> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("is_pos", isPos)
                .addParams("page", String.valueOf(pageSize))
                .addParams("num", String.valueOf(pageNum))
                .create();
        HttpManager.getInstance().create(ApiService.class).loadTodayOperation(params)
                .compose(HttpManager.<BaseBean<TodayOperationSub>>applySchedulers()).subscribe(callBack);

    }

    //******** 获取规划列表 ********
    public void requestPlanList(Context context, String isPos, int pageSize, int pageNum, SimpleCallBack<BaseBean<List<SmartPlanSub>>> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("is_pos", isPos)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNum", String.valueOf(pageNum))
                .create();
        HttpManager.getInstance().create(ApiService.class).loadPlanList(params)
                .compose(HttpManager.<BaseBean<List<SmartPlanSub>>>applySchedulers()).subscribe(callBack);
    }

    //******** 标记还款交易 ********
    public void signRepay(Context context, int autoPlanningId, SimpleCallBack<BaseBean> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("auto_planning_id", String.valueOf(autoPlanningId))
                .create();
        HttpManager.getInstance().create(ApiService.class).signRepay(params)
                .compose(HttpManager.<BaseBean>applySchedulers()).subscribe(callBack);
    }
}
